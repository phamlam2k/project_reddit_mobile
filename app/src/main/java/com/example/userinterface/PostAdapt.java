package com.example.userinterface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.userinterface.post.Post;

import java.util.ArrayList;

public class PostAdapt extends RecyclerView.Adapter<PostAdapt.myviewholder>{
    ArrayList<Post> post;

    @NonNull
    @Override
    public PostAdapt.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_list_profile_item, parent, false);

        return new PostAdapt.myviewholder(view);
    }

    public PostAdapt(ArrayList<Post> post) {
        this.post = post;

    }


    @Override
    public void onBindViewHolder(@NonNull PostAdapt.myviewholder holder, int position) {
        Post p = post.get(position);

        if(p == null){
            return;
        }

        holder.name.setText(post.get(position).getName());
        holder.time.setText(post.get(position).getTime());
        holder.description.setText(post.get(position).getDesc());
        holder.like.setText(String.valueOf(post.get(position).getLike()));
        holder.unlike.setText(String.valueOf(post.get(position).getUnlike()));

    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    public class myviewholder extends RecyclerView.ViewHolder {

        TextView name, time, description, like, unlike;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.post_username_profile);
            time=itemView.findViewById(R.id.post_time_profile);
            description=itemView.findViewById(R.id.post_description_profile);
            like=itemView.findViewById(R.id.like_count_profile);
            unlike=itemView.findViewById(R.id.unlike_count_profile);

        }
    }


}
