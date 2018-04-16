package com.i7play.pameizi.network

import com.i7play.pameizi.bean.ZiPaiResp
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface API {
    @GET("/zipai/{pageId}")
    fun zipai(@Path("pageId") pageId: String): Observable<ZiPaiResp>
}