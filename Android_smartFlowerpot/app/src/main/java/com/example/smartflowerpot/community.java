package com.example.smartflowerpot;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class community extends Fragment {

    private Intent intent1;
    private Data data;
    private static final String TAG = "LoadData";
    private ScrollView scroll;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

        Button btnLogout = (Button) getActivity().findViewById(R.id.BtnLogout);
        Button btnWrite = (Button) getActivity().findViewById(R.id.BtnWrite);
        Button btnDelete = (Button) getActivity().findViewById(R.id.BtnDelete);
        RecyclerView recyclerView = getActivity().findViewById(R.id.RecyclerView);
        scroll = (ScrollView) getActivity().findViewById(R.id.Scroll);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        final RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        scroll.setVerticalScrollBarEnabled(true);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "로그아웃되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1 = new Intent(getActivity(), Write.class);
                startActivity(intent1);
            }
        });



        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.removeValue();
                adapter.notifyDataSetChanged();
                onResume();
            }
        });

        ref.child("Data").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                data = snapshot.getValue(Data.class);
                //Toast.makeText(getActivity().getApplicationContext(), data.getTitle(),Toast.LENGTH_SHORT).show();
                adapter.additem(data);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_community, container, false);

        Button btnLogout = (Button) view.findViewById(R.id.BtnLogout);
        Button btnWrite = (Button) view.findViewById(R.id.BtnWrite);
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerView);
        Button btnDelete = (Button) getActivity().findViewById(R.id.BtnDelete);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                Toast.makeText(getActivity(), "로그아웃되었습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent1 = new Intent(getActivity(), Write.class);
                startActivity(intent1);
            }
        });


        return view;
    }

}