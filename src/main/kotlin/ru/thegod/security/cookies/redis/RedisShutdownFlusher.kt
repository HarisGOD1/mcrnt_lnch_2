package ru.thegod.security.cookies.redis

import io.lettuce.core.api.StatefulRedisConnection
import io.micronaut.context.annotation.Requires
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.context.event.ShutdownEvent
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
@Requires(property = "ru.thegod.redis.flush-on-shutdown.enabled", value = "true", defaultValue = "false")
class RedisShutdownFlusher(
    // Injects Redis connection from application.properties
    private val redisConnection: StatefulRedisConnection<String, String>
) : ApplicationEventListener<ShutdownEvent> {

    companion object {
        private val LOG = LoggerFactory.getLogger(RedisShutdownFlusher::class.java)
    }

    override fun onApplicationEvent(event: ShutdownEvent) {
        LOG.warn("Shutdown event received. 'myapp.redis.flush-on-shutdown.enabled' is true. FLUSHING CURRENT REDIS DATABASE!")
        try {
            // Get synchronous commands from the connection
            val syncCommands = redisConnection.sync()

            // FLUSHDB flushes the database this connection is currently targeting.
            // If your redis.uri in application.yml specifies a database number
            // (e.g., redis://localhost:6379/2), that specific database (DB 2) will be flushed.
            // Otherwise, it's typically database 0.
            val result: String = syncCommands.flushdb()
            LOG.info("Redis FLUSHDB command executed successfully. Result: {}", result)
        } catch (e: Exception) {
            LOG.error("Error flushing Redis database on shutdown: {}", e.message, e)
        }
        // The redisConnection itself is managed by Micronaut and will be closed
        // as part of the application context shutdown.
    }
}