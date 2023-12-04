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
import com.example.navigationcomponent.databinding.FragmentDetailProductBinding
import com.example.navigationcomponent.ui.viewmodels.ProductViewModel
import com.squareup.picasso.Picasso

class DetailProductFragment : Fragment() {
    private lateinit var productViewModel: ProductViewModel
    private lateinit var binding: FragmentDetailProductBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString(Title)
        val productId = arguments?.getString(ProductId)
        val actionBar = (activity as AppCompatActivity).supportActionBar

        actionBar?.title = title
        actionBar?.setDisplayHomeAsUpEnabled(true)
        setHasOptionsMenu(true)

        productViewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        productViewModel.detailProduct.observe(viewLifecycleOwner) { detail ->
            var currentImageIndex = 0

            binding.productNameTextView.text = "Name: ${detail.title}"
            binding.productDescriptionTextView.text = "Description: ${detail.description}"
            binding.productPriceTextView.text = "Price: $${detail.price}"
            binding.productRatingTextView.text = "Rating: ${detail.rating} star"
            Picasso.get().load(detail.images?.get(currentImageIndex)).into(binding.productImageView)

            binding.nextButton.setOnClickListener {
                if (currentImageIndex < (detail.images?.size?.minus(1) ?: 0)) {
                    currentImageIndex++
                    Picasso.get().load(detail.images?.get(currentImageIndex)).into(binding.productImageView)
                }
            }

            binding.previousButton.setOnClickListener {
                if (currentImageIndex > 0) {
                    currentImageIndex--
                    Picasso.get().load(detail.images?.get(currentImageIndex)).into(binding.productImageView)
                }
            }
        }

        if (productId != null) {
            productViewModel.getProductById(productId.toInt())
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
        const val Title = "Title"
        const val ProductId = "ProductId"
    }
}