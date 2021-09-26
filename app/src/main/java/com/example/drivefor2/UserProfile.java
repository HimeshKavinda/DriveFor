package com.example.drivefor2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    EditText et_id, et_name, et_address, et_contactnum;
    Button btn_save, btn_show, btn_update, btn_delete;
    User pObj;
    DatabaseReference dbRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_address = findViewById(R.id.et_address);
        et_contactnum = findViewById(R.id.et_contactnum);

        pObj = new User();
    }

    public void clearControls() {
        et_id.setText("");
        et_name.setText("");
        et_address.setText("");
        et_contactnum.setText("");
    }

    //insert records to Firebase database
    public void CreateData(View view) {
        dbRef = FirebaseDatabase.getInstance("https://drivefor2-default-rtdb.firebaseio.com/").getReference().child("user");

        try {
            if (TextUtils.isEmpty(et_id.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please enter your ID.", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(et_name.getText().toString())){
                Toast.makeText(getApplicationContext(), "Please enter your name.", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(et_address.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please enter your address.", Toast.LENGTH_SHORT).show();
            }else if (TextUtils.isEmpty(et_contactnum.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Please enter a your contact number.", Toast.LENGTH_SHORT).show();
            }else {
                pObj.setID(et_id.getText().toString().trim());
                pObj.setName(et_name.getText().toString().trim());
                pObj.setAddress(et_address.getText().toString().trim());
                pObj.setContactNumber(Integer.parseInt(et_contactnum.getText().toString().trim()));

                //dbRef.push().setValue(stuObj);
                dbRef.child(pObj.getID()).setValue(pObj);


                Toast.makeText(getApplicationContext(), "Data Inserted Successfully!", Toast.LENGTH_SHORT).show();
                clearControls();
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Invalid Number Format", Toast.LENGTH_SHORT).show();
        }
    }

    //retrieve data from firebase database
    public void ViewData (View view) {
        dbRef = FirebaseDatabase.getInstance("https://drivefor2-default-rtdb.firebaseio.com/").getReference().child("user").child(pObj.getID());
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    et_id.setText(dataSnapshot.child("id").getValue().toString());
                    et_name.setText(dataSnapshot.child("name").getValue().toString());
                    et_address.setText(dataSnapshot.child("address").getValue().toString());
                    et_contactnum.setText(dataSnapshot.child("contactNumber").getValue().toString());
                }
                else
                    Toast.makeText(getApplicationContext(), "No Source To Display!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //update data in the Firebase database
    public void UpdateData (View view) {
        dbRef = FirebaseDatabase.getInstance("https://drivefor2-default-rtdb.firebaseio.com/").getReference().child("user");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(pObj.getID())) {
                    try {
                        pObj.setID(et_id.getText().toString().trim());
                        pObj.setName(et_name.getText().toString().trim());
                        pObj.setAddress(et_address.getText().toString().trim());
                        pObj.setContactNumber(Integer.parseInt(et_contactnum.getText().toString().trim()));

                        dbRef = FirebaseDatabase.getInstance("https://drivefor2-default-rtdb.firebaseio.com/").getReference().child("user").child(pObj.getID());
                        dbRef.setValue(pObj);
                        clearControls();

                        Toast.makeText(getApplicationContext(), "Data updated Successfully!", Toast.LENGTH_SHORT).show();
                    }
                    catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Invalid Country!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "No Source To Display!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //delete data from Firebase Database
    public void DeleteData (View view) {
        dbRef = FirebaseDatabase.getInstance("https://drivefor2-default-rtdb.firebaseio.com/").getReference().child("user");
        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(pObj.getID())) {
                    dbRef = FirebaseDatabase.getInstance("https://drivefor2-default-rtdb.firebaseio.com/").getReference().child("user").child(pObj.getID());
                    dbRef.removeValue();
                    clearControls();

                    Toast.makeText(getApplicationContext(), "Data Deleted Successfully!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "No Source To Display!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Intent i = new Intent(this,MyVehicle.class);
                startActivity(i);
                Toast.makeText(this, "My Vehicle panel is Opening.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Intent i2 = new Intent(this,UserProfile.class);
                startActivity(i2);
                Toast.makeText(this, "User profile panel is Opening.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                Toast.makeText(this, "Lets' go next Options.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Toast.makeText(this, "Feedback panel is Opening.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem2:
                Toast.makeText(this, "Settings panel is Opening.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem3:
                Toast.makeText(this, "Report panel is Opening.", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}