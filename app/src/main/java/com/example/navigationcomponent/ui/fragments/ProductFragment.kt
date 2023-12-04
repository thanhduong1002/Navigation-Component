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
import com.example.navigationcomponent.databinding.FragmentProductBinding
import com.example.navigationcomponent.ui.adapters.ProductAdapter
import com.example.navigationcomponent.ui.viewmodels.ProductViewModel

class ProductFragment : Fragment() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var productAdapter: ProductAdapter
    private lateinit var binding: FragmentProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        productAdapter = ProductAdapter(emptyList())
        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewProducts.adapter = productAdapter

        val actionBar = (activity as AppCompatActivity).supportActionBar

        actionBar?.title = "List Products"
        actionBar?.setDisplayHomeAsUpEnabled(false)

        productViewModel.productsList.observe(viewLifecycleOwner) { productList ->
            productAdapter.setProductsList(productList)
            productAdapter.notifyDataSetChanged()
        }

        productViewModel.getAllProducts()
    }
}