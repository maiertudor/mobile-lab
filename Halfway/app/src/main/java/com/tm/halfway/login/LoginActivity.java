package com.tm.halfway.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tm.halfway.MainActivity;
import com.tm.halfway.R;

/**
 * Last edit by tudor.maier on 28/11/2017.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
        if (!"EMPTY".equals(sharedPref.getString("Authorization", "EMPTY"))){
            Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(loginIntent);
        }
        setContentView(R.layout.login_fragment);

        final EditText usernameText = (EditText) findViewById(R.id.usernameID);
        final EditText passwordText = (EditText) findViewById(R.id.passwordID);
        final TextView errorMessage = (TextView) findViewById(R.id.errorMessageID);

        Button loginButton = (Button) findViewById(R.id.login_buttonID);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (usernameText != null && passwordText != null) {
                    final String username = String.valueOf(usernameText.getText());
                    String password = String.valueOf(passwordText.getText());

                    new LoginAsync() {
                        @Override
                        protected void onPostExecute(String token) {
                            super.onPostExecute(token);

                            if (token != null) {
                                SharedPreferences sharedPref = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("Authorization", token);
                                editor.putString("Owner", username);
                                if("admin".equals(username)){
                                    editor.putString("Role", "ADMIN");
                                } else {
                                    editor.putString("Role", "QUEST");
                                }
                                editor.apply();

                                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(loginIntent);
                            } else {
                                errorMessage.setAlpha(1);
                                errorMessage.animate()
                                        .translationX(20)
                                        .setDuration(50)
                                        .withEndAction(new Runnable() {
                                            @Override
                                            public void run() {
                                                errorMessage.animate()
                                                        .translationX(-20)
                                                        .setDuration(50)
                                                        .start();
                                            }
                                        })
                                        .start();

                                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                // Vibrate for 500 milliseconds
                                v.vibrate(100);
                            }
                        }
                    }.execute(username, password);
                }
            }
        });

        Button registerButton = (Button) findViewById(R.id.register_buttonID);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getSharedPreferences("TOKEN", Context.MODE_PRIVATE);
                String token = sharedPref.getString("Authorization", "null");
                Log.d("LoginFragment", token);
            }

        });
    }

}
