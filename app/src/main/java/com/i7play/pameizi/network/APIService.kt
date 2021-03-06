package com.i7play.pameizi.network

import android.text.TextUtils
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Retrofit
import java.io.IOException
import java.util.*
import java.util.concurrent.TimeUnit


class APIService {
    companion object {
        val agents = arrayListOf("Mozilla/5.0 (iPhone 84; CPU iPhone OS 10_3_3 like Mac OS X) AppleWebKit/603.3.8 (KHTML, like Gecko) Version/10.0 MQQBrowser/7.8.0 Mobile/14G60 Safari/8536.25 MttCustomUA/2 QBWebViewType/1 WKType/1",
                "Mozilla/5.0 (Linux; Android 7.0; STF-AL10 Build/HUAWEISTF-AL10; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.49 Mobile MQQBrowser/6.2 TBS/043508 Safari/537.36 V1_AND_SQ_7.2.0_730_YYB_D QQ/7.2.0.3270 NetType/4G WebP/0.3.0 Pixel/1080",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 10_3_3 like Mac OS X) AppleWebKit/603.3.8 (KHTML, like Gecko) Mobile/14G60 MicroMessenger/6.5.18 NetType/WIFI Language/en",
                "Mozilla/5.0 (Linux; Android 5.1.1; vivo Xplay5A Build/LMY47V; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/48.0.2564.116 Mobile Safari/537.36 T7/9.3 baiduboxapp/9.3.0.10 (Baidu; P1 5.1.1)",
                "Mozilla/5.0 (Linux; U; Android 7.0; zh-cn; STF-AL00 Build/HUAWEISTF-AL00) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 Chrome/37.0.0.0 MQQBrowser/7.9 Mobile Safari/537.36",
                "Mozilla/5.0 (Linux; Android 6.0; LEX626 Build/HEXCNFN5902606111S) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/35.0.1916.138 Mobile Safari/537.36 T7/7.4 baiduboxapp/8.3.1 (Baidu; P1 6.0)",
                "Mozilla/5.0 (iPhone 92; CPU iPhone OS 10_3_2 like Mac OS X) AppleWebKit/603.2.4 (KHTML, like Gecko) Version/10.0 MQQBrowser/7.7.2 Mobile/14F89 Safari/8536.25 MttCustomUA/2 QBWebViewType/1 WKType/1",
                "Mozilla/5.0 (Linux; U; Android 7.0; zh-CN; ZUK Z2121 Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.89 UCBrowser/11.6.8.952 Mobile Safari/537.36",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Mobile/15A372 MicroMessenger/6.5.17 NetType/WIFI Language/zh_HK",
                "Mozilla/5.0 (Linux; U; Android 6.0.1; zh-CN; SM-C7000 Build/MMB29M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.89 UCBrowser/11.6.2.948 Mobile Safari/537.36",
                "MQQBrowser/5.3/Mozilla/5.0 (Linux; Android 6.0; TCL 580 Build/MRA58K; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/52.0.2743.98 Mobile Safari/537.36",
                "Mozilla/5.0 (iPhone; CPU iPhone OS 10_2 like Mac OS X) AppleWebKit/602.3.12 (KHTML, like Gecko) Mobile/14C92 MicroMessenger/6.5.16 NetType/WIFI Language/zh_CN","" +
                "Mozilla/5.0 (Linux; U; Android 5.1.1; zh-cn; MI 4S Build/LMY47V) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/53.0.2785.146 Mobile Safari/537.36 XiaoMi/MiuiBrowser/9.1.3","" +
                "Mozilla/5.0 (Linux; U; Android 7.0; zh-CN; SM-G9550 Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/40.0.2214.89 UCBrowser/11.7.0.953 Mobile Safari/537.36",
                "Mozilla/5.0 (Linux; Android 5.1; m3 note Build/LMY47I; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/48.0.2564.116 Mobile Safari/537.36 T7/9.3 baiduboxapp/9.3.0.10 (Baidu; P1 5.1)")
        val UA_KEY = "user-agent"

        val TIMEOUT_LENGTH: Long = 30
        var mAPI_SERVICE: API? = null
        private var sGson: Gson? = null
        private var sHttpClient: OkHttpClient? = null
        val BASEURL = "http://www.mzitu.com"

        fun init() {
            if (mAPI_SERVICE == null) {
                val retrofit = Retrofit.Builder()
                        .client(httpClient())
                        .addConverterFactory(JspoonConverterFactory.create())
                                //.add(FruitConverterFactory.create(fruit()), Html::class.java)
                                //.add(GsonConverterFactory.create(gson()), Json::class.java))
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(BASEURL)
                        .build()
                mAPI_SERVICE = retrofit.create<API>(API::class.java)
            }
        }

        fun gson(): Gson {
            if (sGson == null) {
                sGson = GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
            }
            return sGson!!
        }

        /*fun fruit(): Fruit {
            if (sFruit == null) {
                sFruit = Fruit()
            }
            return sFruit!!
        }*/

        fun httpClient(): OkHttpClient {
            if (sHttpClient == null) {
                sHttpClient = OkHttpClient.Builder()
                        .connectTimeout(TIMEOUT_LENGTH, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
                        .addInterceptor(ConfigInterceptor())
                        .addInterceptor(HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build()
            }
            return sHttpClient!!
        }

    }


    private class ConfigInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val ua = request.header(UA_KEY)
            if (TextUtils.isEmpty(ua)) {
                request = request.newBuilder()
                        .addHeader("user-agent", agents[Random().nextInt(agents.size - 1)])
                        .build()
            }
            return chain.proceed(request)
        }
    }

    fun get(): API? {
        return mAPI_SERVICE
    }


}