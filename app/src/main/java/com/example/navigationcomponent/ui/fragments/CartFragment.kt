package com.example.navigationcomponent.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationcomponent.databinding.FragmentCartBinding
import com.example.navigationcomponent.ui.adapters.CartAdapter
import com.example.navigationcomponent.ui.viewmodels.CartViewModel
import com.example.navigationcomponent.ui.viewmodels.UserViewModel

class CartFragment : Fragment() {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var userViewModel: UserViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var binding: FragmentCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        cartAdapter = CartAdapter(emptyList())
        binding.recyclerViewCarts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewCarts.adapter = cartAdapter

        val actionBar = (activity as AppCompatActivity).supportActionBar

        actionBar?.title = "List Carts"
        actionBar?.setDisplayHomeAsUpEnabled(false)

        cartViewModel.cartsList.observe(viewLifecycleOwner) { cartList ->
            val userIdList = cartList.mapNotNull { it.userId }
            Log.d("list", "onViewCreated: $userIdList")
            val imageList = mutableListOf<String?>()

            userIdList.forEach { userId ->
                userViewModel.fetchUserImage(userId) { image ->
                    imageList.add(image)

                    if (imageList.size == userIdList.size) {
                        Log.d("listImage", "onViewCreated: $imageList")

                        val cartsWithImages = cartList.mapIndexed { index, cart ->
                            cart.copy(image = imageList[index])
                        }
                        cartAdapter.setCartsList(cartsWithImages)
                        cartAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        cartViewModel.getAllCarts()
    }
}