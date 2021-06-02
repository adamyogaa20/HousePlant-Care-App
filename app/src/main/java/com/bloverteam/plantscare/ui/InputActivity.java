package com.bloverteam.plantscare.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.bloverteam.plantscare.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SuppressWarnings("deprecation")
public class InputActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_PICK_IMAGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }

    @SuppressLint("QueryPermissionsNeeded")
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Get input image and start InterpreterActivity to make prediction
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Bitmap inputImage =  Bitmap.createScaledBitmap(imageBitmap, 500, 500, true);
            inputImage = adjustImage(inputImage);
            startInterpreterActivity(inputImage);
        }

        else if(requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK){
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                BufferedInputStream rawImage = new BufferedInputStream(inputStream);
                Bitmap imageBitmap = BitmapFactory.decodeStream(rawImage);
                Bitmap inputImage = Bitmap.createScaledBitmap(imageBitmap, 500, 500, true);
                startInterpreterActivity(inputImage);
            }catch(FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    public void takeImage(View clickedButton){
        // Delegate to camera app
        dispatchTakePictureIntent();
    }

    public void chooseImage(View clickedButton){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE);
    }

    private static Bitmap adjustImage(Bitmap image){
        float degrees = 90;
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees);

        return Bitmap.createBitmap(image, 0, 0,
                image.getWidth(),
                image.getHeight(),
                matrix,
                true);
    }


    private void startInterpreterActivity(Bitmap inputImage){

        // Pass input image to activity
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        inputImage.compress(Bitmap.CompressFormat.PNG, 100, bStream);
        byte[] byteArray = bStream.toByteArray();

        Intent interpreterIntent = new Intent(this, InterpreterActivity.class);
        interpreterIntent.putExtra("Image", byteArray);

        // Start activity to show results
        startActivity(interpreterIntent);
    }
}