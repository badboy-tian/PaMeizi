package com.i7play.pameizi.network

import com.i7play.pameizi.bean.ZiPaiResp
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Administrator on 2017/8/15.
 * http相关
 */

class HttpMethods//构造方法私有
private constructor() {
    init {
        APIService.init()
    }


    //在访问HttpMethods时创建单例
    private object SingletonHolder {
        val INSTANCE = HttpMethods()
    }

    private fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream -> upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()) }
    }

    fun zipai(subscriber: Observer<ZiPaiResp>, pageNum: Int) {
        var path = "comment-page-$pageNum"
        if (pageNum == -1) {
            path = ""
        }
        APIService.mAPI_SERVICE!!.zipai(path).compose(applySchedulers()).subscribe(subscriber)
    }

    companion object {
        private val DEFAULT_TIMEOUT = 5

        //获取单例
        val instance: HttpMethods
            get() = SingletonHolder.INSTANCE
    }

}
