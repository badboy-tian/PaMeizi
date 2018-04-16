package com.i7play.pameizi


import android.support.v4.app.Fragment
import com.i7play.supertian.base.BaseLazyFragment


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseLazyFragment() {
    override fun fetchData() {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
    }

    companion object {
        fun newInstance(xuanhuan: ItemType): HomeFragment{
            val homeFragment = HomeFragment()

            return homeFragment
        }
    }
}