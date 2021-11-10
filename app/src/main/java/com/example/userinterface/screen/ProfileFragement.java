package com.example.userinterface.screen;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.userinterface.R;
import com.example.userinterface.ViewPagerAdapter;
import com.example.userinterface.ViewPagerAdapterProfile;
import com.example.userinterface.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragement extends AppCompatActivity implements View.OnClickListener {
    private ImageView backbtn;
    private TextView editBtn;
    private String userID;
    private FirebaseUser user;
    private DatabaseReference reference;
    TextView usernameHome;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fragement);

        backbtn = findViewById(R.id.back_profile);
        editBtn = findViewById(R.id.edit_profile);

        backbtn.setOnClickListener(ProfileFragement.this);

        editBtn.setOnClickListener(this);

        mViewPager = findViewById(R.id.view_pager_profile);

        setUpViewPager();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);
                usernameHome = findViewById(R.id.username_profile);

                if(userProfile != null){
                    String name = userProfile.username;
                    usernameHome.setText(name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){}
        });
    }

    private void setUpViewPager(){
        ViewPagerAdapterProfile viewPagerAdapter = new ViewPagerAdapterProfile(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(viewPagerAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.back_profile:
                finish();
                break;
            case R.id.edit_profile:

                break;
        }
    }
}
