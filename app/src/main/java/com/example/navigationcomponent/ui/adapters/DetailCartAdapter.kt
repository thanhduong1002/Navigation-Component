package com.example.navigationcomponent.ui.adapters

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationcomponent.R
import com.example.navigationcomponent.data.remote.models.Products
import com.example.navigationcomponent.databinding.ProductItemBinding
import com.example.navigationcomponent.ui.fragments.DetailProductFragment
import com.example.navigationcomponent.ui.viewmodels.ProductViewModel
import com.squareup.picasso.Picasso

class DetailCartAdapter(
    private var detailCartsList: List<Products>,
    private val productViewModel: ProductViewModel
) : RecyclerView.Adapter<DetailCartAdapter.DetailCartHolder>() {

    fun setDetailCartsList(detailCartsList: List<Products>) {
        this.detailCartsList = detailCartsList
    }

    class DetailCartHolder(val productItemBinding: ProductItemBinding) :
        RecyclerView.ViewHolder(productItemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailCartHolder {
        return DetailCartHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return detailCartsList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: DetailCartHolder, position: Int) {
        val item = detailCartsList[position]

        holder.productItemBinding.textViewName.text = item.title
        holder.productItemBinding.textViewPrice.text = "$${item.price}"
        holder.productItemBinding.textViewQuantity.text = "Quantity: ${item.quantity}"

        if (item.id != null) {
            productViewModel.fetchProductImage(item.id!!) { imageUrl ->
                if (!imageUrl.isNullOrEmpty()) {
                    Picasso.get().load(imageUrl).into(holder.productItemBinding.imageView)
                } else {
                    holder.productItemBinding.imageView.setImageResource(R.drawable.ic_launcher_foreground)
                }
            }
        } else {
            Log.d("productId", "null")
        }

        holder.productItemBinding.cardView.setOnClickListener {
            val bundle = Bundle()

            bundle.putString(DetailProductFragment.ProductId, item.id.toString())
            bundle.putString(DetailProductFragment.Title, item.title)

            holder.itemView.findNavController()
                .navigate(R.id.action_detailCartFragment_to_detailProductFragment, bundle)
        }
    }
}