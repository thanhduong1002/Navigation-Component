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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.navigationcomponent.databinding.FragmentDetailCartBinding
import com.example.navigationcomponent.ui.adapters.DetailCartAdapter
import com.example.navigationcomponent.ui.viewmodels.CartViewModel
import com.example.navigationcomponent.ui.viewmodels.ProductViewModel

class DetailCartFragment : Fragment() {
    private lateinit var cartViewModel: CartViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var detailCartAdapter: DetailCartAdapter
    private lateinit var binding: FragmentDetailCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cartId = arguments?.getString(CartId)
        val discountedTotal = arguments?.getString(DiscountedTotal)

        cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        detailCartAdapter = DetailCartAdapter(emptyList(), productViewModel)
        binding.recyclerViewDetailCarts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewDetailCarts.adapter = detailCartAdapter

        val actionBar = (activity as AppCompatActivity).supportActionBar

        actionBar?.title = "Total: $$discountedTotal"
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        cartViewModel.detailCart.observe(viewLifecycleOwner) { cart ->
            detailCartAdapter.setDetailCartsList(cart.products)
            detailCartAdapter.notifyDataSetChanged()
        }

        if (cartId != null) {
            cartViewModel.getDetailCart(cartId.toInt())
        }
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
        const val CartId = "CartId"
        const val DiscountedTotal = "DiscountedTotal"
    }
}