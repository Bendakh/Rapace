package com.example.sbendakhlia.rapace;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;

public class MainActivity extends AppCompatActivity {


    ProgressDialog mDialog;
    VideoView vv;
    ImageButton btnPlayPause;
    TextView test;
    //EditText passwordChangeField;
    //Button changePasswordButton;
    FirebaseAuth mAuth;
    SharedPreferences sp = getApplicationContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

    Button settingsButton;

    //We will use this variable to get the video from the server
    String videoUrl = "http://mic.duytan.edu.vn:86/FINAL.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        test = findViewById(R.id.test);
        settingsButton = findViewById(R.id.settings_button);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent settingsIntent = new Intent(MainActivity.this, Settings.class);
                startActivity(settingsIntent);
            }
        });

        //changePasswordButton = findViewById(R.id.change_password_button);
        //passwordChangeField = findViewById(R.id.password_change_field);
        Bundle data = getIntent().getExtras();
        if(data == null)
        {
            return;
        }
        String name = data.getString("_name");
        String email = data.getString("_email");
        String id = data.getString("_id");
        boolean admin = data.getBoolean("_admin");
        int nDays = data.getInt("_nDays");
        String lastChange = data.getString("_lastChange");

        sp.edit().putInt("ndays", nDays).putString("lastChange",lastChange).apply();

        try
        {
            long value = User.GetNumberOfDaysSinceLastPasswordChange(lastChange);
            sp.edit().putLong("daysSinceChange",value).apply();
            if(value >= (nDays - 5))
            {
                //Propose password change
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Password change")
                        .setMessage("Do you want to change your password? You'll be forced to change it in five days!")
                        .setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent newIntent = new Intent(MainActivity.this, PasswordChange.class);
                                startActivity(newIntent);
                            }
                        });
                builder.create();
            }
            else if(value >= nDays)
            {
                Intent newIntent = new Intent(MainActivity.this, PasswordChange.class);
                startActivity(newIntent);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        test.setText(name + " " + email + " " + id + " " + admin);

        /*changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordChangeField.getText() != null)
                {
                    new FirebaseDataBaseHelper().UpdatePassword(mAuth.getCurrentUser().getUid(), passwordChangeField.getText().toString(), new FirebaseDataBaseHelper.DataStatus() {
                        @Override
                        public void DataIsInserted() {

                        }

                        @Override
                        public void DataIsUpdated() {
                            mAuth.getCurrentUser().updatePassword(passwordChangeField.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    ShowMessage("Password updated!");
                                }
                            });
                        }

                        @Override
                        public void DataIsDeleted() {

                        }
                    });
                }
            }
        });*/
        /*vv = findViewById(R.id.video_view);
        btnPlayPause = findViewById(R.id.btn_play_pause);

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new ProgressDialog(MainActivity.this);
                mDialog.setMessage("Waiting");
                mDialog.setCanceledOnTouchOutside(false);
                mDialog.show();

                try {
                    if (!vv.isPlaying())
                    {
                        Uri uri = Uri.parse(videoUrl);
                        vv.setVideoURI(uri);
                        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            btnPlayPause.setImageResource(R.drawable.ic_play);
                        }
                    });
                    }
                    else
                    {
                        vv.pause();
                        btnPlayPause.setImageResource(R.drawable.ic_play);
                    }
                }
                catch(Exception ex)
                {
                    //Whatever
                }
                vv.requestFocus();
                vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mDialog.dismiss();
                        mp.setLooping(true);
                        vv.start();
                        btnPlayPause.setImageResource(R.drawable.ic_pause);
                    }
                });
            }
        });*/
    }

    @Override
    protected void onResume() {
        super.onResume();

        long value = sp.getLong("daysSinceChange",0);
        int nDays = sp.getInt("ndays",0);

        if(value >= nDays)
        {
            Intent newIntent = new Intent(MainActivity.this, PasswordChange.class);
            startActivity(newIntent);
        }
    }

    private void ShowMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
