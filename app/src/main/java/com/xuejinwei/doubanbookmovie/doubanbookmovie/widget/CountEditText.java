package com.xuejinwei.doubanbookmovie.doubanbookmovie.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xuejinwei.doubanbookmovie.doubanbookmovie.R;
import com.xuejinwei.doubanbookmovie.doubanbookmovie.util.DensityUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xuejinwei on 16/4/29.
 * Email:xuejinwei@outlook.com
 * 长文本编辑框 加入屏蔽表情的inputfilter
 */
public class CountEditText extends LinearLayout {

    private ClearEditText mEditText;
    @SuppressWarnings("FieldCanBeLocal")
    private TextView mTextView;
    int maxChar;

    public CountEditText(Context context) {
        super(context);
        init(context, null);
    }

    public CountEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CountEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        TypedArray attributes = this.getContext().obtainStyledAttributes(attrs, R.styleable.CountEditText);
        maxChar = attributes.getInt(R.styleable.CountEditText_maxChar, 200);
        int textSize = attributes.getDimensionPixelSize(R.styleable.CountEditText_android_textSize, DensityUtil.dp2px(14));
        int textColor = attributes.getColor(R.styleable.CountEditText_android_textColor,
                ContextCompat.getColor(context, android.R.color.background_dark));
        int countNormalColor = attributes.getColor(R.styleable.CountEditText_countNormalColor, 0x80000000);
        int countHighlightColor = attributes.getColor(R.styleable.CountEditText_countHighlightColor, 0xffff4444);
        String textHint = attributes.getString(R.styleable.CountEditText_android_hint);

        attributes.recycle();

        addView(mEditText = getInputText(context, maxChar, textSize, textColor, textHint));
        addView(mTextView = getCountText(context, maxChar, mEditText, countNormalColor, countHighlightColor));
    }

    private ClearEditText getInputText(Context context, int maxChar, float textSize, int textColor, String hint) {
        ClearEditText inputText = new ClearEditText(context);
        inputText.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        // 允许适当过量输入以提示用户
        inputText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxChar + 5),emojiFilter});
        inputText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        inputText.setTextColor(textColor);
        inputText.setGravity(Gravity.START | Gravity.TOP);
        inputText.setLines(5);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            inputText.setBackground(null);
        }
        inputText.setHintTextColor(ContextCompat.getColor(context, R.color.text_color_grey_light));
        inputText.setPadding(0, 0, 0, 0);
        inputText.setHint(hint);
        return inputText;
    }

    public TextView getCountText(Context context, final int maxChar, final EditText editText,
                                 final int countNormalColor, final int countHighlightColor) {
        final TextView countText = new TextView(context);
        countText.setLayoutParams(new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        countText.setGravity(Gravity.END);
        countText.setPadding(0, 0, 10, 0);

        countText.setText(String.format("%s / %s", editText.length(), maxChar));
        updateCountTextColor(countText, countNormalColor, countHighlightColor, editText.length(), maxChar);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateCountTextColor(countText, countNormalColor, countHighlightColor, s.length(), maxChar);
                countText.setText(String.format("%s / %s", s.length(), maxChar));
            }
        });
        return countText;
    }

    private void updateCountTextColor(TextView textView, int normalColor, int highlightColor, int current, int max) {
        textView.setTextColor(current > max ? highlightColor : normalColor);
    }

    /**
     * 溢出字数
     */
    public int countOverflow() {
        return mEditText.length() - maxChar;
    }

    public EditText getEditText() {
        return mEditText;
    }

    public TextView getCountText() {
        return mTextView;
    }

    public void setText(String string) {
        mEditText.setText(string);
    }


    InputFilter emojiFilter = new InputFilter() {

        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher emojiMatcher = emoji.matcher(source);

            if (emojiMatcher.find()) {

                return "";

            }
            return null;

        }
    };
}
