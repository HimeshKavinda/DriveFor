package com.example.drivefor2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bottomNavigation extends AppCompatActivity {

    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);


        getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer,new RepairFragment()).commit();

        bnv = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                Fragment temp = null;
                //MainActivity2 main = null;

                switch (item.getItemId())
                {
                    case R.id.menu_handyman : temp = new RepairFragment();
                        break;
                    case R.id.menu_directions : temp = new ServiceFragment();
                        break;
                    case R.id.menu_access : temp = new ReminderFragment();
                        break;
                    case R.id.menu_route : temp = new RouteFragment();

                }


                getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer,temp).commit();

                return true;
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
                Intent i3 = new Intent(this,adddata.class);
                startActivity(i3);
                Toast.makeText(this, "Add reminder panel is Opening.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item4:
                Intent i4 = new Intent(this,MainActivity.class);
                startActivity(i4);
                Toast.makeText(this, "Reminder Store is Opening.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item5:
                Toast.makeText(this, "Lets' go next Options.", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.subitem1:
                Intent i6 = new Intent(this,Feedback.class);
                startActivity(i6);
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