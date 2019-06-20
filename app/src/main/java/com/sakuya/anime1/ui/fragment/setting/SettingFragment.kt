package com.sakuya.anime1.ui.fragment.setting

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.core.view.setMargins
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.orhanobut.hawk.Hawk
import com.polyak.iconswitch.IconSwitch
import com.sakuya.anime1.R
import com.sakuya.anime1.ui.LoadActivity
import com.sakuya.anime1.utils.SizeUtil
import com.sakuya.anime1.utils.ViewUtils
import com.skydoves.colorpickerview.ActionMode
import com.skydoves.colorpickerview.ColorPickerView

class SettingFragment : SupportFragment() {

    private var deftcolors :Array<String> = Hawk.get("colors")
    private lateinit var icon_switch: IconSwitch
    private lateinit var view_bg_color:View
    companion object{
        fun newInstance(): SettingFragment {
            val args = Bundle()

            val fragment = SettingFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Nullable
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        initView(view)
        initClick()
        initHawk()
        return view
    }

    private fun initClick() {
        view_bg_color.setOnClickListener {
            BottomSheetDialog(context!!).apply {
                this.setCanceledOnTouchOutside(true)
                this.setContentView(LayoutInflater.from(activity).inflate(R.layout.color_bottom_sheet, null)
                    .apply {
                        this.findViewById<FlexboxLayout>(R.id.flex_box).apply {
                            deftcolors.forEach {
                                this.addView(createNewFlexItemTextView(it))
                            }
                        }
                    })
            }.show()
        }
    }

    private fun createNewFlexItemTextView(str:String): View {
        val view = LinearLayout(context)
        view.gravity = Gravity.CENTER
        if(str == Hawk.get("card_color")){
            view.addView(TextView(context).apply {
                this.text = "当前"
                this.textSize = 14f
                this.setTextColor(Color.WHITE)
            })
        }
        view.setBackgroundColor(Color.parseColor(str))
        val layoutParams = FlexboxLayout.LayoutParams(
            SizeUtil.dp2Px(50),
            SizeUtil.dp2Px(50)
        )
        val margin = SizeUtil.dp2Px(10)
        layoutParams.setMargins(margin)
        view.layoutParams = layoutParams
        view.setOnClickListener {
            Hawk.put("card_color",str)
            reStartActivity()
        }
        return view
    }

    private fun initHawk(){
        if(Hawk.get("isColorful")) {
            icon_switch.checked = IconSwitch.Checked.LEFT
        }
        else {
            icon_switch.checked = IconSwitch.Checked.RIGHT
        }
        icon_switch.setCheckedChangeListener {
            if(Hawk.get("isColorful")) {
                Hawk.put("isColorful", false)
            }
            else {
                Hawk.put("isColorful", true)
            }
            reStartActivity()
        }
    }

    private fun initView(view:View) {
        icon_switch = view.findViewById(R.id.icon_switch)
        view_bg_color = view.findViewById(R.id.view_bg_color)
        icon_switch.setIconSize(10)
        view_bg_color.setBackgroundColor(Color.parseColor(Hawk.get("card_color")))
    }

    fun reStartActivity(){
        startActivity(Intent(activity!!,LoadActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(activity!!).toBundle())
    }
}