package com.example.togethereat.Facebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.togethereat.LoginActivity;
import com.example.togethereat.R;
import com.facebook.login.LoginManager;

public class FacebookProfileActivity extends AppCompatActivity {

    private TextView profileName, profileEmail;
    private ImageView profileImage;
    private Button signOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_profile);

        profileName = findViewById(R.id.profile_text);
        profileEmail = findViewById(R.id.profile_email);
        profileImage = findViewById(R.id.profile_image);
        signOut=findViewById(R.id.sign_out);
        setDataOnView();
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent=new Intent(FacebookProfileActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    private void setDataOnView() {
        String url = getIntent().getStringExtra("userPicture");
        Glide.with(this).load(url).centerInside().fitCenter()
                .error(R.drawable.ic_launcher_background)
                .into(profileImage);
        profileName.setText(getIntent().getStringExtra("userName"));
        profileEmail.setText(getIntent().getStringExtra("userEmail"));
    }

}
