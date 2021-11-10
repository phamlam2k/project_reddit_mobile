package com.example.userinterface;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;


import com.example.userinterface.screen.ProfileFragement;
import com.example.userinterface.user.LogIn;
import com.example.userinterface.user.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private NavigationView navigationSider;
    private BottomNavigationView navigationView;
    private ViewPager mViewPager;
    private DrawerLayout mDrawerLayout;
    TextView usernameHome,postUsername;
    Toolbar myToolbar;

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            mDrawerLayout.closeDrawer((GravityCompat.START));
        }
        else{
            super.onBackPressed();
        }
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_pager);
        myToolbar = findViewById(R.id.myToolBar);

        mDrawerLayout = findViewById(R.id.maincontainer);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);
                usernameHome = (TextView)findViewById(R.id.username_home);
                postUsername = (TextView)findViewById(R.id.post_username_name);

                if(userProfile != null){
                    String name = userProfile.username;
                    String email = userProfile.email;

                    usernameHome.setText(name);
                    postUsername.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

        setUpViewPager();
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, myToolbar,R.string.nav_drawer_open, R.string.nav_drawer_close);

        mDrawerLayout.addDrawerListener(toggle);

        toggle.syncState();

        navigationSider = findViewById(R.id.navigationView);
        navigationSider.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.nav_my_profile){
                    Intent intent = new Intent(MainActivity.this, ProfileFragement.class);
                    startActivity(intent);
                }else if(id == R.id.nav_login){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(MainActivity.this, LogIn.class);
                    startActivity(intent);
                }

                mDrawerLayout.closeDrawer((GravityCompat.START));
                return false;
            }
        });

        navigationView = findViewById(R.id.bottom_nav);

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.action_post:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.action_chat:
                        mViewPager.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });
    }


    private void setUpViewPager(){
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);

    }

}