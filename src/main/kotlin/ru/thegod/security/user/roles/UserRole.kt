package ru.thegod.security.user.roles

enum class UserRole{
    ADMIN,           // 0 <- 2^0
    DEFAULT_USER,    // 1 <- 2^1
    THIRD_USER      // 2 <- 2^2
                            // X <- 2^k
                            // sumOf(UserRoles) = max 2^k+1


}