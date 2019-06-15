package com.sakuya.anime1.ui.fragment.anime

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle
import android.util.Log
import android.util.Size
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
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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
import com.sakuya.anime1.utils.SizeUtil
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
    private var animeView: View? = null
    private lateinit var table_id:TextView
    private lateinit var sheet :BottomSheetFragment
    private lateinit var recyclerView:RecyclerView
    private lateinit var day_recycler:RecyclerView
    private lateinit var btn_all:ImageSwitcher
    private lateinit var switcher: ImageSwitcher
    private val animeHelper = AnimeHelper
    private lateinit var multi : MultiTransformation<Bitmap>
    var outputPortDragging  = false
    private var list: MutableList<newAnime> ?= null
    private var colors = arrayOf(intArrayOf(Color.parseColor("#e0c3fc"),Color.parseColor("#8ec5fc")),
        intArrayOf(Color.parseColor("#fbc2eb"),Color.parseColor("#a6c1ee")),intArrayOf(Color.parseColor("#fdcbf1"),Color.parseColor("#e6dee9")),
        intArrayOf(Color.parseColor("#a1c4fd"),Color.parseColor("#a1c4fd")),intArrayOf(Color.parseColor("#a1c4fd"),Color.parseColor("#a1c4fd"))
        ,intArrayOf(Color.parseColor("#a18cd1"),Color.parseColor("#fbc2eb")))
    private var randNum = (1 until colors.size).shuffled().last()
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
        animeView = view
        initView(view)
        initData()
        click()
        return view
    }

    private fun initView(view:View) {
        sheet = BottomSheetFragment()
        btn_all = view.findViewById(R.id.btn_all)
        switchInit()
        setMargin(btn_all)
        table_id = view.findViewById(R.id.table_id)
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
                            //currColorDrawable = GradientDrawable(GradientDrawable.Orientation.BL_TR, colors.get(randNum))
                            setColorsDrawable()
                        }
                    }
                }
            }
        })
    }

    private fun switchInit(){
        switcher = animeView!!.findViewById(R.id.switcher)
        switcher.inAnimation = AnimationUtils.loadAnimation(context,
            R.anim.fade_in)
        switcher.outAnimation = AnimationUtils.loadAnimation(context,
            R.anim.fade_out)
        switcher.setFactory(this@AnimeFragment)
        btn_all.inAnimation = AnimationUtils.loadAnimation(context,
            R.anim.fade_in)
        btn_all.outAnimation = AnimationUtils.loadAnimation(context,
            R.anim.fade_out)
        btn_all.setFactory(this@AnimeFragment)
        setColorsDrawable()
    }

    private fun getRandNum(){
        var n = (1 until colors.size).shuffled().last()
        if(n==randNum)
            randNum = when {
                n-1<0 -> n+1
                n+1>=colors.size -> n-1
                else -> n+1
            }
        else randNum = n
    }

    private fun click() {
        btn_all.setOnClickListener {
            sheet.show(fragmentManager!!, "DemoBottomSheetFragment")
        }
    }

    fun setColorsDrawable(){
        switcher.apply {
            this.setImageDrawable(GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors[randNum]).apply { this.cornerRadius = 20f })
        }
        btn_all.apply {
            this.setImageDrawable(GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors[randNum]).apply { this.cornerRadius = 20f })
        }
//        Glide.with(context!!)
//            .load(currColorDrawable)
//            .skipMemoryCache(false)
//            .transition(GenericTransitionOptions.with(R.anim.fade_out))
//            .apply(bitmapTransform( RoundedCorners(20)))
//            .into(object : ViewTarget<ImageView, Drawable>(btn_all) {
//                override fun onLoadStarted(placeholder: Drawable?) {
//                    super.onLoadStarted(placeholder)
//                    btn_all.setImageDrawable(placeholder, withShadow = false)
//                }
//
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    super.onLoadCleared(placeholder)
//                    btn_all.setImageDrawable(placeholder, withShadow = false)
//                }
//
//                override fun onLoadFailed(errorDrawable: Drawable?) {
//                    super.onLoadFailed(errorDrawable)
//                    btn_all.setImageDrawable(errorDrawable, withShadow = false)
//                }
//
//                override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                    btn_all.setImageDrawable(resource)
//                }
//            })
    }

    fun setMargin(img: ImageSwitcher){
        val params = img.layoutParams
        val windowManager = img.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val defaultDisplay = windowManager.defaultDisplay
        val point = Point()
        defaultDisplay.getSize(point)
        val x = point.x
        params.width = x - x/6 - SizeUtil.dp2Px(40)
        img.layoutParams = params
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
                    recyclerView.adapter.apply {
                        switchInit()
                    }
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