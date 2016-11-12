package com.paypal.splittable.managers;

/**
 * Created by arbalan on 3/28/16.
 */

import android.graphics.Bitmap;
import android.graphics.Color;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that serves as a utility manager for QR code Image view.
 * <p/>
 * Created by arbalan on 10/30/15.
 */
public class QRCodeManager {
    private static final int DEFAULT_BACKGROUND_COLOR = Color.WHITE;
    private static final int DEFAULT_CODE_COLOR = Color.BLACK;

    // Singleton
    private static QRCodeManager sInstance = null;

    private QRCodeManager() {
    }

    public static synchronized QRCodeManager getInstance() {
        if (sInstance == null) {
            sInstance = new QRCodeManager();
        }
        return sInstance;
    }

    public static Bitmap generateQRCodeBitmap(String value, int height, int width) {
        return generateQRCodeBitmap(value, height, width, DEFAULT_CODE_COLOR, DEFAULT_BACKGROUND_COLOR);
    }

    public static Bitmap generateQRCodeBitmap(String value, int height, int width, int codeColor, int backgroundColor) {
        BitMatrix bm = generateBitMatrix(value, BarcodeFormat.QR_CODE, height, width);
        return convertQRBitMatrixToBitmap(bm, codeColor, backgroundColor);
    }

    private static Bitmap convertQRBitMatrixToBitmap(BitMatrix bitMatrix, int pixelColor, int pixelBackgroundColor) {
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();

        int[] colors = new int[width * height];
        if (0 != (pixelColor & 0x00FFFFFF)) {
            // If the set-bit pixel color is black, we can skip this as the array will be
            // initialized to zeros anyway, and the later conversion to RGB565 will interpret
            // those pixels as solid black as alpha will be lost in the translation.
            java.util.Arrays.fill(colors, pixelColor);
        }
        int index = -1;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                index++;
                if (!bitMatrix.get(x, y)) {
                    colors[index] = pixelBackgroundColor;
                }
            }
        }
        return Bitmap.createBitmap(colors, 0, width, width, height, Bitmap.Config.RGB_565);
    }

    private static BitMatrix generateBitMatrix(String data, BarcodeFormat format, int height, int width) {
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            // Remove border
            Map<EncodeHintType, Integer> hints = new HashMap<>();
            hints.put(EncodeHintType.MARGIN, 0);

            return writer.encode(data, format, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}