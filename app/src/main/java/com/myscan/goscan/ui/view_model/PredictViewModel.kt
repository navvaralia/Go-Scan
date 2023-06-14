package com.myscan.goscan.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myscan.goscan.data.api.APIConfig
import com.myscan.goscan.data.api.PredictResponses
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PredictViewModel : ViewModel() {

    private val _predictName = MutableLiveData<String>()
    val predictName: LiveData<String> = _predictName

    private val _predictPrice = MutableLiveData<Int>()
    val predictPrice: LiveData<Int> = _predictPrice

    fun predictGroceries(image: MultipartBody.Part) {
        val client = APIConfig.getAPIService().predictGroceries(image)
        client.enqueue(object : Callback<PredictResponses> {
            override fun onResponse(
                call: Call<PredictResponses>,
                response: Response<PredictResponses>
            ) {
                if (response.isSuccessful) {
                    val predictResult = response.body()
                    _predictName.value = predictResult?.name
                    _predictPrice.value = predictResult?.price

                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PredictResponses>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "AddStoriesViewModel"
    }
}