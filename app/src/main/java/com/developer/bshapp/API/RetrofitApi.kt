package com.developer.bshapp.API


import com.developer.bshapp.model.UserUploadModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface RetrofitApi {
   @FormUrlEncoded
   @POST("api/createNews.php")
   suspend fun uploadNews(@FieldMap params: HashMap<String?, String?>): Response<ResponseBody>



}