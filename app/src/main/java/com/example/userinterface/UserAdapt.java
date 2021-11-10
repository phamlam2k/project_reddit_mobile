package com.example.userinterface;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userinterface.post.Post;

import java.util.ArrayList;

public class UserAdapt extends RecyclerView.Adapter<UserAdapt.myviewholder> {
    ArrayList<Post> post;
    private IClickListener mIClickListener;

    public interface IClickListener {
        void onClickUpdateItem(Post post);
        void onClickDeletePost(Post post);
        void onClickUnlike(Post post);
        void onClickLike(Post post);
    }

    public UserAdapt(ArrayList<Post> post, IClickListener listener) {
        this.post = post;
        this.mIClickListener = listener;

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);

        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Post p = post.get(position);

        if(p == null) {
            return;
        }

        holder.name.setText(post.get(position).getName());
        holder.time.setText(post.get(position).getTime());
        holder.description.setText(post.get(position).getDesc());
        holder.like.setText(String.valueOf(post.get(position).getLike()));
        holder.unlike.setText(String.valueOf(post.get(position).getUnlike()));
        holder.like_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickLike(p);
            }
        });
        holder.unlike_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickUnlike(p);
            }
        });
        holder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickListener.onClickUpdateItem(p);
            }
        });
        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Click : ", p.getDesc());
                mIClickListener.onClickDeletePost(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        ImageView  img_delete, like_btn, unlike_btn;
        TextView name, time , description, like, unlike;


        public myviewholder(@NonNull View itemView) {
            super(itemView);

            like_btn=itemView.findViewById(R.id.like);
            unlike_btn=itemView.findViewById(R.id.unlike);
            img_delete=itemView.findViewById(R.id.delete_post);
            name=itemView.findViewById(R.id.post_username);
            time=itemView.findViewById(R.id.post_time);
            description=itemView.findViewById(R.id.post_description);
            like=itemView.findViewById(R.id.like_count);
            unlike=itemView.findViewById(R.id.unlike_count);


        }
    }
}

