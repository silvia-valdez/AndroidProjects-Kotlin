package com.silviavaldez.sampleapp.helpers

import android.app.Activity
import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Typeface helper.
 * Created by Silvia Valdez on 22/05/18.
 */
class TypefaceHelper(private val context: Activity) {

    // TODO: Implement typeface in the whole app

    val regular: Typeface
    val italic: Typeface
    val hairline: Typeface
    val hairlineItalic: Typeface
    val light: Typeface
    val lightItalic: Typeface
    val bold: Typeface
    val boldItalic: Typeface
    val black: Typeface
    val blackItalic: Typeface

    private val tag = TypefaceHelper::class.java.simpleName

    init {
        val strRegular = "fonts/lato_regular.ttf"
        this.regular = getTypeface(strRegular)

        val strItalic = "fonts/lato_italic.ttf"
        this.italic = getTypeface(strItalic)

        val strHairline = "fonts/lato_hairline.ttf"
        this.hairline = getTypeface(strHairline)

        val strHairlineItalic = "fonts/lato_hairline_italic.ttf"
        this.hairlineItalic = getTypeface(strHairlineItalic)

        val strLight = "fonts/lato_light.ttf"
        this.light = getTypeface(strLight)

        val strLightItalic = "fonts/lato_light_italic.ttf"
        this.lightItalic = getTypeface(strLightItalic)

        val strBold = "fonts/lato_bold.ttf"
        this.bold = getTypeface(strBold)

        val strBoldItalic = "fonts/lato_bold_italic.ttf"
        this.boldItalic = getTypeface(strBoldItalic)

        val strBlack = "fonts/lato_black.ttf"
        this.black = getTypeface(strBlack)

        val strBlackItalic = "fonts/lato_black_italic.ttf"
        this.blackItalic = getTypeface(strBlackItalic)
    }

    fun overrideAllTypefaces() {
        try {
            val view = context.window.decorView.rootView
            overrideTypefaces(view, regular)
        } catch (ex: Exception) {
            Log.e(tag, "Attempting to override all typefaces", ex)
        }
    }

    fun overrideAllTypefaces(view: View) {
        try {
            overrideTypefaces(view, regular)
        } catch (ex: Exception) {
            Log.e(tag, "Attempting to override all typefaces", ex)
        }
    }

    private fun overrideTypefaces(view: View, typeface: Typeface) {
        try {
            if (view is ViewGroup) {
                for (i in 0 until view.childCount) {
                    val child = view.getChildAt(i)
                    overrideTypefaces(child, typeface)
                }
            } else if (view is TextView) {
                view.typeface = typeface
            }
        } catch (ex: Exception) {
            Log.e(tag, "Attempting to override a typeface", ex)
        }
    }

    private fun getTypeface(typefaceName: String): Typeface {
        return Typeface.createFromAsset(context.resources.assets, typefaceName)
    }
}
