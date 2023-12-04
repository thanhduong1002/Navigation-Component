package com.example.navigationcomponent.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.navigationcomponent.databinding.FragmentProfileBinding
import com.example.navigationcomponent.ui.viewmodels.UserViewModel
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var binding: FragmentProfileBinding

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        val userId = arguments?.getString(UserId)
        val title = arguments?.getString(Title)
        val actionBar = (activity as AppCompatActivity).supportActionBar

        actionBar?.title = title
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        if (userId != null) {
            userViewModel.getDetailUser(userId)
        }

        userViewModel.userDetail.observe(viewLifecycleOwner) { detail ->
            binding.nameItem.text = "Name: ${detail.firstName} ${detail.lastName}"
            binding.emailItem.text = "Email: ${detail.email}"
            binding.phoneItem.text = "Phone: ${detail.phone}"
            binding.ageItem.text = "Age: ${detail.age}"
            binding.birthdayItem.text = "Birthday: ${detail.birthDate}"
            Picasso.get().load(detail.image).into(binding.imageAvt)
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            requireActivity().supportFragmentManager.popBackStack()

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val UserId = "UserId"
        const val Title = "Title"
    }
}