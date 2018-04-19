package com.guardanis.blank.tools.views

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView

import com.guardanis.imageloader.ImageUtils

object ViewHelper {

    fun setBackgroundResourceAndKeepPadding(v: View, resourceId: Int) {
        val top = v.paddingTop
        val left = v.paddingLeft
        val right = v.paddingRight
        val bottom = v.paddingBottom

        v.setBackgroundResource(resourceId)
        v.setPadding(left, top, right, bottom)
    }

    fun setBackgroundDrawableAndKeepPadding(v: View, drawable: Drawable) {
        val top = v.paddingTop
        val left = v.paddingLeft
        val right = v.paddingRight
        val bottom = v.paddingBottom

        setBackgroundDrawable(v, drawable)
        v.setPadding(left, top, right, bottom)
    }

    @SuppressLint("NewApi")
    fun setBackgroundDrawable(v: View, drawable: Drawable) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
            v.setBackgroundDrawable(drawable)
        else
            v.background = drawable
    }

    @SuppressLint("NewApi")
    fun setBackgroundRippleDrawable(v: View, resId: Int) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
            v.setBackgroundResource(resId)
            return
        }

        try {
            val rippledImage = RippleDrawable(
                    ColorStateList.valueOf(resId),
                    v.context.resources.getDrawable(resId),
                    null)

            setBackgroundDrawable(v, rippledImage)
        }
        catch (e: Throwable) { e.printStackTrace() }
    }

    @SuppressLint("NewApi")
    fun setImageAlpha(imageView: ImageView, alpha: Int) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
            imageView.setAlpha(alpha)
        else
            imageView.imageAlpha = alpha
    }

    @SuppressLint("NewApi")
    fun getDrawable(context: Context, resourceId: Int): Drawable {
        return if (Build.VERSION.SDK_INT < 22)
            context.resources.getDrawable(resourceId)
        else
            context.resources.getDrawableForDensity(resourceId, -1, context.theme)
    }

    @SuppressLint("NewApi")
    fun disableHardwareAcceleration(v: View) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
            v.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    fun addGlobalLayoutRequest(v: View, runnable: (() -> Unit)?) {
        v.viewTreeObserver
                .addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        runnable?.invoke()

                        removeOnGlobalLayoutListener(v, this)
                    }
                })
    }

    @SuppressLint("NewApi")
    fun removeOnGlobalLayoutListener(v: View, listener: ViewTreeObserver.OnGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT < 16)
            v.viewTreeObserver
                    .removeGlobalOnLayoutListener(listener)
        else
            v.viewTreeObserver
                    .removeOnGlobalLayoutListener(listener)
    }

    fun adjustScrollingInputs(activity: Activity) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        else
            activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    fun validateColor(color: String): Boolean {
        return color.startsWith("#") && (color.length == 7 || color.length == 9)
    }

    fun getPxFromDip(context: Context, dip: Int): Int {
        return (dip * context.resources.displayMetrics.density + 0.5f).toInt()
    }

    fun closeSoftInputKeyboard(et: EditText) {
        et.clearFocus()
        closeSoftInputKeyboard(et)
    }

    fun closeSoftInputKeyboard(v: View) {
        (v.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .hideSoftInputFromWindow(v.applicationWindowToken, 0)
    }

    fun closeSoftInputKeyboardOnLayout(view: View) {
        view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                removeOnGlobalLayoutListener(view, this)

                view.postDelayed({ closeSoftInputKeyboard(view) }, 200)
            }
        })

        view.requestLayout()
    }

    fun openSoftInputKeyboard(et: EditText) {
        (et.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .toggleSoftInputFromWindow(et.applicationWindowToken, InputMethodManager.SHOW_FORCED, 0)
    }

    fun openSoftInputKeyboardOnLayout(et: EditText) {
        et.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                removeOnGlobalLayoutListener(et, this)

                et.postDelayed({ openSoftInputKeyboard(et) }, 200)
            }
        })

        et.requestLayout()
    }

    fun getActivitySnapshot(activity: Activity): Bitmap {
        val view = activity.window.decorView
        view.isDrawingCacheEnabled = true

        val statusBar = Rect()

        val bmap = view.drawingCache

        activity.window.decorView.getWindowVisibleDisplayFrame(statusBar)

        val snapshot = Bitmap.createBitmap(bmap, 0, statusBar.top, bmap.width, bmap.height - statusBar.top, null, true)

        view.isDrawingCacheEnabled = false
        return snapshot
    }

    fun enableChildren(group: ViewGroup?) {
        if (group == null) return

        for (i in 0 until group.childCount) {
            val v = group.getChildAt(i)

            (v as? ViewGroup)
                    ?.let({ enableChildren(it) })

            v.isEnabled = true
        }
    }

    fun disableChildren(group: ViewGroup?) {
        if (group == null) return

        for (i in 0 until group.childCount) {
            val v = group.getChildAt(i)

            (v as? ViewGroup)
                    ?.let({ disableChildren(it) })

            v.isEnabled = false
        }
    }

    fun getValueInRange(value: Int, range: IntArray): Int {
        return when {
            value < range[0] -> range[0]
            range[1] < value -> range[1]
            else -> value
        }
    }

    fun isSoftKeyboardFinishedAction(view: TextView, action: Int, event: KeyEvent?): Boolean {
        // Some devices return null event on editor actions for Enter Button
        return (action == EditorInfo.IME_ACTION_DONE || action == EditorInfo.IME_ACTION_GO || action == EditorInfo.IME_ACTION_SEND)
                && (event == null || event.action == KeyEvent.ACTION_DOWN)
    }

    fun addAfterTextChangeListener(textView: TextView, afterTextChanged: (() -> Unit)?): TextWatcher {
        val watcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                afterTextChanged?.invoke()
            }
        }

        textView.addTextChangedListener(watcher)

        return watcher
    }

    fun addFinishedActionListener(textView: TextView, onFinished: (() -> Unit)?) {
        textView.setOnEditorActionListener({ view, action, keyEvent ->
            if (action == EditorInfo.IME_ACTION_NEXT || isSoftKeyboardFinishedAction(view, action, keyEvent)) {
                closeSoftInputKeyboard(textView)

                onFinished?.invoke()

                return@setOnEditorActionListener true
            }

            false
        })
    }

    fun setStateListColorBackground(view: View, normal: Int, pressed: Int) {
        val drawable = ImageUtils.buildStateListDrawable(ColorDrawable(normal), ColorDrawable(pressed))

        setBackgroundDrawableAndKeepPadding(view, drawable)
    }
}
