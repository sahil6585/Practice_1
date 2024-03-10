package com.example.task_eheuristic_shyam

interface VolleyCallback {

    fun onSuccess(users: List<User>)
    fun onFailure(error: String)

   /* fun onSuccess(users: List<User>)
    fun onError(error: String)*/
}