package com.happiness.sorttheclutter;



import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class MainActivity extends AppCompatActivity {

    private final Interpreter.Options tfOptions = new Interpreter.Options();
    private Interpreter tf;
    public static final float STANDARD = 128.0f;
    ByteBuffer data = null;
    private static final int CAMERA = 1888;
    private ImageView iv;
    private static final int PERMISSION = 100;
    TextView tvRes, textView1, textView2;
    Button click;
    public static final int MEAN = 128;
    int[] values;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.iv = (ImageView)this.findViewById(R.id.imageView1);
        click = (Button) this.findViewById(R.id.click);
        tvRes = this.findViewById(R.id.tvRes);
        iv.setVisibility(View.INVISIBLE);
        click.setOnClickListener(new View.OnClickListener()
        {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v)
            {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION);
                }
                else
                {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA);
                }
            }
        });
    }
    
    @Override
    public void onRequestPermissionsResult(int code, @NonNull String[] perms, @NonNull int[] res) {
        super.onRequestPermissionsResult(code, perms, res);
        if (code == PERMISSION) {
            if (res[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted!", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA);
            } else {
                Toast.makeText(this, "Camera permission denied!", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void onActivityResult(int reqCode, int resCode ,Intent idata) {
        super.onActivityResult(reqCode, resCode, idata);
        if (reqCode == CAMERA && resCode == Activity.RESULT_OK) {
            Bitmap pic = (Bitmap) idata.getExtras().get("data");
            int height = pic.getHeight();
            int width = pic.getWidth();
            tvRes.setText(width + "   " + height);
            iv.setImageBitmap(pic);
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(pic, 600, 600, false);
            iv.setImageBitmap(Bitmap.createScaledBitmap(resizedBitmap, 800, 800, false));
            int[] pix = new int[224 * 224];
            resizedBitmap.getPixels(pix, 0, 224, 0, 0, 224, 224);
            values = new int[224 * 224];
            try {
                tf = new Interpreter(loadModelFile(), tfOptions);
            } catch (Exception e) {
                e.printStackTrace();
            }
            data = ByteBuffer.allocateDirect(4 * 224 * 224 * 3);
            data.order(ByteOrder.nativeOrder());
            float[][] label = new float[1][2];
            Bitmap input_image = getResizedBitmap(pic, 224, 224);

            convertBitmapToByteBuffer(input_image);

            tf.run(data, label);

            textView1 = this.findViewById(R.id.tVresult);
            textView2 = this.findViewById(R.id.textViewRes);
            textView1.setText((label[0][0] * 100) + " %");
            textView2.setText((label[0][1] * 100) + " %");
            if (label[0][0] > label[0][1])
                tvRes.setText("IT IS ORGANIC!");
            else
                tvRes.setText("IT IS RECYCLABLE!");

            iv.setVisibility(View.VISIBLE);
        }
    }
    private void convertBitmapToByteBuffer(Bitmap bitmap) {
        if (data == null) {
            return;
        }
        data.rewind();
        bitmap.getPixels(values, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;
        for (int i = 0; i < 224 ; ++i) {
            for (int j = 0; j < 224; ++j) {
                final int val = values[pixel++];
                data.putFloat((((val >> 16) & 0xFF)-MEAN)/STANDARD);
                data.putFloat((((val >> 8) & 0xFF)-MEAN)/STANDARD);
                data.putFloat((((val) & 0xFF)-MEAN)/STANDARD);
            }
        }
    }
    public Bitmap getResizedBitmap(Bitmap bm, int w, int h){
        int height = bm.getHeight();
        int width = bm.getWidth();
        float scaleHeight = ((float) h )/height;
        float scaleWidth = ((float) w )/width;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm,0,0,width, height,matrix,false);

        return resizedBitmap;

    }
    private MappedByteBuffer loadModelFile() throws IOException {

        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
    
}