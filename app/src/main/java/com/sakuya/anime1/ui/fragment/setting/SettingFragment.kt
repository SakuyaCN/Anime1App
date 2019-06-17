package com.sakuya.anime1.ui.fragment.setting

import android.app.ActivityOptions
import android.content.Intent
import me.yokeyword.fragmentation.SupportFragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.Nullable
import com.orhanobut.hawk.Hawk
import com.polyak.iconswitch.IconSwitch
import com.sakuya.anime1.R
import com.sakuya.anime1.ui.LoadActivity
import com.skydoves.colorpickerview.ActionMode
import com.skydoves.colorpickerview.ColorPickerView

class SettingFragment : SupportFragment() {
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
             ColorPickerView.Builder(context)
                 .setActionMode(ActionMode.LAST)
            .build()
                 .showContextMenu()
        }
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
    }

    fun reStartActivity(){
        startActivity(Intent(activity!!,LoadActivity::class.java), ActivityOptions.makeSceneTransitionAnimation(activity!!).toBundle())
    }
}