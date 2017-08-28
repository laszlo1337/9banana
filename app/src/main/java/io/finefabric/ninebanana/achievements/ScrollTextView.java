package io.finefabric.ninebanana.achievements;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by laszlo on 2017-08-28.
 */ /*
Ticker textView
 */
@SuppressLint("AppCompatCustomView")
public class ScrollTextView extends TextView {
    public ScrollTextView(Context context) {
        super(context);
        init();
    }

    public ScrollTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScrollTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setSingleLine();
        setEllipsize(TextUtils.TruncateAt.MARQUEE);
        setMarqueeRepeatLimit(3);
        setHorizontalFadingEdgeEnabled(true);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);


    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != VISIBLE) {
            setSelected(false);
        } else setSelected(true);
    }

    /**
     * Get duration of marquee, roughly.
     *
     * @return assumed duration
     */
    public int getDuration() {
        if (getLayout() == null) measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        final float density = getContext().getResources().getDisplayMetrics().density;
        float dpPerSec = 30 * density;
        float textWidth = getLayout().getLineWidth(0);
        final float gap = textWidth / 3.0f;
        int result = Math.round(
                (textWidth - gap) / dpPerSec);
        return result > 0 ? result * 1000 : 2000;
    }


    @Override
    public void setAlpha(float alpha) {
        super.setAlpha(alpha);
        if (alpha <= 0.1f) {
            stopScrolling();
        }
    }

    public void stopScrolling() {
        ((View) getParent()).requestFocus();
        setSelected(false);
    }

    public void startScrolling() {
        requestFocus();
        setSelected(true);

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopScrolling();
    }
}
