package com.example.task_eheuristic_shyam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject


class MainActivity : AppCompatActivity() , Response.Listener<JSONObject>, Response.ErrorListener{


    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.list1)
        userList = ArrayList()
        adapter = UserAdapter(this, userList)
        listView.adapter = adapter

        val requestQueue = Volley.newRequestQueue(this)
        val url = "https://reqres.in/api/users"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, this, this)
        requestQueue.add(jsonObjectRequest)

        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val user = userList[position]
            val intent = Intent(this, Second_Activity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
        }
    }

   override fun onResponse(response: JSONObject) {
        val jsonArray = response.getJSONArray("data")
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val firstName = jsonObject.getString("first_name")
            val lastName = jsonObject.getString("last_name")
            val avatar = jsonObject.getString("avatar")

            val user = User(firstName, lastName, avatar)
            userList.add(user)
        }
        adapter.notifyDataSetChanged()
    }


    override fun onErrorResponse(error: VolleyError?) {

        Toast.makeText(this@MainActivity, "Error: $error", Toast.LENGTH_SHORT).show()
    }

}