package com.example.ymazualko;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import in.mayanknagwanshi.imagepicker.ImageSelectActivity;

public class MainActivity extends AppCompatActivity {

    TextView name;
    TextView age;
    TextView funeral;
    TextView death;
    ImageView picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.nameValue);
        age = findViewById(R.id.ageValue);
        funeral = findViewById(R.id.funeralValue);
        death = findViewById(R.id.deathValue);
        picture =findViewById(R.id.picture);

        name.setText(getIntent().getStringExtra("name"));
        age.setText(getIntent().getStringExtra("age"));
        funeral.setText(getIntent().getStringExtra("funeral"));
        death.setText(getIntent().getStringExtra("death"));
    }

    public void imageSelectClick(View view) {
        Intent intent = new Intent(this, ImageSelectActivity.class);
        intent.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CAMERA, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_GALLERY, true);//default is true
        intent.putExtra(ImageSelectActivity.FLAG_CROP, false);//default is false
        startActivityForResult(intent, 1213);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1213 && resultCode == Activity.RESULT_OK) {
            String filePath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            Bitmap selectedImage = BitmapFactory.decodeFile(filePath);
            picture.setImageBitmap(selectedImage);
        }
    }


}