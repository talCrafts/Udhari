package org.talcrafts.udhari;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.EnumMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private ImageView mImageView;
    private int CAMERA_PERMISSION_CODE = 23;
    ViewGroup contentFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);
        contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        mScannerView.setResultHandler(this);
        if (isCameraAccessAllowed()) {
            contentFrame.addView(mScannerView);
        } else {
            requestStoragePermission();
        }
        mImageView = (ImageView) findViewById(R.id.qr_code_id);
        mImageView.setImageBitmap(endcode("Udhari"));
    }

    @Override
    public void handleResult(Result result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();

    }

    private Bitmap endcode(String input) {
        BarcodeFormat format = BarcodeFormat.QR_CODE;
        EnumMap<EncodeHintType, Object> hint = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
        hint.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix result = null;
        try {
            result = new MultiFormatWriter().encode(input, BarcodeFormat.QR_CODE, 500, 500, hint);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bit = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bit.setPixels(pixels, 0, w, 0, 0, w, h);
        ImageView imageView = (ImageView) findViewById(R.id.qr_code_id);
        imageView.setImageBitmap(bit);
        return bit;
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }


    private boolean isCameraAccessAllowed() {
        boolean flag = false;
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            flag = true;
        }
        return flag;
    }

    private void requestStoragePermission() {

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CAMERA_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                contentFrame.addView(mScannerView);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.permission_camera_rationale);
                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        }
    }

}
