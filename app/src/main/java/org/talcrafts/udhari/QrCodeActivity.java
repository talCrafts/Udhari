package org.talcrafts.udhari;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.talcrafts.udhari.data.AddTxService;
import org.talcrafts.udhari.data.DatabaseContract;

import java.util.EnumMap;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class QrCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private ImageView mImageView;
    private int CAMERA_PERMISSION_CODE = 23;
    ViewGroup contentFrame;
    private static final String EXTRA_DATA = "data";
    private static final String DATE = "date";
    private static final String AMOUNT = "amount";

    public static final String SELECTED_NEIGHBOUR = "SELECTED_NEIGHBOUR";

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
        String data;
        if (getIntent().getExtras() != null) {
            data = getIntent().getExtras().getString(EXTRA_DATA);
        } else {
            data = "Udhari";
        }
        mImageView.setImageBitmap(endcode(data));
    }

    @Override
    public void handleResult(Result result) {
        if (result.getText().split(":").length > 2) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseContract.TableTransactions.COL_DATE, getAmount(result.getText(), DATE)); // bug
            contentValues.put(DatabaseContract.TableTransactions.COL_AMOUNT, getAmount(result.getText(), AMOUNT)); // bug

            AddTxService.insertNewTx(getApplicationContext(), contentValues);
            Intent goBackToMain = new Intent(this, MainActivity.class);
            startActivity(goBackToMain);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Scan Result");
            builder.setMessage("Not a valid value");
            builder.show();
        }
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

    private String getAmount(String data, String column) {
        String[] strings = data.split(":");
        String value = "";
        switch (column) {
            case DATE:
                value = strings[1];
                break;
            case AMOUNT:
                value = strings[3];
                break;
        }
        return value;
    }
}
