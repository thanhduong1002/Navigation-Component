package com.example.navigationcomponent.ui.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationcomponent.R
import com.example.navigationcomponent.data.remote.models.Product
import com.example.navigationcomponent.databinding.ProductItemBinding
import com.example.navigationcomponent.ui.fragments.DetailProductFragment
import com.squareup.picasso.Picasso

class ProductAdapter(private var productsList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    fun setProductsList(productsList: List<Product>) {
        this.productsList = productsList
    }

    class ProductHolder(val productItemBinding: ProductItemBinding) :
        RecyclerView.ViewHolder(productItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        return ProductHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val item = productsList[position]

        holder.productItemBinding.textViewName.text = item.title
        holder.productItemBinding.textViewPrice.text = item.description
        holder.productItemBinding.textViewQuantity.text = "$${item.price}"
        Picasso.get().load(item.images?.get(0)).into(holder.productItemBinding.imageView)

        holder.productItemBinding.cardView.setOnClickListener {
            val bundle = Bundle()

            bundle.putString(DetailProductFragment.Title, item.title)
            bundle.putString(DetailProductFragment.ProductId, item.id.toString())

            holder.itemView.findNavController()
                .navigate(R.id.action_productFragment_to_detailProductFragment, bundle)
        }
    }
}