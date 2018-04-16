package com.i7play.pameizi.activity

import android.Manifest
import android.os.Bundle
import android.os.Handler
import com.i7play.pameizi.R
import com.i7play.supertian.base.BaseActivity
import com.i7play.supertian.ext.jumpToAndFinish
import com.i7play.supertian.ext.toast
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.PermissionListener

class SplashActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initData() {
        AndPermission.with(this).permission(Manifest.permission.WRITE_EXTERNAL_STORAGE).requestCode(10).callback(object : PermissionListener {
            override fun onSucceed(requestCode: Int, grantPermissions: MutableList<String>) {
                when (requestCode) {
                    10 -> {
                        Handler().postDelayed({
                            jumpToAndFinish(MainActivity::class.java)
                        }, 1000)
                    }
                }
            }

            override fun onFailed(requestCode: Int, deniedPermissions: MutableList<String>) {
                when (requestCode) {
                    10 -> {
                        toast("权限申请失败")
                        finish()
                    }
                }
            }

        }).start()
    }

    override fun initListener() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fullScreen()
        super.onCreate(savedInstanceState)
    }
}
