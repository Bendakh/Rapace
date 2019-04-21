package com.example.sbendakhlia.rapace;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;

public class FirebaseDataBaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceUsers;
    private List<User> users = new ArrayList<>();
    User user;

    public interface DataStatus{

        void DataIsInserted();
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public interface FirebaseSuccessListener {

        void onDataFound(boolean b);

    }

    public FirebaseDataBaseHelper()
    {

        mDatabase = FirebaseDatabase.getInstance();
        mReferenceUsers = mDatabase.getReference("Users");

    }

    public User GetUser(final String uid, final FirebaseSuccessListener fb)
    {
        final User toReturn = new User();


        mReferenceUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String tempId = "";
                String tempName = "";
                String tempEmail = "";
                boolean tempAdmin = false;
                int tempnDays = 0;
                String tempLastChangeDate = "";

                if(dataSnapshot.exists()) {
                /*System.out.println(dataSnapshot.child(uid).getValue(User.class).getId());
                System.out.println(dataSnapshot.child(uid).getValue(User.class).getName());
                System.out.println(dataSnapshot.child(uid).getValue(User.class).getEmail());
                System.out.println(dataSnapshot.child(uid).getValue(User.class).isAdmin());*/
                     tempId = dataSnapshot.child(uid).getValue(User.class).getId();
                     tempName = dataSnapshot.child(uid).getValue(User.class).getName();
                     tempEmail = dataSnapshot.child(uid).getValue(User.class).getEmail();
                     tempAdmin = dataSnapshot.child(uid).getValue(User.class).isAdmin();
                     tempnDays = dataSnapshot.child(uid).getValue(User.class).getnDays();
                     tempLastChangeDate = dataSnapshot.child(uid).getValue(User.class).getLastChangedPasswordDate();

                    toReturn.setId(tempId);
                    toReturn.setName(tempName);
                    toReturn.setEmail(tempEmail);
                    toReturn.setAdmin(tempAdmin);
                    toReturn.setnDays(tempnDays);
                    toReturn.setLastChangedPasswordDate(tempLastChangeDate);

                    fb.onDataFound(true);


                }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        /*System.out.println(toReturn.getId());
        System.out.println(toReturn.getName());
        System.out.println(toReturn.getEmail());
        System.out.println(toReturn.isAdmin());*/

        return toReturn;
    }

    public void AddUser(User user, final DataStatus dataStatus)
    {
        //String key = mReferenceUsers.push().getKey();
        mReferenceUsers.child(user.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dataStatus.DataIsInserted();
            }
        });
    }

    public void UpdatePassword(final String uid, String newPassword, final DataStatus ds)
    {
        mReferenceUsers.child(uid).child("password").setValue(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
                Date todayDate = new Date();
                String newDate = formater.format(todayDate);

                mReferenceUsers.child(uid).child("lastChangedPasswordDate").setValue(newDate).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ds.DataIsUpdated();
                    }
                });
            }
        });

    }

    public void UpdateNDays(final String uid, int newNDays, final DataStatus ds)
    {
        mReferenceUsers.child(uid).child("nDays").setValue(newNDays).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                ds.DataIsUpdated();
            }
        });
    }
}
