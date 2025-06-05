package ru.thegod.security.user.roles

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class UserRolesConverter : AttributeConverter<Set<UserRole>?, Int?> {
    public override fun convertToDatabaseColumn(userRoles: Set<UserRole>?): Int? {
        if (userRoles == null) {
            return null
        }
        val total_sum = userRoles.sumOf { userRole -> userRolesMap.get(userRole)!! }
//        println("CONVERTING FROM $userRoles TO "+total_sum)
        return total_sum
    }

    public override fun convertToEntityAttribute(RoleNumber: Int?): Set<UserRole>? {
        if (RoleNumber == null){
            return null
        }
        var roleNumberVar = RoleNumber!!
        var counter = 0
        var roles:MutableSet<UserRole> = mutableSetOf()
        while(roleNumberVar>0){
            if (roleNumberVar%2==1){ // 010 ---> 0 -> 1 -> 1 | exp = 1 -> 2 -> 4
                roles.add(UserRole.entries[counter])
            }
//            println(roleNumberVar)
            counter+=1
            roleNumberVar=roleNumberVar/2
        }
//        println("CONVERTING FROM "+roles)
        return roles
    }

//    companion object {
//        private const val SEPARATOR = UserRole.ADMIN.
//    }

    val userRolesMap = mapOf(
        UserRole.ADMIN to 1,
        UserRole.DEFAULT_USER to 2,
        UserRole.THIRD_USER to 4
    )
}