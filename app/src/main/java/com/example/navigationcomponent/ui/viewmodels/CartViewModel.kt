package com.example.navigationcomponent.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationcomponent.data.remote.api.ServiceBuilder
import com.example.navigationcomponent.data.remote.api.ServiceInterface
import com.example.navigationcomponent.data.remote.models.Cart
import com.example.navigationcomponent.data.remote.models.CartApiResponse
import com.example.navigationcomponent.data.remote.models.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : ViewModel() {
    private val retrofit = ServiceBuilder.buildService(ServiceInterface::class.java)

    private val _cartsList: MutableLiveData<List<Cart>> = MutableLiveData(emptyList())
    val cartsList: LiveData<List<Cart>> = _cartsList

    private val _detailCart: MutableLiveData<Cart> = MutableLiveData()
    val detailCart: LiveData<Cart> = _detailCart

    private val _productIdList: MutableLiveData<ArrayList<Int>> = MutableLiveData()
    val productIdList : LiveData<ArrayList<Int>> = _productIdList

    fun getAllCarts() {
        retrofit.getAllCarts().enqueue(object : Callback<CartApiResponse> {
            override fun onResponse(
                call: Call<CartApiResponse>,
                response: Response<CartApiResponse>
            ) {
                try {
                    val responseBody = response.body()!!
                    val carts = responseBody.carts

                    _cartsList.postValue(carts)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<CartApiResponse>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
            }
        })
    }

    fun getDetailCart(id: Int) {
        retrofit.getDetailCart(id).enqueue(object : Callback<Cart> {
            override fun onResponse(
                call: Call<Cart>,
                response: Response<Cart>
            ) {
                try {
                    val responseBody = response.body()!!

                    _productIdList.postValue(getProductIds(responseBody.products))
                    _detailCart.postValue(responseBody)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<Cart>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
            }
        })
    }

    fun getProductIds(products: ArrayList<Products>): ArrayList<Int> {
        val productIds = ArrayList<Int>()

        for (product in products) {
            val productId = product.id

            if (productId != null) {
                productIds.add(productId)
            }
        }

        return productIds
    }
}