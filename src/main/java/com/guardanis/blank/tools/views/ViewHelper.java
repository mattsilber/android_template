package com.guardanis.blank.tools.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHelper {

    public static void setBackgroundResourceAndKeepPadding(View v, int resourceId) {
        int top = v.getPaddingTop();
        int left = v.getPaddingLeft();
        int right = v.getPaddingRight();
        int bottom = v.getPaddingBottom();

        v.setBackgroundResource(resourceId);
        v.setPadding(left, top, right, bottom);
    }

    public static void setBackgroundDrawableAndKeepPadding(View v, Drawable drawable) {
        int top = v.getPaddingTop();
        int left = v.getPaddingLeft();
        int right = v.getPaddingRight();
        int bottom = v.getPaddingBottom();

        setBackgroundDrawable(v, drawable);
        v.setPadding(left, top, right, bottom);
    }

    @SuppressLint("NewApi")
    public static void setBackgroundDrawable(View v, Drawable drawable) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
            v.setBackgroundDrawable(drawable);
        else v.setBackground(drawable);
    }

    @SuppressLint("NewApi")
    public static void setBackgroundRippleDrawable(View v, int resId) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP)
            v.setBackgroundResource(resId);
        else{
            try{
                RippleDrawable rippledImage = new RippleDrawable(ColorStateList.valueOf(resId), v.getContext().getResources().getDrawable(resId), null);
                setBackgroundDrawable(v, rippledImage);
            }
            catch(Throwable e){ e.printStackTrace(); }
        }
    }

    @SuppressLint("NewApi")
    public static void setImageAlpha(ImageView imageView, int alpha) {
        if(android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN)
            imageView.setAlpha(alpha);
        else imageView.setImageAlpha(alpha);
    }

    @SuppressLint("NewApi")
    public static Drawable getDrawable(Context context, int resourceId) {
        if(Build.VERSION.SDK_INT < 22)
            return context.getResources().getDrawable(resourceId);
        else
            return context.getResources().getDrawableForDensity(resourceId, -1, context.getTheme());
    }

    @SuppressLint("NewApi")
    public static void disableHardwareAcceleration(View v) {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
            v.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public static void addGlobalLayoutRequest(final View v, Runnable runnable){
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                runnable.run();
                removeOnGlobalLayoutListener(v, this);
            }
        });
    }

    @SuppressLint("NewApi")
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if(Build.VERSION.SDK_INT < 16)
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        else v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
    }

    public static void adjustScrollingInputs(Activity activity) {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        else
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public static boolean validateColor(String color) {
        return color.startsWith("#") && (color.length() == 7 || color.length() == 9);
    }

    public static int getPxFromDip(Context context, int dip) {
        return (int) (dip * context.getResources().getDisplayMetrics().density + 0.5f);
    }

    public static void closeSoftInputKeyboard(EditText et) {
        et.clearFocus();
        closeSoftInputKeyboard((View) et);
    }

    public static void closeSoftInputKeyboard(View v) {
        ((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }

    public static void closeSoftInputKeyboardOnLayout(final View view) {
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(view, this);
                view.postDelayed(new Runnable() {
                    public void run() {
                        closeSoftInputKeyboard(view);
                    }
                }, 200);
            }
        });
        view.requestLayout();
    }

    public static void openSoftInputKeyboard(EditText et) {
        ((InputMethodManager) et.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInputFromWindow(et.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
    }

    public static void openSoftInputKeyboardOnLayout(final EditText et) {
        et.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                removeOnGlobalLayoutListener(et, this);
                et.postDelayed(new Runnable() {
                    public void run() {
                        openSoftInputKeyboard(et);
                    }
                }, 200);
            }
        });
        et.requestLayout();
    }

    public static Bitmap getActivitySnapshot(Activity activity, boolean fade) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        Bitmap bmap = view.getDrawingCache();

        Rect statusBar = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(statusBar);
        Bitmap snapshot = Bitmap.createBitmap(bmap, 0, statusBar.top, bmap.getWidth(), bmap.getHeight() - statusBar.top, null, true);

        if(fade && snapshot != null){
            Canvas canvas = new Canvas(snapshot);
            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#88121212"));
            canvas.drawRect(0, 0, snapshot.getWidth(), snapshot.getHeight(), paint);
        }

        view.setDrawingCacheEnabled(false);
        return snapshot;
    }

    public static void enableChildren(ViewGroup group) {
        if(group == null) return;

        for(int i = 0; i < group.getChildCount(); i++){
            View v = group.getChildAt(i);
            if(v instanceof ViewGroup){
                v.setEnabled(true);
                enableChildren((ViewGroup) v);
            }
            else v.setEnabled(true);
        }
    }

    public static void disableChildren(ViewGroup group) {
        if(group == null) return;

        for(int i = 0; i < group.getChildCount(); i++){
            View v = group.getChildAt(i);
            if(v instanceof ViewGroup){
                v.setEnabled(false);
                disableChildren((ViewGroup) v);
            }
            else v.setEnabled(false);
        }
    }

    public static int getValueInRange(int value, int[] range) {
        if(value < range[0]) return range[0];
        else if(range[1] < value) return range[1];
        else return value;
    }

    public static boolean isSoftKeyboardFinishedAction(TextView view, int action, KeyEvent event) {
        // Some devices return null event on editor actions for Enter Button
        return (action == EditorInfo.IME_ACTION_DONE || action == EditorInfo.IME_ACTION_GO || action == EditorInfo.IME_ACTION_SEND) && (event == null || event.getAction() == KeyEvent.ACTION_DOWN);
    }

    public static TextWatcher addAfterTextChangeListener(final TextView textView, Runnable afterTextChanged){
        TextWatcher watcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(afterTextChanged != null)
                    afterTextChanged.run();
            }
        };

        textView.addTextChangedListener(watcher);

        return watcher;
    }

    public static void addFinishedActionListener(final TextView textView, final Runnable onFinished){
        textView.setOnEditorActionListener((view, action, keyEvent) -> {
            if(action == EditorInfo.IME_ACTION_NEXT || isSoftKeyboardFinishedAction(view, action, keyEvent)){
                closeSoftInputKeyboard(textView);

                if(onFinished != null)
                    onFinished.run();

                return true;
            }

            return false;
        });
    }
}
