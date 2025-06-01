package ru.thegod.security.cookie

import io.lettuce.core.api.StatefulRedisConnection
import jakarta.inject.Singleton
import java.util.*

@Singleton
class ExpiredTokenStorage(private var redisConnection: StatefulRedisConnection<String, String>) {

    fun getItem(key: String): Optional<String> {
        return Optional.ofNullable(redisConnection.sync()[key])
    }

    fun putItem(key: String, value: String): String {
        return redisConnection.sync().setex(key, 30, value)
    }

    fun putSingleExpiredToken(token:String):String{
        return redisConnection.sync().setex("token:$token", 30, "expired")
    }
    fun getSingleExpiredToken(token:String):Optional<String>{
        return Optional.ofNullable(redisConnection.sync()["token:$token"])
    }


    fun putAllExpiredTime(username:String,born:Long):String{
        return redisConnection.sync().setex("expired:$username",30,born.toString())
    }

    fun getAllExpiredTime(username: String):Optional<String>{
        return Optional.ofNullable(redisConnection.sync()["expired:$username"])
    }

    fun getSize():Long{
        return redisConnection.sync().dbsize()
    }

    fun flushDB():String{
        return redisConnection.sync().flushdb()
    }
    fun info():String{
        return redisConnection.sync().info()
    }
    fun selectDatabase(number: Int):String{
        return redisConnection.sync().select(number)
    }
}