package com.example.jalpa.firebasepushnotifications;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity {

    private TextView user_id_view;
    private String mUserId;
    private String mCurrentId;

    private EditText mMessageView;
    private Button mSendBtn;
    private String mUserName;
    private ProgressBar mMessageProgress;
    private FirebaseAuth mAuth;

    private FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);

        user_id_view = (TextView) findViewById(R.id.user_id_view);
        mMessageView = (EditText) findViewById(R.id.message_view);
        mSendBtn = (Button) findViewById(R.id.send_btn);
        mFirestore = FirebaseFirestore.getInstance();
        mCurrentId = FirebaseAuth.getInstance().getUid();
        mMessageProgress = (ProgressBar) findViewById(R.id.messageProgress);
        mAuth = FirebaseAuth.getInstance();


         mUserId = getIntent().getStringExtra("user_id");
        mUserName = getIntent().getStringExtra("user_name");

        user_id_view.setText("Send to " + mUserName);
        final String user_id = mAuth.getUid();

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mMessageView.getText().toString();




                if (!message.isEmpty()){

                    mMessageProgress.setVisibility(View.VISIBLE);


                    Map<String , Object> notificationMessage = new HashMap<>();
                    notificationMessage.put("message" , message);
                    notificationMessage.put("from" , mCurrentId);

                    //
                    Toast.makeText(SendActivity.this, ""+ mUserId,Toast.LENGTH_LONG).show();

                    mFirestore.collection("Users/" + mUserId + "/Notifications").add(notificationMessage).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(SendActivity.this , "Notification Sent to... " + mUserId , Toast.LENGTH_LONG).show();

                            mMessageView.setText("");
                            mMessageProgress.setVisibility(View.INVISIBLE);


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toast.makeText(SendActivity.this, ""+ user_id,Toast.LENGTH_LONG).show();

                            Toast.makeText(SendActivity.this , "Error:" +e.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });


                }
                else{
                    //Toast.makeText(SendActivity.this, ""+ user_id,Toast.LENGTH_LONG).show();


                    Toast.makeText(SendActivity.this , "Else portion."  , Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}
