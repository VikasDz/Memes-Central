package com.t.memesworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    private RecyclerView deleteNoticeRecycler;
    private ProgressBar progressBar;
    private ArrayList<NoticeData> list;
    private  NoticeAdapter adapter;


    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private Button mLogoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar = findViewById(R.id.progressBar);
        deleteNoticeRecycler =findViewById(R.id.deleteNoticeRecycler);
        reference = FirebaseDatabase.getInstance().getReference().child("Meme");

        deleteNoticeRecycler.setHasFixedSize(true);
        deleteNoticeRecycler.setLayoutManager(new LinearLayoutManager(this));


        list = new ArrayList<>();
        adapter = new NoticeAdapter(this,list);
        deleteNoticeRecycler.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot Snapshot : snapshot.getChildren()){
                    NoticeData data = Snapshot.getValue(NoticeData.class);
                    list.add(0,data);
                }
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                deleteNoticeRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
            }
        });



        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();

        mLogoutBtn = findViewById(R.id.outbutton);

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                sendUserToLogin();

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(mCurrentUser == null){
            sendUserToLogin();
        }
    }
    private void sendUserToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();


    }
}