package com.example.navigationcomponent.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationcomponent.data.remote.api.ServiceBuilder
import com.example.navigationcomponent.data.remote.api.ServiceInterface
import com.example.navigationcomponent.data.remote.models.ToDoApiResponse
import com.example.navigationcomponent.data.remote.models.Todo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ToDoViewModel : ViewModel() {
    private val retrofit = ServiceBuilder.buildService(ServiceInterface::class.java)

    private val _toDosList: MutableLiveData<List<Todo>> = MutableLiveData(emptyList())
    val toDosList: LiveData<List<Todo>> = _toDosList

    fun getAllToDos() {
        retrofit.getAllTodos().enqueue(object : Callback<ToDoApiResponse> {
            override fun onResponse(
                call: Call<ToDoApiResponse>,
                response: Response<ToDoApiResponse>
            ) {
                try {
                    val responseBody = response.body()!!
                    val todos = responseBody.todos

                    _toDosList.postValue(todos)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<ToDoApiResponse>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
            }
        })
    }

}