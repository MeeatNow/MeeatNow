package com.example.togethereat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.gallery_imageview);
    }

    public void openGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, GET_GALLERY_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (isExistGalleryData(requestCode, resultCode, data)) {

            setImageOnImageView(data);

            // 머신러닝 후 intent로 Glide로 이미지 띄우는 페이지로 이동
            runMachineRunning();
            goToGlideActivity();
        }
    }


    private boolean isExistGalleryData(int requestCode, int resultCode, Intent data) {
        return requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null;
    }

    private void setImageOnImageView(Intent data) {
        Uri selectedImageUri = data.getData();
        imageView.setImageURI(selectedImageUri);
    }

    private void runMachineRunning() {

    }

    private void goToGlideActivity() {
        Intent intent = new Intent(getApplicationContext(), GlideActivity.class);
        startActivity(intent);
    }
}
