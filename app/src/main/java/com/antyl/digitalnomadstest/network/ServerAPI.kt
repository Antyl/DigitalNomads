package com.antyl.digitalnomadstest.network

import com.antyl.digitalnomadstest.model.entity.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerAPI {

    @GET("v2/everything?q=android&from=2019-04-00&sortBy=publi%20shedAt&apiKey=26eddb253e7840f988aec61f2ece2907")
    fun getArticles(@Query("page") page: Int): Call<ArticlesResponse>

}