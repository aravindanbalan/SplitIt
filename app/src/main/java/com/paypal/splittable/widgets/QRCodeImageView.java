package com.paypal.splittable.widgets;

import com.paypal.splittable.managers.QRCodeManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by arbalan on 3/28/16.
 */
public class QRCodeImageView extends ImageView {
    private static final int QR_CODE_MODULES = 41;
    private static final int MIN_QR_CODE_SIZE = (QR_CODE_MODULES * 3);

    private Bitmap mBitmap;
    private String mQRCodeValue;

    public QRCodeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * We are forcing the bitmap size to a multiple of the number of modules
     * (number of squares in a row or column), with the minimum multiple being 3
     * times the number of modules. This is to prevent an unpredictable sized
     * border on the generated bitmap.
     *
     * @param desiredSize
     * @return A size that is a multiple of the number of modules, equal to or
     * greater than the desiredSize.
     */
    private static int calculateQRCodeSize(int desiredSize) {
        int roundedSize;
        if (0 != (desiredSize % QR_CODE_MODULES)) {
            roundedSize = ((desiredSize / QR_CODE_MODULES) + 1) * QR_CODE_MODULES;
        } else {
            roundedSize = desiredSize;
        }
        return Math.max(roundedSize, MIN_QR_CODE_SIZE);
    }

    public final void setPayCode(String value) {
        if (mQRCodeValue != null && mQRCodeValue.equals(value)) {
            return;
        }

        mQRCodeValue = value;
        int size = getMeasuredHeight();
        if (size > 0) {
            size = calculateQRCodeSize(size);
        } else {
            size = calculateQRCodeSize(MIN_QR_CODE_SIZE);
        }

        Bitmap b = QRCodeManager.generateQRCodeBitmap(mQRCodeValue, size, size);
        setImageBitmapInternal(b);
    }

    private final void setImageBitmapInternal(Bitmap bm) {
        if (mBitmap != null) {
            mBitmap.recycle();
        }
        mBitmap = bm;
        setImageBitmap(bm);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mBitmap != null && w <= mBitmap.getWidth() && h <= mBitmap.getHeight()) {
            return;
        }

        if (mQRCodeValue != null) {
            int size = Math.min(w, h);
            size = calculateQRCodeSize(size);
            Bitmap b = QRCodeManager.generateQRCodeBitmap(mQRCodeValue, size, size);
            setImageBitmapInternal(b);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public String getCode() {
        return mQRCodeValue;
    }
}