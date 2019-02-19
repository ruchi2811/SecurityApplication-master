package com.example.jalpa.firebasepushnotifications;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.AppGlideModule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.io.InputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.ViewHolder> {

    private List<Users> usersList;
    private Context context;

    public UsersRecyclerAdapter(Context context , List<Users> usersList){
        this.usersList = usersList;
        this.context =context;
    }
    @NonNull
    @Override
    public UsersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_item, parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRecyclerAdapter.ViewHolder holder, int position) {
        holder.user_name_view.setText(usersList.get(position).getName());

        final CircleImageView user_image_view = holder.user_image_view;
        //Glide.with(context).load(usersList.get(position).getImage()).into(user_image_view);
        Glide.with(context).load(usersList.get(position).getImage()).into(user_image_view);
       //Glide.with(context).load(R.drawable.ruchi).into(user_image_view);

        final String user_id = usersList.get(position).User_id;
        final String user_name = usersList.get(position).getName();


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(context, SendActivity.class);
                sendIntent.putExtra("user_id", user_id);
                sendIntent.putExtra("user_name",user_name);
                context.startActivity(sendIntent);
            }
        });



    }
    public static String user_id;

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private CircleImageView user_image_view;
        private TextView user_name_view;

        public ViewHolder(View itemView) {
            super(itemView);

             mView = itemView;

             user_image_view =(CircleImageView) mView.findViewById(R.id.user_list_image);
             user_name_view = (TextView) mView.findViewById(R.id.user_list_name);
        }
    }

    public final class MyAppGlideModule extends AppGlideModule{

        }
    }

