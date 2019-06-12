package com.sakuya.anime1.ui.fragment.anime

import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.sakuya.anime1.R
import com.sakuya.anime1.adapter.AnimeRecyclerAdapter
import com.sakuya.anime1.entity.bean.newAnime
import com.sakuya.anime1.helper.AnimeHelper
import com.sakuya.anime1.ui.view.StartSnapHelper

class AnimeFragment : SupportFragment() {

    private lateinit var recyclerView:RecyclerView
    private val animeHelper = AnimeHelper
    var outputPortDragging  = false
    var outputPortScrolling = false

    companion object{
        fun newInstance(): AnimeFragment {
            val args = Bundle()

            val fragment = AnimeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_anime, container, false)
        initView(view)
        initData()
        return view
    }

    private fun initView(view:View) {
        val sheet = BottomSheetFragment()
        sheet.show(fragmentManager!!, "DemoBottomSheetFragment")
        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context).apply { orientation = OrientationHelper.HORIZONTAL }
        StartSnapHelper().attachToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                var firstPosition = 0
                when(newState){
                    RecyclerView.SCROLL_STATE_SETTLING ->{
                        if (!outputPortDragging && !outputPortScrolling){
                            outputPortScrolling = true
                        }
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING ->{
                        outputPortDragging = true
                    }
                    RecyclerView.SCROLL_STATE_IDLE ->{
                        if (!outputPortDragging && outputPortScrolling){
                            var outputPortLayoutMgr = recyclerView.layoutManager as LinearLayoutManager
                            outputPortDragging = false
                            outputPortScrolling = false
                            firstPosition = outputPortLayoutMgr.findFirstVisibleItemPosition()
                            Log.e("aaaaaaaa","postion = ${firstPosition}")
                        }
                    }
                }
            }
        })
    }

    private fun initData() {
        queryObjects()
        //animeHelper.getInstance().getMainData()
    }

    private fun queryObjects() {
        var bmobQuery: BmobQuery<newAnime> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<newAnime>() {
            override fun done(list: MutableList<newAnime>?, ex: BmobException?) {
                if (ex == null) {
                    recyclerView.adapter = AnimeRecyclerAdapter(list!!)
                } else {
                   Log.e("error",ex.message)
                }
            }
        })
    }
}