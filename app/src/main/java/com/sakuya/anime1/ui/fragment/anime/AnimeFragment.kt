package com.sakuya.anime1.ui.fragment.anime

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.Drawable
import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.Nullable
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import com.evrencoskun.tableview.TableView
import com.evrencoskun.tableview.adapter.AbstractTableAdapter
import com.google.gson.Gson
import com.sakuya.anime1.R
import com.sakuya.anime1.adapter.AnimeRecyclerAdapter
import com.sakuya.anime1.adapter.DayRecyclerAdapter
import com.sakuya.anime1.entity.TableEntity
import com.sakuya.anime1.entity.bean.newAnime
import com.sakuya.anime1.entity.bean.tableView
import com.sakuya.anime1.helper.AnimeHelper
import com.sakuya.anime1.ui.view.StartSnapHelper
import com.sakuya.anime1.ui.view.coloredshadow.ShadowImageView
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_anime.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AnimeFragment : SupportFragment() , ViewSwitcher.ViewFactory {

    override fun makeView(): View {
        val i = ImageView(context)
        i.scaleType = ImageView.ScaleType.CENTER_CROP
        i.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        return i
    }

    private lateinit var table_id:TextView
    private lateinit var sheet :BottomSheetFragment
    private lateinit var recyclerView:RecyclerView
    private lateinit var day_recycler:RecyclerView
    private lateinit var btn_all:ShadowImageView
    //private lateinit var bg:ImageView
    private lateinit var switcher: ImageSwitcher
    private val animeHelper = AnimeHelper
    private lateinit var multi : MultiTransformation<Bitmap>
    var outputPortDragging  = false
    val dlist = arrayOf(R.drawable.jb_bl,R.drawable.jb_red,R.drawable.jb_d,R.drawable.jb_gr,R.drawable.jb_org,R.drawable.jb_yel,R.drawable.jb_z)
    private var randNum = 0
    private var list: MutableList<newAnime> ?= null

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
        click()
        return view
    }

    private fun initView(view:View) {
        sheet = BottomSheetFragment()
        btn_all = view.findViewById(R.id.btn_all)
        //bg = view.findViewById(R.id.bg)
        switcher = view.findViewById(R.id.switcher)
        switcher.setFactory(this)
        switcher.inAnimation = AnimationUtils.loadAnimation(context,
            R.anim.fade_in)
        switcher.outAnimation = AnimationUtils.loadAnimation(context,
            R.anim.fade_out)
        switcher.setImageResource(dlist!![randNum])
        setMargin(btn_all)
        table_id = view.findViewById(R.id.table_id)
        initializeTableView()
        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(context).apply { orientation = OrientationHelper.HORIZONTAL }
        StartSnapHelper().attachToRecyclerView(recyclerView)

        day_recycler = view.findViewById(R.id.day_recycler)
        day_recycler.layoutManager = LinearLayoutManager(context).apply { orientation = OrientationHelper.HORIZONTAL }
        StartSnapHelper().attachToRecyclerView(day_recycler)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when(newState){
                    RecyclerView.SCROLL_STATE_SETTLING ->{
                        if (outputPortDragging){
                            outputPortDragging = false
                        }
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING ->{
                        outputPortDragging = true
                    }
                    RecyclerView.SCROLL_STATE_IDLE ->{
                        if (!outputPortDragging){
//                            var outputPortLayoutMgr = recyclerView.layoutManager as LinearLayoutManager
                            outputPortDragging = false
//                            firstPosition = outputPortLayoutMgr.findFirstVisibleItemPosition()
//                            Glide.with(context!!).load(list!![firstPosition].ImgUrl)
//                                .apply(bitmapTransform(multi))
//                                .transition(DrawableTransitionOptions.withCrossFade())
//                                .into(bg)
                            //loadSwitcher(dlist!![firstPosition])
                            getRandNum()
                            switcher.setImageResource(dlist!![randNum])
                        }
                    }
                }
            }
        })
    }

    private fun getRandNum(){
        var n = (1 until dlist.size).shuffled().last()
        if(n==randNum)
            randNum = when {
                n-1<0 -> n+1
                n+1>=dlist.size -> n-1
                else -> n+1
            }
        else randNum = n
    }

    private fun initializeTableView() {

    }

    private fun click() {
        btn_all.setOnClickListener {
            sheet.show(fragmentManager!!, "DemoBottomSheetFragment")
        }
    }

    fun setMargin(img: ImageView){
        val params = img.layoutParams
        val windowManager = img.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val defaultDisplay = windowManager.defaultDisplay
        val point = Point()
        defaultDisplay.getSize(point)
        val x = point.x
        params.width = x - x/6
        img.layoutParams = params
        if(img is ShadowImageView){
            Glide.with(context!!)
                .load(R.drawable.jbs1)
                .placeholder(R.drawable.test)
                .error(R.drawable.test)
                .into(object : ViewTarget<ImageView, Drawable>(img) {
                    override fun onLoadStarted(placeholder: Drawable?) {
                        super.onLoadStarted(placeholder)
                        img.setImageDrawable(placeholder, withShadow = false)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        super.onLoadCleared(placeholder)
                        img.setImageDrawable(placeholder, withShadow = false)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        img.setImageDrawable(errorDrawable, withShadow = false)
                    }

                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        img.setImageDrawable(resource)
                    }
                })
        }
    }

    private fun initData() {
        queryObjects()
    }

    private fun queryObjects() {
        var bmobQuery: BmobQuery<newAnime> = BmobQuery()
        bmobQuery.findObjects(object : FindListener<newAnime>() {
            override fun done(list: MutableList<newAnime>?, ex: BmobException?) {
                if (ex == null) {
                    this@AnimeFragment.list = list
                    recyclerView.adapter = AnimeRecyclerAdapter(list!!)
                    multi = MultiTransformation<Bitmap>(
                        BlurTransformation(100)
                    )
//                    Glide.with(context!!).load(list!![0].ImgUrl)
//                        .apply(bitmapTransform(multi))
//                        .transition( DrawableTransitionOptions.withCrossFade())
//                        .into(bg)
                    //loadSwitcher(list!![0].ImgUrl!!)
                } else {
                   Log.e("error",ex.message)
                }
            }
        })

        var tableQuery: BmobQuery<tableView> = BmobQuery()
        tableQuery.findObjects(object : FindListener<tableView>() {
            override fun done(list: MutableList<tableView>?, ex: BmobException?) {
                if (ex == null) {
                    val entity = Gson().fromJson(list!![0].tableData,TableEntity::class.java)
                    table_id.text = entity.tableName
                    day_recycler.adapter = DayRecyclerAdapter(entity.tableData)
                } else {
                    Log.e("error",ex.message)
                }
            }
        })
    }

    fun loadSwitcher(url:Int){
        Glide.with(context!!)
            .load(url)
            .dontAnimate()
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .apply(bitmapTransform(multi))
            .into(object : ViewTarget<ImageView, Drawable>(switcher.currentView as ImageView) {
                override fun onLoadStarted(placeholder: Drawable?) {
                    super.onLoadStarted(placeholder)
                    switcher.setImageDrawable(placeholder)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    super.onLoadCleared(placeholder)
                    switcher.setImageDrawable(placeholder)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
                    switcher.setImageDrawable(errorDrawable)
                }

                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                    switcher.setImageDrawable(resource)
                }
            })
    }
}