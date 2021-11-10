package com.example.userinterface.screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.userinterface.R;
import com.example.userinterface.post.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
public class PostFragment extends Fragment {

    FirebaseDatabase rootNode;
    FirebaseAuth mAuth;
    DatabaseReference reference;
    TextView postUsername;
    private EditText editUsername;
    private Button postBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mAuth = FirebaseAuth.getInstance();
        postUsername = view.findViewById(R.id.post_username_name);
        editUsername = view.findViewById(R.id.write);
        postBtn = view.findViewById(R.id.post_btn);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postU = postUsername.getText().toString();
                String editU = editUsername.getText().toString();

                SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdft.format(new Date());

                Post post = new Post(0, 0, postU, editU, currentDateandTime);

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("posts");

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String time = sdf.format(new Date().getTime());

                reference.child(time).setValue(post, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                        Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                        editUsername.setText("");
                    }
                });
            }
        });

        return view;
    }
}
