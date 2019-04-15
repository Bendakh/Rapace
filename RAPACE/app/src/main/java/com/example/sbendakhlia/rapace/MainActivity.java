package com.example.sbendakhlia.rapace;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {


    ProgressDialog mDialog;
    VideoView vv;
    ImageButton btnPlayPause;
    TextView test;
    EditText passwordChangeField;
    Button changePasswordButton;
    FirebaseAuth mAuth;

    //We will use this variable to get the video from the server
    String videoUrl = "http://mic.duytan.edu.vn:86/FINAL.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        test = findViewById(R.id.test);

        changePasswordButton = findViewById(R.id.change_password_button);
        passwordChangeField = findViewById(R.id.password_change_field);
        Bundle data = getIntent().getExtras();
        if(data == null)
        {
            return;
        }
        String name = data.getString("_name");
        String email = data.getString("_email");
        String id = data.getString("_id");
        boolean admin = data.getBoolean("_admin");
        test.setText(name + " " + email + " " + id + " " + admin);

        changePasswordButton.setOnClickListener(new View.OnClickListener() {
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
        });
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

    private void ShowMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
