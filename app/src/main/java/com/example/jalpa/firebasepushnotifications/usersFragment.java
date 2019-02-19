package com.example.jalpa.firebasepushnotifications;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class usersFragment extends Fragment {

    private RecyclerView mUsersListView;

    private List<Users> usersList;
    private UsersRecyclerAdapter usersRecyclerAdapter;

    private FirebaseFirestore mFirestore;

    public usersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        mFirestore =FirebaseFirestore.getInstance();

        mUsersListView = (RecyclerView) view.findViewById(R.id.users_list);

        usersList = new ArrayList<>();
        usersRecyclerAdapter = new UsersRecyclerAdapter(container.getContext(), usersList);

        mUsersListView.setHasFixedSize(true);
        mUsersListView.setLayoutManager(new LinearLayoutManager(container.getContext()));
        mUsersListView.setAdapter(usersRecyclerAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        usersList.clear();


        mFirestore.collection("Users").addSnapshotListener(getActivity() , new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for(DocumentChange doc: queryDocumentSnapshots.getDocumentChanges()){
                    if (doc.getType() == DocumentChange.Type.ADDED){

                        String user_id = doc.getDocument().getId();

                        Users users = doc.getDocument().toObject(Users.class).WithId(user_id);
                        usersList.add(users);
                        usersRecyclerAdapter.notifyDataSetChanged();

                    }
                }
            }
        });
    }
}
