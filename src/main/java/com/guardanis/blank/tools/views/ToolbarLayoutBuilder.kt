package com.guardanis.blank.tools.views

import android.graphics.Color
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.guardanis.blank.R
import com.guardanis.imageloader.views.SVGImageView

class ToolbarLayoutBuilder {

    val toolbar: Toolbar

    val parent: LinearLayout
        get() = toolbar.findViewById<LinearLayout>(R.id.base__toolbar_parent)

    @JvmOverloads
    constructor(toolbar: Toolbar, inflatedLayoutResId: Int = R.layout.base__toolbar) {
        this.toolbar = toolbar

        val customView = LayoutInflater.from(toolbar.context)
                .inflate(inflatedLayoutResId, toolbar, false)

        toolbar.removeAllViews()
        toolbar.addView(customView)

        try {
            toolbar.setPadding(0, 0, 0, 0) // Fix for tablets
            toolbar.setContentInsetsAbsolute(0, 0)
        }
        catch (e: Throwable) { e.printStackTrace() }
    }

    fun addTitle(textResId: Int, onClickListener: (() -> Unit)?): ToolbarLayoutBuilder {
        return addTitle(toolbar.context.getString(textResId), onClickListener)
    }

    fun addTitle(text: String, onClickListener: (() -> Unit)?): ToolbarLayoutBuilder {
        val textTitle = inflateTitleText()

        textTitle.text = text
        textTitle.setOnClickListener({ onClickListener?.invoke() })

        addTitle(textTitle)

        return this
    }

    fun addTitleSvg(assetFileName: String, onClickListener: (() -> Unit)?): ToolbarLayoutBuilder {
        val svg = buildSvgImageView(assetFileName, onClickListener)
        addTitle(svg)

        return this
    }

    @JvmOverloads
    fun addOptionText(text: String, onClickListener: (() -> Unit)? = null): ToolbarLayoutBuilder {
        val textOption = buildTextOption(text, onClickListener)

        addOption(textOption)

        return this
    }

    fun addOptionSvg(assetFileName: String, onClickListener: (() -> Unit)?): ToolbarLayoutBuilder {
        val svg = buildSvgImageView(assetFileName, onClickListener)
        addOption(svg)

        return this
    }

    fun addOptionImage(resId: Int, onClickListener: (() -> Unit)?): ToolbarLayoutBuilder {
        val svg = inflateOptionImage()

        svg.setImageResource(resId)
        svg.setOnClickListener({ onClickListener?.invoke() })

        addOption(svg)

        return this
    }

    fun buildSvgImageView(assetFileName: String, onClickListener: (() -> Unit)?): SVGImageView {
        val svg = inflateOptionImage()
        svg.setImageAsset(assetFileName)
        svg.setOnClickListener({ onClickListener?.invoke() })

        return svg
    }

    @JvmOverloads
    fun buildTextOption(text: String, onClickListener: (() -> Unit)? = null): TextView {
        val textView = inflateOptionText()
        textView.text = text
        textView.setOnClickListener({ onClickListener?.invoke() })

        if (onClickListener != null)
            ViewHelper.setBackgroundResourceAndKeepPadding(textView, R.drawable.base__button_transparent)

        return textView
    }

    fun inflateTitleText(): TextView {
        return LayoutInflater.from(toolbar.context)
                .inflate(R.layout.base__toolbar_title_text, toolbar, false) as TextView
    }

    fun inflateOptionText(): TextView {
        return LayoutInflater.from(toolbar.context)
                .inflate(R.layout.base__toolbar_option_text, toolbar, false) as TextView
    }

    fun inflateOptionImage(): SVGImageView {
        return LayoutInflater.from(toolbar.context)
                .inflate(R.layout.base__toolbar_option_svg, toolbar, false) as SVGImageView
    }

    fun addOption(view: View): View {
        parent.addView(view)

        return view
    }

    fun addTitle(view: View): View {
        toolbar.findViewById<LinearLayout>(R.id.base__toolbar_title_parent)
                .addView(view)

        return view
    }

    fun setTransparentBackground(): ToolbarLayoutBuilder {
        return setBackgroundColor(Color.TRANSPARENT)
    }

    fun setBackgroundColor(color: Int): ToolbarLayoutBuilder {
        parent.setBackgroundColor(color)

        return this
    }

    fun setBackgroundResource(resourceId: Int): ToolbarLayoutBuilder {
        parent.setBackgroundResource(resourceId)

        return this
    }

    fun hide(): ToolbarLayoutBuilder {
        parent.visibility = View.GONE

        return this
    }

}
