package com.i7play.pameizi


import android.support.v7.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter
import com.i7play.pameizi.activity.CheckActivity
import com.i7play.pameizi.bean.ZiPaiResp
import com.i7play.pameizi.network.HttpMethods
import com.i7play.supertian.adapter.AutoCommonGridAdapter
import com.i7play.supertian.base.BaseLazyFragment
import com.i7play.supertian.ext.addTo
import com.i7play.supertian.network.BaseObserver
import com.zhy.adapter.recyclerview.base.ViewHolder
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_zipai.view.*


/**
 *
 */
class HomeFragment : BaseLazyFragment() {
    var pageNum = -1
    val datas = arrayListOf<ZiPaiResp.Item>()
    override fun fetchData() {
        loadData(true, false)
    }

    fun loadData(isFirst: Boolean, isRefresh: Boolean) {
        val ob = object : BaseObserver<ZiPaiResp>(activity!!) {
            override fun _onNext(t: ZiPaiResp) {
                pageNum = t.pageNum
                if (isRefresh) {
                    datas.clear()
                }
                datas.addAll(t.items)

                list.adapter.notifyDataSetChanged()
                list.refreshComplete(datas.size)
            }

            override fun onSubscribe(d: Disposable) {
                super.onSubscribe(d)
                d.addTo(compositeDisposable)
                if (isFirst) {
                    show("加载中...")
                }else{
                    list.refreshComplete(datas.size)
                }
            }
        }

        HttpMethods.instance.zipai(ob, pageNum)
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        list.layoutManager = GridLayoutManager(activity, 3)
        val adapter = LRecyclerViewAdapter(object : AutoCommonGridAdapter<ZiPaiResp.Item>(activity!!, R.layout.item_zipai, datas) {
            override fun convert(holder: ViewHolder?, t: ZiPaiResp.Item?, position: Int) {
                if (holder == null || t == null) return

                Glide.with(this@HomeFragment).load(t.imgUrl).into(holder.convertView.ivImg)
                holder.convertView.tvTitle.text = t.time

                holder.convertView.ivImg.setOnClickListener {
                    jumpTo(CheckActivity::class.java, "url", t.imgUrl)
                }
            }
        })

        list.adapter = adapter
    }

    override fun initListener() {
        super.initListener()
        list.setOnRefreshListener {
            pageNum = -1
            loadData(false, true)
        }

        list.setOnLoadMoreListener {
            if (pageNum - 1 == 0) {
                list.setNoMore(true)
                return@setOnLoadMoreListener
            }

            pageNum--
            loadData(false, false)
        }
    }

    companion object {
        fun newInstance(xuanhuan: ItemType): HomeFragment {
            val homeFragment = HomeFragment()

            return homeFragment
        }
    }
}