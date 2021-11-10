package com.example.userinterface.screen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userinterface.R;
import com.example.userinterface.UserAdapt;
import com.example.userinterface.post.Post;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private UserAdapt mUserAdapter;

    RecyclerView recyclerView;
    ArrayList<Post> mListUser;
    TextView text;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        EditText editSearch = view.findViewById(R.id.search_bar_text);
        ImageView searchBtn = view.findViewById(R.id.search_bar_btn);
        text = view.findViewById(R.id.search_text);

//        searchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(text.getText().toString().trim() != ""){
//                    count += 1;
//                    getListUser(editSearch.getText().toString().trim());
//                    text.setText(editSearch.getText().toString().trim());
//                }
//            }
//        });

        getListUser("");


        mListUser = new ArrayList<>();

        mUserAdapter = new UserAdapt(mListUser, new UserAdapt.IClickListener() {
            @Override
            public void onClickUpdateItem(Post post) {
                openDialogUpdateItem(post);
            }

            @Override
            public void onClickDeletePost(Post post) {
                onClickDeleteData(post);
            }

            @Override
            public void onClickUnlike(Post post) {
                onClickIncreaseUnLike(post);
            }

            @Override
            public void onClickLike(Post post) {
                onClickIncreaseLike(post);
            }
        });

        recyclerView.setAdapter(mUserAdapter);

        return view;
    }

    public void getListUser (String keyword) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("posts");

        Query myTopPostQuery = reference.orderByChild("time");
        myTopPostQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);

                if(post != null ){
                    if(post.getName().contains(keyword)){
                        mListUser.add(0 ,post);
                    }

                    mUserAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Post post = dataSnapshot.getValue(Post.class);

                if(post == null || mListUser == null || mListUser.isEmpty()){
                    return;
                }

                for (int i = 0; i < mListUser.size(); i ++) {
                    if (post.getTime().equals(mListUser.get(i).getTime())){
                        mListUser.set(i , post);
                        break;
                    }
                }
                mUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);

                if(post == null || mListUser == null || mListUser.isEmpty()){
                    return;
                }

                for (int i = 0; i < mListUser.size(); i ++) {
                    if (post.getTime().equals(mListUser.get(i).getTime())){
                        mListUser.remove(mListUser.get(i));
                    }
                }

                mUserAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    public void onClickIncreaseLike (Post postT) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("posts");

        TextView likeCount = (TextView) recyclerView.findViewById(R.id.like_count);

        likeCount.setText(String.valueOf(postT.getLike()));

        int like = Integer.parseInt(likeCount.getText().toString().trim());
        postT.setLike(like+1);

        reference.child(String.valueOf(postT.getTime())).updateChildren(postT.toMapLike(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

            }
        });
    }

    public void onClickIncreaseUnLike (Post postT) {
        FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("posts");

        TextView unlikeCount = (TextView) recyclerView.findViewById(R.id.unlike_count);

        unlikeCount.setText(String.valueOf(postT.getUnlike()));

        int unlike = Integer.parseInt(unlikeCount.getText().toString().trim());
        postT.setUnlike(unlike+1);

        reference.child(String.valueOf(postT.getTime())).updateChildren(postT.toMapUnLike(), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {}
        });
    }

    public void onClickDeleteData(Post post){
        new AlertDialog.Builder(getActivity())
                .setTitle("Delete the post")
                .setMessage("Are you sure want to delete this post")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("posts");

                        myRef.child(String.valueOf(post.getTime())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                Toast.makeText(getActivity(), "Delete data success", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }

    public void openDialogUpdateItem(Post post){
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_update);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        EditText editText = dialog.findViewById(R.id.edt_update);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnUpdate = dialog.findViewById(R.id.btn_update);

        editText.setText(post.getDesc());

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                DatabaseReference reference = rootNode.getReference("posts");

                String newdesc = editText.getText().toString().trim();
                post.setDesc(newdesc);

                reference.child(String.valueOf(post.getTime())).updateChildren(post.toMap(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        Toast.makeText(getActivity(), "Update description success", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

            }
        });

        dialog.show();
    }


}
