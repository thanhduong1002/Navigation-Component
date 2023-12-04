package com.example.navigationcomponent.ui.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationcomponent.R
import com.example.navigationcomponent.data.remote.models.User
import com.example.navigationcomponent.databinding.UserItemBinding
import com.example.navigationcomponent.ui.fragments.ProfileFragment
import com.squareup.picasso.Picasso

class UserAdapter(private var usersList: List<User>) :
    RecyclerView.Adapter<UserAdapter.UserHolder>() {

    fun setUsersList(usersList: List<User>) {
        this.usersList = usersList
    }

    class UserHolder(val userItemBinding: UserItemBinding) :
        RecyclerView.ViewHolder(userItemBinding.root)

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        val item = usersList[position]

        holder.userItemBinding.textViewName.text = "${item.firstName} ${item.lastName}"
        holder.userItemBinding.textViewEmail.text = item.email
        Picasso.get().load(item.image).into(holder.userItemBinding.imageView)

        holder.userItemBinding.buttonViewDetails.setOnClickListener {
            val bundle = Bundle()

            bundle.putString(ProfileFragment.UserId, item.id.toString())
            bundle.putString(ProfileFragment.Title, item.firstName + " " + item.lastName)

            holder.itemView.findNavController()
                .navigate(R.id.action_userFragment_to_profileFragment, bundle)
        }
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}