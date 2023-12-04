package com.example.navigationcomponent.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationcomponent.databinding.FragmentUserBinding
import com.example.navigationcomponent.ui.adapters.UserAdapter
import com.example.navigationcomponent.ui.viewmodels.UserViewModel

class UserFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userAdapter: UserAdapter
    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userAdapter = UserAdapter(emptyList())
        binding.recyclerViewUsers.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewUsers.adapter = userAdapter

        val actionBar = (activity as AppCompatActivity).supportActionBar

        actionBar?.title = "List Users"
        actionBar?.setDisplayHomeAsUpEnabled(false)

        userViewModel.usersList.observe(viewLifecycleOwner) { userList ->
            userAdapter.setUsersList(userList)
            userAdapter.notifyDataSetChanged()
        }

        userViewModel.getAllUsers()
    }
}