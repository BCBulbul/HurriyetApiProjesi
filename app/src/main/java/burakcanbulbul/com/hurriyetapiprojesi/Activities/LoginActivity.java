package burakcanbulbul.com.hurriyetapiprojesi.Activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import burakcanbulbul.com.hurriyetapiprojesi.R;


public class LoginActivity extends AppCompatActivity
{

    private TwitterLoginButton loginButton;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Twitter.initialize(this);
        setContentView(R.layout.activity_login);

        loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
        enterFacebook();

        loginButton.setCallback(new Callback<TwitterSession>()
        {
            @Override
            public void success(Result<TwitterSession> result)
            {
                /*
                  This provides TwitterSession as a result
                  This will execute when the authentication is successful
                 */
                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;

                //Calling login method and passing twitter session
                login(session);
            }

            @Override
            public void failure(TwitterException exception)
            {
                //Displaying Toast message
                Toast.makeText(LoginActivity.this, "Authentication failed!", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * @param session
     * This method will get username using session and start a new activity where username will be displayed
     */


    public void login(TwitterSession session)
    {
        String username = session.getUserName();
        Toast.makeText(LoginActivity.this, "Twitter'a başarıyla giriş yapıldı, haberler sayfası getiriliyor", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
    }

    /**
     * @param requestCode - we'll set it to REQUEST_CAMERA
     * @param resultCode - this will store the result code
     * @param data - data will store an intent
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void enterFacebook()
    {
        callbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(callbackManager,


                new FacebookCallback<LoginResult>()
                {
                    @Override
                    public void onSuccess(LoginResult loginResult)
                    {

                        Toast.makeText(LoginActivity.this, "Facebook'a başarıyla giriş yapıldı, haberler sayfası getiriliyor", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }

                    // If the user cancels the login, then call onCancel//
                    @Override
                    public void onCancel()
                    {
                    }

                    // If an error occurs, then call onError//
                    @Override
                    public void onError(FacebookException exception)
                    {
                    }
                });
    }





    }
