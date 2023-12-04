package com.example.navigationcomponent.ui.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationcomponent.R
import com.example.navigationcomponent.data.remote.models.Cart
import com.example.navigationcomponent.databinding.CartItemBinding
import com.example.navigationcomponent.ui.fragments.DetailCartFragment
import com.squareup.picasso.Picasso

class CartAdapter(private var cartsList: List<Cart>) :
    RecyclerView.Adapter<CartAdapter.CartHolder>() {
    private var imageCache: MutableMap<Int, String?> = mutableMapOf()

    fun setCartsList(cartsList: List<Cart>) {
        this.cartsList = cartsList
    }

    class CartHolder(val cartItemBinding: CartItemBinding) :
        RecyclerView.ViewHolder(cartItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartHolder {
        return CartHolder(
            CartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cartsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartHolder, position: Int) {
        val item = cartsList[position]

        holder.cartItemBinding.totalPrice.text = "Total price: $${item.discountedTotal}"
        holder.cartItemBinding.totalQuantity.text = "Total quantity: ${item.totalQuantity}"
        val cachedImage = imageCache[position]
        if (cachedImage != null) {
            Picasso.get().load(cachedImage).into(holder.cartItemBinding.imageUser)
        } else {
            Picasso.get().load(item.image).into(holder.cartItemBinding.imageUser)
            // Lưu ảnh vào cache
            imageCache[position] = item.image
        }
        holder.cartItemBinding.cardView.setOnClickListener {
            val bundle = Bundle()

            bundle.putString(DetailCartFragment.CartId, item.id.toString())
            bundle.putString(DetailCartFragment.DiscountedTotal, item.discountedTotal.toString())

            holder.itemView.findNavController()
                .navigate(R.id.action_cartFragment_to_detailCartFragment, bundle)
        }
    }
}