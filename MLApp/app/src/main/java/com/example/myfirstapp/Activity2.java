package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;


//public class Activity2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
public class Activity2 extends AppCompatActivity{
    File f1,f2,f3,f4,f;
    Button uploadBtn;
    String image_file_name1,image_file_name2,image_file_name3,image_file_name4,image_file_name;
    String responseBody1,responseBody2,responseBody3,responseBody4;
    Bitmap bmp1,bmp2,bmp3,bmp4,bmp;
    ByteArrayOutputStream bos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("captured_image");

        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView image = findViewById(R.id.image_view);

        int xCount = 2, yCount = 2;
        // Allocate a two dimensional array to hold the individual images.
        Bitmap[][] bitmaps = new Bitmap[xCount][yCount];
        int width, height;
        // Divide the original bitmap width by the desired vertical column count
        width = bmp.getWidth() / xCount;
        // Divide the original bitmap height by the desired horizontal row count
        height = bmp.getHeight() / yCount;
        // Loop the array and create bitmaps for each coordinate
        for(int x = 0; x < xCount; ++x) {
            for(int y = 0; y < yCount; ++y) {
                // Create the sliced bitmap
                bitmaps[x][y] = Bitmap.createBitmap(bmp, x * width, y * height, width, height);
            }
        }
        bmp1 = bitmaps[0][0];
        bmp2 = bitmaps[0][1];
        bmp3 = bitmaps[1][0];
        bmp4 = bitmaps[1][1];
        image.setImageBitmap(bmp);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        image_file_name = "temporary_" +sdf.format(new Date())+".png";
        image_file_name1 = "temporary1_" +sdf.format(new Date())+".png";
        image_file_name2 = "temporary2_" +sdf.format(new Date())+".png";
        image_file_name3 = "temporary3_" +sdf.format(new Date())+".png";
        image_file_name4 = "temporary4_" +sdf.format(new Date())+".png";


        //create a file to write bitmap data
        f1 = new File(getCacheDir(), image_file_name1);
        f2 = new File(getCacheDir(), image_file_name2);
        f3 = new File(getCacheDir(), image_file_name3);
        f4 = new File(getCacheDir(), image_file_name4);

        try {
            boolean x1 = f1.createNewFile();
            boolean x2 = f2.createNewFile();
            boolean x3 = f3.createNewFile();
            boolean x4 = f4.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Convert bitmap to byte array

        bos = new ByteArrayOutputStream();
        ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        ByteArrayOutputStream bos3 = new ByteArrayOutputStream();
        ByteArrayOutputStream bos4 = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100 , bos);
        bmp1.compress(Bitmap.CompressFormat.PNG, 100 , bos1);
        bmp2.compress(Bitmap.CompressFormat.PNG, 100 , bos2);
        bmp3.compress(Bitmap.CompressFormat.PNG, 100 , bos3);
        bmp4.compress(Bitmap.CompressFormat.PNG, 100 , bos4);

