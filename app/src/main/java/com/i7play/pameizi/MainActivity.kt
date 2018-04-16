package com.i7play.pameizi

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.MenuItemCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import com.i7play.supertian.base.BaseActivity
import com.i7play.supertian.ext.toast
import com.maning.library.zxing.utils.ZXingUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.share_layout.view.*

class MainActivity : BaseActivity() {
    val titles = arrayListOf("性感", "日本", "台湾", "清纯", "自拍", "日更")

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        setSupportActionBar(toolbar)
        initTabLayout()
    }

    private fun initTabLayout() {
        viewpager.adapter = MainAdapter(supportFragmentManager)
        viewpager.offscreenPageLimit = fragments.size - 1
        tablayout.setupWithViewPager(viewpager)
    }

    override fun initListener() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(menuItem) as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.queryHint = "输入书名/作者"
        //searchView.setIconifiedByDefault(true)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.let {
                    /*ActivityManager.finishActivity(SearchActivity::class.java)
                    jumpTo(SearchActivity::class.java, "words", it)*/
                    return true
                }
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            R.id.action_share -> {
                val fragment = fragments[viewpager.currentItem]
                var url = ""

                val view = layoutInflater.inflate(R.layout.share_layout, null)
                view.ivCode.setImageBitmap(ZXingUtils.createQRImage(url, 600))
                view.tvUrl.text = url
                view.ivCode.setOnClickListener {
                    copy(url)
                }
                AlertDialog.Builder(this).setTitle("分享").setPositiveButton("确定", null).setView(view).create().show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun copy(url: String) {
        val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        manager.primaryClip = ClipData.newPlainText("", url)
        toast("复制成功")
    }

    val fragments = ArrayList<Fragment>()

    private inner class MainAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm), ViewPager.OnPageChangeListener {
        init {
            fragments.add(HomeFragment.newInstance(ItemType.XUANHUAN))
            fragments.add(HomeFragment.newInstance(ItemType.WUXIA))
            fragments.add(HomeFragment.newInstance(ItemType.DUSHI))
            fragments.add(HomeFragment.newInstance(ItemType.LISHI))
            fragments.add(HomeFragment.newInstance(ItemType.WANGYOU))
            fragments.add(HomeFragment.newInstance(ItemType.KEHUAN))
        }


        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }


        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        }

        override fun onPageSelected(position: Int) {

        }

        override fun onPageScrollStateChanged(state: Int) {

        }
    }
}
