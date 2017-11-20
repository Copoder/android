package com.iunin.demo.platformdemo.ui.widgit;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.iunin.demo.platformdemo.R;


public class AutoCompleteTextViewWithDeleteView extends AppCompatAutoCompleteTextView implements TextWatcher, View.OnTouchListener, View.OnFocusChangeListener {
    public AutoCompleteTextViewWithDeleteView(Context context) {
        super(context);
        init();
    }

    public AutoCompleteTextViewWithDeleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AutoCompleteTextViewWithDeleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        addTextChangedListener(this);
        setOnTouchListener(this);
        setOnFocusChangeListener(this);


    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {


    }

    @Override
    public void afterTextChanged(Editable s) {


    }

    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        if (text.length() == 0) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.delete_end_svg), null);

        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int DRAWABLE_RIGHT = 2;
        Drawable rightIcon = getCompoundDrawables()[DRAWABLE_RIGHT];
        if (rightIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
            int leftEdgeOfRightDrawable = getRight() - getPaddingRight()
                    - rightIcon.getBounds().width();
            if (event.getRawX() >= leftEdgeOfRightDrawable) {
                setText("");
            }
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        }else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, getResources().getDrawable(R.drawable.delete_end_svg), null);
        }

    }
}
