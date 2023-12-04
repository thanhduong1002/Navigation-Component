package com.example.navigationcomponent.data.remote.models

import com.google.gson.annotations.SerializedName

data class CartApiResponse (

    @SerializedName("carts" ) var carts : ArrayList<Cart> = arrayListOf(),
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("skip"  ) var skip  : Int?             = null,
    @SerializedName("limit" ) var limit : Int?             = null
)

data class Cart (

    @SerializedName("id"              ) var id              : Int?                = null,
    @SerializedName("products"        ) var products        : ArrayList<Products> = arrayListOf(),
    @SerializedName("total"           ) var total           : Int?                = null,
    @SerializedName("discountedTotal" ) var discountedTotal : Int?                = null,
    @SerializedName("userId"          ) var userId          : Int?                = null,
    @SerializedName("totalProducts"   ) var totalProducts   : Int?                = null,
    @SerializedName("totalQuantity"   ) var totalQuantity   : Int?                = null,
                                        var image           : String?             = null

)

data class Products (

    @SerializedName("id"                 ) var id                 : Int?    = null,
    @SerializedName("title"              ) var title              : String? = null,
    @SerializedName("price"              ) var price              : Int?    = null,
    @SerializedName("quantity"           ) var quantity           : Int?    = null,
    @SerializedName("total"              ) var total              : Int?    = null,
    @SerializedName("discountPercentage" ) var discountPercentage : Double? = null,
    @SerializedName("discountedPrice"    ) var discountedPrice    : Int?    = null

)