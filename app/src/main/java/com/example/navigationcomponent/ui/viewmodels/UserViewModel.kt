package com.example.navigationcomponent.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navigationcomponent.data.remote.api.ServiceBuilder
import com.example.navigationcomponent.data.remote.api.ServiceInterface
import com.example.navigationcomponent.data.remote.models.User
import com.example.navigationcomponent.data.remote.models.UserApiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserViewModel : ViewModel() {
    private val retrofit = ServiceBuilder.buildService(ServiceInterface::class.java)

    private val _usersList: MutableLiveData<List<User>> = MutableLiveData(emptyList())
    val usersList: LiveData<List<User>> = _usersList

    private val _userDetail: MutableLiveData<User> = MutableLiveData()
    val userDetail: LiveData<User> = _userDetail

    fun getAllUsers() {
        retrofit.getAllUsers().enqueue(object : Callback<UserApiResponse> {
            override fun onResponse(
                call: Call<UserApiResponse>,
                response: Response<UserApiResponse>
            ) {
                try {
                    val responseBody = response.body()!!
                    val users = responseBody.users

                    _usersList.postValue(users)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<UserApiResponse>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
            }
        })
    }

    fun getDetailUser(id: String) {
        retrofit.getDetailUser(id.toInt()).enqueue(object : Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                try {
                    val responseBody = response.body()!!

                    _userDetail.postValue(responseBody)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
            }
        })
    }

    fun fetchUserImage(userId: Int, callback: (String?) -> Unit) {
        retrofit.getDetailUser(userId).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                try {
                    val responseBody = response.body()

                    if (responseBody != null) {
                        val image = responseBody.image

                        callback(image)
                    } else {
                        callback(null)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    callback(null)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.e("Failed", "Api Failed" + t.message)
                callback(null)
            }
        })
    }
}