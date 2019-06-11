package com.sakuya.anime1.ui.fragment.anime

import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.jeremyliao.liveeventbus.LiveEventBus
import com.sakuya.anime1.R
import com.sakuya.anime1.adapter.AnimeRecyclerAdapter
import com.sakuya.anime1.adapter.ViewPagerAdapter
import com.sakuya.anime1.entity.AnimeEntity
import com.sakuya.anime1.entity.AnimeEntityList
import com.sakuya.anime1.helper.AnimeHelper
import com.sakuya.anime1.ui.view.StartSnapHelper
import org.w3c.dom.Text

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
        observe()
        initData()
        return view
    }

    private fun initView(view:View) {
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

    private fun observe(){
        LiveEventBus.get()
            .with("main_data", AnimeEntityList::class.java)
            .observeForever(observer)
    }

    private fun initData() {
        animeHelper.getInstance().getMainData()
    }

    val observer = Observer<AnimeEntityList> {
        recyclerView.adapter = AnimeRecyclerAdapter(it.list)
    }
}