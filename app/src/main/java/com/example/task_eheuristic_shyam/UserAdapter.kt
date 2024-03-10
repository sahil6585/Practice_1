package com.example.task_eheuristic_shyam

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


class UserAdapter(private val context: Context, private val userList: List<User>) : BaseAdapter() {

    override fun getCount(): Int {
        return userList.size
    }

    override fun getItem(position: Int): Any {
        return userList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.design_file, parent, false)
        }

        val user = userList[position]
        val nameTextView = view!!.findViewById<TextView>(R.id.fname)
        val avatarImageView = view.findViewById<ImageView>(R.id.image)

        nameTextView.text = "${user.firstName} ${user.lastName}"

        Glide.with(context)
            .load(user.avatar)
            .into(avatarImageView)

        return view
    }
}