        //write the bytes in file
        FileOutputStream fos1 = null,fos2 = null,fos3 = null,fos4 = null;
        try {
            fos1 = new FileOutputStream(f1);
            fos2 = new FileOutputStream(f2);
            fos3 = new FileOutputStream(f3);
            fos4 = new FileOutputStream(f4);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos1.write(bos1.toByteArray());
            fos1.flush();
            fos1.close();
            fos2.write(bos2.toByteArray());
            fos2.flush();
            fos2.close();
            fos3.write(bos3.toByteArray());
            fos3.flush();
            fos3.close();
            fos4.write(bos4.toByteArray());
            fos4.flush();
            fos4.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        uploadBtn = findViewById(R.id.upload_btn);

        // button click
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String baseURL1 = "http://192.168.0.111:5000/upload1";
                String baseURL2 = "http://192.168.0.111:5000/upload2";
                String baseURL3 = "http://192.168.0.111:5000/upload3";
                String baseURL4 = "http://192.168.0.111:5000/upload4";
                AsyncHttpClient asyncHttpClient1 = new AsyncHttpClient();
                File fls1 = new File(getCacheDir(), image_file_name1);
                RequestParams params1 = new RequestParams();
                try {
                    params1.put("file", fls1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                asyncHttpClient1.post(Activity2.this, baseURL1, params1, new AsyncHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(Activity2.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        responseBody1 = new String(responseBody, StandardCharsets.UTF_8);
//                        Log.d("responseBody1", responseBody1);
                        Toast.makeText(Activity2.this, "Upload Successful!", Toast.LENGTH_SHORT).show();
                        AsyncHttpClient asyncHttpClient2 = new AsyncHttpClient();
                        File fls2 = new File(getCacheDir(), image_file_name2);
                        RequestParams params2 = new RequestParams();
                        try {
                            params2.put("file", fls2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        asyncHttpClient2.post(Activity2.this, baseURL2, params2, new AsyncHttpResponseHandler() {
                            @Override
                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                Toast.makeText(Activity2.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                responseBody2 = new String(responseBody, StandardCharsets.UTF_8);
//                                Log.d("responseBody2", responseBody2);
                                Toast.makeText(Activity2.this, "Upload Successful!", Toast.LENGTH_SHORT).show();
                                AsyncHttpClient asyncHttpClient3 = new AsyncHttpClient();
                                File fls3 = new File(getCacheDir(), image_file_name3);
                                RequestParams params3 = new RequestParams();
                                try {
                                    params3.put("file", fls3);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                asyncHttpClient3.post(Activity2.this, baseURL3, params3, new AsyncHttpResponseHandler() {
                                    @Override
                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                        Toast.makeText(Activity2.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                        responseBody3 = new String(responseBody, StandardCharsets.UTF_8);
//                                        Log.d("responseBody3", responseBody3);
                                        Toast.makeText(Activity2.this, "Upload Successful!", Toast.LENGTH_SHORT).show();
                                        AsyncHttpClient asyncHttpClient4 = new AsyncHttpClient();
                                        File fls4 = new File(getCacheDir(), image_file_name4);
                                        RequestParams params4 = new RequestParams();
                                        try {
                                            params4.put("file", fls4);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        asyncHttpClient4.post(Activity2.this, baseURL4, params4, new AsyncHttpResponseHandler() {
                                            @Override
                                            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                                                Toast.makeText(Activity2.this, "Upload failed!", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                                                responseBody4 = new String(responseBody, StandardCharsets.UTF_8);
//                                                Log.d("responseBody4", responseBody4);
                                                Toast.makeText(Activity2.this, "Upload Successful!", Toast.LENGTH_SHORT).show();
                                                String[] numberStrs1 = responseBody1.split(",");
                                                String[] numberStrs2 = responseBody2.split(",");
                                                String[] numberStrs3 = responseBody3.split(",");
                                                String[] numberStrs4 = responseBody4.split(",");
                                                float maxValue = 0,tmpSum;
                                                int idx = -1;
                                                for(int i = 0;i < numberStrs1.length;i++)
                                                {
                                                    tmpSum = Float.parseFloat(numberStrs1[i])+Float.parseFloat(numberStrs2[i])+Float.parseFloat(numberStrs3[i])+Float.parseFloat(numberStrs4[i]);
                                                    Log.d("output",Float.toString(tmpSum));
                                                    if(tmpSum>maxValue){
                                                        maxValue = tmpSum;
                                                        idx = i;
                                                    }
                                                }
                                                String op_pth = getCacheDir() + "/predictions/"+Integer.toString(idx)+"/";
                                                Log.d("Predicted Value",Integer.toString(idx));

                                                File directory = new File(op_pth);
                                                if (! directory.exists()){
                                                    directory.mkdirs();
                                                }

                                                f = new File(op_pth, image_file_name);

                                                try {
                                                    boolean x = f.createNewFile();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }

                                                //write the bytes in file
                                                FileOutputStream fos = null;
                                                try {
                                                    fos = new FileOutputStream(f);
                                                } catch (FileNotFoundException e) {
                                                    e.printStackTrace();
                                                }
                                                try {
                                                    fos.write(bos.toByteArray());
                                                    fos.flush();
                                                    fos.close();
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });



            }
        });
    }
}