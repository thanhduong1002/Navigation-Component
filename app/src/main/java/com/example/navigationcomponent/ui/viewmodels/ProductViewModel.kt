package com.example.navigationcomponent.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationcomponent.data.remote.api.ServiceBuilder
import com.example.navigationcomponent.data.remote.api.ServiceInterface
import com.example.navigationcomponent.data.remote.models.Product
import com.example.navigationcomponent.data.remote.models.ProductApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductViewModel : ViewModel() {
    private val retrofit = ServiceBuilder.buildService(ServiceInterface::class.java)

    private val _productsList: MutableLiveData<List<Product>> = MutableLiveData(emptyList())
    val productsList: LiveData<List<Product>> = _productsList

    private val _detailProduct: MutableLiveData<Product> = MutableLiveData()
    val detailProduct: LiveData<Product> = _detailProduct

    fun getDetailProduct(id: Int, callback: (Product?) -> Unit) {
        retrofit.getDetailProduct(id).enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                try {
                    val product = response.body()!!

                    callback(product)
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
            }
        })
    }

    fun fetchProductImage(userId: Int, callback: (String?) -> Unit) {
        retrofit.getDetailProduct(userId).enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                try {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        val image = responseBody.images?.get(0)

                        callback(image)
                    } else {
                        callback(null)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
                callback(null)
            }
        })
    }

    fun getAllProducts() {
        retrofit.getAllProducts().enqueue(object : Callback<ProductApiResponse> {
            override fun onResponse(
                call: Call<ProductApiResponse>,
                response: Response<ProductApiResponse>
            ) {
                try {
                    val responseBody = response.body()!!
                    val products = responseBody.products

                    _productsList.postValue(products)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ProductApiResponse>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
            }
        })
    }

    fun getProductById(id: Int) {
        retrofit.getDetailProduct(id).enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                try {
                    val responseBody = response.body()!!

                    _detailProduct.postValue(responseBody)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
            }
        })
    }
}