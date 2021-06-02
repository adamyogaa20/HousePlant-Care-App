package com.bloverteam.plantscare.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bloverteam.plantscare.helper.Parser;
import com.bloverteam.plantscare.R;

import java.io.IOException;

public class InfoActivity extends AppCompatActivity {

    private Parser jsonParser = null;

    // Views
    private ImageView imageView = null;
    private TextView textArea = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        findViews();
        createParser();
        showInfo();
    }

    private void findViews(){
        imageView = findViewById(R.id.imgView);
        textArea = findViewById(R.id.textArea);
    }

    private void createParser(){
        try{
            jsonParser = Parser.getInstance(getAssets().open("flowers_info.json"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private Bitmap getInputImage() {
        byte[] raw = getIntent().getByteArrayExtra("Image");
        return BitmapFactory.decodeByteArray(raw, 0, raw.length);
    }

    private void showInfo(){

        // Get predicted class id
        int id = getIntent().getIntExtra("predictedClass", -1);

        String name   = jsonParser.getItem(id, "name");
        String family = jsonParser.getItem(id, "family");
        String genus  = jsonParser.getItem(id, "genus");
        String caraMerawat = jsonParser.getItem(id, "caraMerawat");

        String flowerInfo = String.format(getString(R.string.info_message),
                name,
                family,
                genus,
                caraMerawat);

        imageView.setImageBitmap(getInputImage());
        textArea.setText(flowerInfo);
    }
}