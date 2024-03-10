package com.example.task_eheuristic_shyam

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

/*
class VolleyRequest(private val context: Context) {

    private val queue = Volley.newRequestQueue(context)
    private val url = "https://reqres.in/api/users" // Your API endpoint

    fun fetchData(callback: VolleyCallback) {
        val request = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                val usersJsonArray = response.getJSONArray("data")
                val users = mutableListOf<User>()

                for (i in 0 until usersJsonArray.length()) {
                    val userObject = usersJsonArray.getJSONObject(i)
                    val user = User(
                        userObject.getString("first_name"),
                        userObject.getString("last_name"),
                        userObject.getString("avatar")
                    )
                    users.add(user)
                }

                callback.onSuccess(users)
            },
            Response.ErrorListener {
                    error ->
                callback.onFailure(error.message ?: "Unknown error occurred")
            }
        )

        queue.add(request)
    }



}*/

  /*  private val queue = Volley.newRequestQueue(context)
    val url = "https://reqres.in/api/users"

    fun fetchData(callback: VolleyCallback) {


*//*

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->

                try
                {
                    var jsonobject = JSONObject(response)

                    var jsonArray = jsonobject.getJSONArray("data")
                    for(i in 0 until jsonArray.length())
                    {


                        var jsonObject2 = jsonArray.getJSONObject(i)

                        var name = jsonObject2.getString("name")
                        var categoryname = jsonObject2.getString("categoryname")
                        var image = jsonObject2.getString("imagejsonurl")

                        Log.d("Tops1234",response.toString())

                        var m = User()
                        m.name=name
                        m.categoryname=categoryname
                        m.imagejsonurl=image
                        list.add(m)

                    }
                    var myAdapter = MyAdapter(applicationContext,list)
                    listView.adapter=myAdapter


                }
                catch(e:Exception)
                {
                    e.printStackTrace()
                }

*//*


              *//*  val userList = mutableListOf<User>()

                Log.d("User","  Pass 1 " + userList)
                for (i in 0 until response.length()) {
                    val userObject = response.getJSONObject(i)

                    Log.d("User","  Pass 2 " + userObject)

                    val user = User(
                        userObject.getString("first_name"),
                        userObject.getString("last_name"),
                        userObject.getString("avatar")


                    )
                    userList.add(user)

                    Log.d("User","  Pass 3  " + userList)
                }
                callback.onSuccess(userList)*//*
            },
            { error ->
                callback.onError(error.message ?: "Unknown error occurred")
            }
        )
        Volley.newRequestQueue(context).add(request)
    }
}*/