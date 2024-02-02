package com.example.repository

import com.example.data.model.User
import com.example.data.table.UserTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class repo{
    suspend fun addUser(user: User){
        DatabaseFactory.dbQuery { UserTable.insert { ut->
            ut[UserTable.email] = user.email
            ut[UserTable.hashPassword]= user.hashPassword
            ut[UserTable.name]=user.userName
        } }
    }
    suspend fun finduserByEmail(email:String)=DatabaseFactory.dbQuery {
        UserTable.select{UserTable.email.eq(email) }
            .map {
                rowtouser(it)
            }
            .singleOrNull()

    }

    private fun rowtouser(row: ResultRow?):User?{
        if(row==null){
            return null
        }
        return User(
            email = row[UserTable.email],
            hashPassword = row[UserTable.hashPassword],
            userName = row[UserTable.name]

        )

    }

}
