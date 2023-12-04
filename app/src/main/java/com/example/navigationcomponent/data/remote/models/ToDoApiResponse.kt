package com.example.navigationcomponent.data.remote.models

import com.google.gson.annotations.SerializedName

data class ToDoApiResponse (

    @SerializedName("todos" ) var todos : ArrayList<Todo> = arrayListOf(),
    @SerializedName("total" ) var total : Int?             = null,
    @SerializedName("skip"  ) var skip  : Int?             = null,
    @SerializedName("limit" ) var limit : Int?             = null

)

data class Todo (

    @SerializedName("id"        ) var id        : Int?     = null,
    @SerializedName("todo"      ) var todo      : String?  = null,
    @SerializedName("completed" ) var completed : Boolean? = null,
    @SerializedName("userId"    ) var userId    : Int?     = null,
                                  var image     : String?  = null

)