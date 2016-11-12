package com.paypal.splittable.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

/**
 * Created by arbalan on 3/28/16.
 */
public class PaymentCodeCardController extends RelativeLayout {
    private static final int INDEX_PAYCODE_CONTAINER = 0;
    private static final int INDEX_QRCODE = 0;

    private ViewGroup mPaycodeContainer;
    private QRCodeImageView mQRCodeImageView;

    public PaymentCodeCardController(Context context) {
        super(context);
    }

    public PaymentCodeCardController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mPaycodeContainer = (ViewGroup) getChildAt(INDEX_PAYCODE_CONTAINER);
        mQRCodeImageView = (QRCodeImageView) mPaycodeContainer.getChildAt(INDEX_QRCODE);
    }

    public final void renderPayCode(String payCodeValue) {
        mQRCodeImageView.setPayCode(payCodeValue);
        mQRCodeImageView.setVisibility(View.VISIBLE);
    }
}
