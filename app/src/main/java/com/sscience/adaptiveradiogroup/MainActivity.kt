package com.sscience.adaptiveradiogroup

import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author SScience
 * @description 可自动换行的 RadioGroup
 * @email chentushen.science@gmail.com
 * @data 2019/5/29
 */

class MainActivity : AppCompatActivity() {

    private var num = 11

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = window.decorView
            val lsu = decorView.systemUiVisibility
            decorView.systemUiVisibility = lsu or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            window.navigationBarColor = Color.WHITE
        }

        initItem(num)

        add.setOnClickListener {
            initItem(1)
        }

        reset.setOnClickListener {
            radio_group.removeAllViews()
            initItem(1)
        }

        left.setOnClickListener {
            radio_group.setGravityDirection(0)
        }

        right.setOnClickListener {
            radio_group.setGravityDirection(1)
        }

    }

    fun initItem(num: Int) {
        for (i in 0 until num) {
            val radioButton = RadioButton(this)
            radioButton.text = "按钮按钮: ${(radio_group.childCount * 2)}"
            radioButton.background = getDrawable(R.drawable.radio_bg)
            radioButton.setButtonDrawable(0)
            radioButton.gravity = Gravity.CENTER
            radio_group.addView(radioButton)
        }
    }
}
