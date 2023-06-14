package com.myscan.goscan.data.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface APIService {
    @Multipart
    @POST("predict")
    fun predictGroceries(
        @Part file: MultipartBody.Part
    ): Call<PredictResponses>
}