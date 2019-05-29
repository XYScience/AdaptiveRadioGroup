package com.sscience.adaptiveradiogroup

import android.content.Context

/**
 * @author SScience
 * @description
 * @email chentushen.science@gmail.com
 * @data 2019/5/29
 */
class UiUtil {

    companion object{

        fun dp2px(context: Context, dpValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        fun px2dp(context: Context, pxValue: Float): Int {
            val scale: Float = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }
    }
}
