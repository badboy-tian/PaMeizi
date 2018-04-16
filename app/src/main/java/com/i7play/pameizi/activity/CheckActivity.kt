package com.i7play.pameizi.activity

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.i7play.pameizi.R
import com.i7play.pameizi.util.DownLoadImageService
import com.i7play.pameizi.util.ImageDownLoadCallBack
import com.i7play.supertian.base.BaseActivity
import com.i7play.supertian.ext.toast
import kotlinx.android.synthetic.main.activity_check.*
import java.io.File

class CheckActivity : BaseActivity() {
    var url = ""
    override fun getLayoutId(): Int {
        return R.layout.activity_check
    }

    override fun initData() {
        url = intent.getStringExtra("url")
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "查看图片"

        Glide.with(this).load(url).into(ivImg)
    }

    override fun initListener() {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.menu_down -> {
                val dialog = ProgressDialog(this)
                dialog.setMessage("正在下载...")
                dialog.setCanceledOnTouchOutside(false)
                dialog.show()
                Thread(DownLoadImageService(this, url, object : ImageDownLoadCallBack{
                    override fun onDownLoadSuccess(file: File?) {
                        runOnUiThread {
                            toast(file?.absolutePath!!)
                            dialog.dismiss()
                        }
                    }

                    override fun onDownLoadSuccess(bitmap: Bitmap?) {
                        runOnUiThread {
                            dialog.dismiss()
                        }
                    }

                    override fun onDownLoadFailed() {
                        runOnUiThread {
                            dialog.dismiss()
                            toast("图片下载失败")
                        }
                    }
                })).start()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }
}
