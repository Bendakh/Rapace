package com.example.sbendakhlia.rapace;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class Settings extends Activity {


    EditText passwordChangeField;
    EditText nDaysChangeField;
    Button changePasswordButton;
    Button changeNDaysButton;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        changePasswordButton = findViewById(R.id.change_password_button);
        passwordChangeField = findViewById(R.id.password_change_field);
        nDaysChangeField = findViewById(R.id.change_ndays_field);
        changeNDaysButton = findViewById(R.id.change_ndays_button);


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
        changeNDaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(nDaysChangeField.getText() != null)
                {
                    int newVal = Integer.parseInt(nDaysChangeField.getText().toString());
                    if(newVal > 0 && newVal <= 30)
                    {
                        new FirebaseDataBaseHelper().UpdateNDays(mAuth.getCurrentUser().getUid(), newVal, new FirebaseDataBaseHelper.DataStatus() {
                            @Override
                            public void DataIsInserted() {

                            }

                            @Override
                            public void DataIsUpdated() {
                                ShowMessage("Field updated");
                            }

                            @Override
                            public void DataIsDeleted() {

                            }
                        });
                    }
                }
            }
        });
    }


    private void ShowMessage(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
