package com.example.userinterface.screen;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userinterface.PostAdapt;
import com.example.userinterface.R;
import com.example.userinterface.post.Post;
import com.example.userinterface.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RecycleViewPost extends Fragment {
    private PostAdapt mPostAdapt;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    ArrayList<Post> mListPost;
    RecyclerView recyclerView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        recyclerView = view.findViewById(R.id.recycle_view_profile);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mListPost = new ArrayList<>();
        mPostAdapt = new PostAdapt(mListPost);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User userProfile = dataSnapshot.getValue(User.class);

                if(userProfile != null){
                    String email = userProfile.email;

                    getListPost(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });




        recyclerView.setAdapter(mPostAdapt);

        return view;
    }

    public void getListPost(String keyword){
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("posts");

        Query myTopPostQuery = reference.orderByChild("time");
        myTopPostQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);

                if(post != null ){
                    if(post.getName().contains(keyword)){
                        mListPost.add(0 ,post);
                    }

                    mPostAdapt.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);

                if(post == null || mListPost == null || mListPost.isEmpty()){
                    return;
                }

                for (int i = 0; i < mListPost.size(); i ++) {
                    if (post.getTime().equals(mListPost.get(i).getTime())){
                        mListPost.set(i , post);
                        break;
                    }
                }
                mPostAdapt.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }
}
