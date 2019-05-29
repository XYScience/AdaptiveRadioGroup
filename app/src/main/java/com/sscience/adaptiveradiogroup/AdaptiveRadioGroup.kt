package com.sscience.adaptiveradiogroup

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RadioGroup

/**
 * @author SScience
 * @description 可自动换行的 RadioGroup
 * @email chentushen.science@gmail.com
 * @data 2019/5/29
 */
class AdaptiveRadioGroup : RadioGroup {

    /**
     * 0: start, 1: end
     */
    private var gravityDirection: Int = 0
    private var marginVertical: Float = 10F
    private var marginHorizontal: Float = 10F

    constructor(context: Context) : super(context) {
        init(null)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        init(attributeSet)
    }

    private fun init(attributeSet: AttributeSet?) {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.AdaptiveRadioGroup)

        gravityDirection = a.getInt(R.styleable.AdaptiveRadioGroup_gravity, 0)
        setGravityDirection(gravityDirection)
        marginVertical = a.getDimension(R.styleable.AdaptiveRadioGroup_margin_vertical, 10F)
        marginHorizontal = a.getDimension(R.styleable.AdaptiveRadioGroup_margin_horizontal, 10F)

        a.recycle()
    }

    fun setGravityDirection(gravity: Int) {
        gravityDirection = if (gravity == 0) 0 else 1

        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //获取最大宽度
        val maxWidth = View.MeasureSpec.getSize(widthMeasureSpec) - paddingStart - paddingEnd
        //获取Group中的Child数量
        val childCount = childCount
        //设置Group的左边距，下面也会使用x计算每行所占的宽度
        var x = 0
        //设置Group的上边距，下面也会使用y计算Group所占的高度
        var y = 30
        //设置Group的行数
        var row = 0
        val gravityStart = gravityDirection == 0
        val paddingVertical = if (gravityStart) paddingTop else paddingBottom
        val paddingHorizontal = if (gravityStart) paddingStart else paddingEnd
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if (child.visibility != View.GONE) {
                child.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
                //重新计算child的宽高
                var width = child.measuredWidth
                val height = child.measuredHeight
                //添加到X中，(width+10) 设置child左边距
                x += width + if (index == 0) paddingHorizontal else marginVertical.toInt()
                //行数*child高度+这次child高度=现在Group的高度,(height + 10)设置child上边距
                y = paddingVertical + row * height + (height + if (row == 0) 0 else row * marginHorizontal.toInt())
                //当前行宽X大于Group的最大宽度时，进行换行
                if (x > maxWidth) {
                    //当index不为0时，进行row++，防止FirstChild出现大于maxWidth时,提前进行row++
                    if (index != 0)
                        row++
                    //child的width大于maxWidth时，重新设置child的width为最大宽度
                    if (width >= maxWidth) {
                        width = maxWidth - 30
                    }
                    //重新设置当前X
                    x = width + paddingHorizontal
                    //重新设置现在Group的高度
                    y = paddingVertical + row * height + (height + row * marginHorizontal.toInt())
                }
            }
        }
        y += paddingBottom
        // 设置容器所需的宽度和高度
        setMeasuredDimension(maxWidth, y)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childCount = childCount
        val maxWidth = r - l - paddingStart - paddingEnd
        var x = 0
        var y = 0
        var row = 0
        val gravityStart = gravityDirection == 0
        val paddingVertical = if (gravityStart) paddingTop else paddingBottom
        val paddingHorizontal = if (gravityStart) paddingStart else paddingEnd
        for (index in 0 until childCount) {
            val child = this.getChildAt(index)
            if (child.visibility != View.GONE) {
                var width = child.measuredWidth
                val height = child.measuredHeight
                x += width + if (index == 0) paddingHorizontal else marginVertical.toInt()
                y = paddingVertical + row * height + (height + if (row == 0) 0 else row * marginHorizontal.toInt())
                if (x > maxWidth) {
                    if (index != 0)
                        row++
                    if (width >= maxWidth) {
                        width = maxWidth - 30
                    }
                    x = width + paddingHorizontal
                    y = paddingVertical + row * height + (height + row * marginHorizontal.toInt())
                }
                val left = if (gravityStart) x - width else maxWidth - x + paddingStart + paddingEnd
                val right = if (gravityStart) x else maxWidth - x + width + paddingStart + paddingEnd
                child.layout(left, y - height, right, y)
            }
        }
    }

}
