package com.example.togethereat;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.togethereat.Facebook.FacebookProfileActivity;
import com.example.togethereat.Google.GoogleProfileActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "TEST";
    public static GoogleSignInClient googleSignInClient;
    private SignInButton googleSignInButton;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private String userEmail;
    private String userName;
    private String userPicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("public_profile", "email");

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

//        if (accessToken.getCurrentAccessToken() != null) {
//            goToMain();
//        }
//        else {
//        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                accessToken = loginResult.getAccessToken();
                requestMe(accessToken);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


        googleSignInButton = findViewById(R.id.sign_in_google_button);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });
    }

    public void goToMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public void goToProfile() {
        Intent intent = new Intent(getApplicationContext(), FacebookProfileActivity.class);
        intent.putExtra("userEmail", userEmail);
        intent.putExtra("userName", userName);
        intent.putExtra("userPicture", userPicture);
        startActivity(intent);
    }

    private void onLoggedInGoogle(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, GoogleProfileActivity.class);
        intent.putExtra(GoogleProfileActivity.GOOGLE_ACCOUNT, googleSignInAccount);

        startActivity(intent);
        finish();
    }

    public void requestMe(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    userEmail = object.getString("email");
                    userName = object.getString("name");
                    JSONObject jobj1 = object.optJSONObject("picture");
                    JSONObject jobj2 = jobj1.optJSONObject("data");
                    userPicture = jobj2.getString("url");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                goToProfile();

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture");
        request.setParameters(parameters);
        request.executeAsync();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        String email = account.getEmail();
                        String m = account.getFamilyName();
                        String m2 = account.getGivenName();
                        String m3 = account.getDisplayName();
                        Log.d("Name : ", m);
                        Log.d("Name2 : ", m2);
                        Log.d("Name3 : ", m3);
                        Log.d("Email : ", email);
                        onLoggedInGoogle(account);
                    } catch (ApiException e) {
                        Log.w(TAG, "signInResult: failed code = " + e.getStatusCode());
                    }

                default:
                    LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
                    callbackManager.onActivityResult(requestCode, resultCode, data);
                    break;
            }
    }

    @Override
    public void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyloggedAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (alreadyloggedAccount != null) {
            Toast.makeText(this, "Already Logged In", Toast.LENGTH_SHORT).show();
            onLoggedInGoogle(alreadyloggedAccount);
        } else {
            Log.d(TAG, "Not logged in");
        }
    }

}
