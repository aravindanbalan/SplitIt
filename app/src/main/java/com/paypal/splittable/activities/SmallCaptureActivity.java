package com.paypal.splittable.activities;

import com.paypal.splittable.R;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

/**
 * Created by arbalan on 3/25/16.
 */
public class SmallCaptureActivity extends CaptureActivity {
    @Override
    protected CompoundBarcodeView initializeContent() {
        setContentView(R.layout.capture_small);
        return (CompoundBarcodeView)findViewById(R.id.zxing_barcode_scanner);
    }
}