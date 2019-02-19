package com.example.jalpa.firebasepushnotifications;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.jalpa.firebasepushnotifications.R.id.logout_btn;
import static com.example.jalpa.firebasepushnotifications.R.id.profile_image;
//import static com.firebase.ui.auth.AuthUI.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class profileFragment extends Fragment {
    private Button mLogoutBtn;
    private FirebaseAuth mAuth;
    private CircleImageView mProfileImage;
    private TextView mProfileName;
    private FirebaseFirestore mFirestore;
    private String mUserId;

    public profileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        mFirestore = FirebaseFirestore.getInstance();
        mUserId = mAuth.getCurrentUser().getUid();


        mLogoutBtn = (Button) view.findViewById(R.id.logout_btn);
        mProfileImage = (CircleImageView) view.findViewById(R.id.profile_image);
        mProfileName = (TextView) view.findViewById(R.id.profile_name);

        mFirestore.collection("Users").document(mUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                                                        @Override
                                                                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                                                            String user_name = documentSnapshot.getString("name");
                                                                                            mProfileName.setText(user_name);

                                                                                        }
                                                                                    });



        FirebaseStorage.getInstance().getReference().child("images").child(mUserId + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                RequestOptions placeholderOption = new RequestOptions();
                placeholderOption.placeholder(R.mipmap.default_pic);
                Glide.with(container.getContext())
                        .setDefaultRequestOptions(placeholderOption)
                        .load(uri).into(mProfileImage);


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Error : " + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



        //mFirestore.collection("Users").document(mUserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            //@Override
            //public void onSuccess(DocumentSnapshot documentSnapshot) {

                //String user_name = documentSnapshot.getString("name");
                //String user_image = documentSnapshot.getString("image");
               // Uri uri=Uri.parse(user_image);


                //mProfileName.setText(user_name);

                //RequestOptions placeholderOption = new RequestOptions();
                //placeholderOption.placeholder(R.mipmap.default_pic);
                //Glide.with(container.getContext()).setDefaultRequestOptions(placeholderOption).load(user_image).into(mProfileImage);
               //Glide.with(container.getContext()).setDefaultRequestOptions(placeholder).load(user_image).into(mProfileImage);
                //mProfileImage.setImageURI(uri);


            //}
        //});

        mLogoutBtn.setOnClickListener(new View.OnClickListener(){

                                         @Override
                                         public void onClick(View v) {
                                             try {
                                                 //mAuth.signOut();
                                                 Map<String, Object> tokenMapRemove = new HashMap<>();
                                                 tokenMapRemove.put("token_id" , FieldValue.delete());
                                                 mFirestore.collection("Users").document(mUserId).update(tokenMapRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                     @Override
                                                     public void onSuccess(Void aVoid) {
                                                         mAuth.signOut();
                                                         Intent loginIntent = new Intent(getContext(), LoginActivity.class);
                                                         startActivity(loginIntent);
                                                         Toast.makeText(getActivity(), "User Sign out!", Toast.LENGTH_SHORT).show();
                                                     }
                                                 });


                                             }
                                             catch (Exception e){
                                                 Toast.makeText(getActivity(), "Error in Sign out!" + e, Toast.LENGTH_SHORT).show();

                                             }

                                          }
                                     }
       );


        return view;
    }

}
