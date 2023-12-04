package com.example.navigationcomponent.data.remote.api

import com.example.navigationcomponent.data.remote.models.Cart
import com.example.navigationcomponent.data.remote.models.CartApiResponse
import com.example.navigationcomponent.data.remote.models.Product
import com.example.navigationcomponent.data.remote.models.ProductApiResponse
import com.example.navigationcomponent.data.remote.models.ToDoApiResponse
import com.example.navigationcomponent.data.remote.models.User
import com.example.navigationcomponent.data.remote.models.UserApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface ServiceInterface {
    //User
    @Headers("Content-Type:application/json")
    @GET("/users")
    fun getAllUsers(): Call<UserApiResponse>

    @GET("/users/{id}")
    fun getDetailUser(@Path("id") id: Int): Call<User>

    //Product
    @Headers("Content-Type:application/json")
    @GET("/products")
    fun getAllProducts(): Call<ProductApiResponse>

    @GET("/products/{id}")
    fun getDetailProduct(@Path("id") id: Int): Call<Product>

    //Cart
    @Headers("Content-Type:application/json")
    @GET("/carts")
    fun getAllCarts(): Call<CartApiResponse>

    @GET("/carts/{id}")
    fun getDetailCart(@Path("id") id: Int): Call<Cart>

    //Todos
    @Headers("Content-Type:application/json")
    @GET("/todos")
    fun getAllTodos(): Call<ToDoApiResponse>
}