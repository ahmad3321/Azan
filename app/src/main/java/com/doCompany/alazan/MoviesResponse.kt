package com.doCompany.alazan

import com.doCompany.alazan.Models.Welcome1
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface QuotesApi {
    //@GET("v1/calendarByCity/2023/4")
    @GET
    suspend fun getQuotes(
        @Url url:String,
        @Query(value = "city") city: String?,
        @Query(value = "country") country: String?,
        @Query(value = "method") method: String?
    ):  Response<Welcome1>
}