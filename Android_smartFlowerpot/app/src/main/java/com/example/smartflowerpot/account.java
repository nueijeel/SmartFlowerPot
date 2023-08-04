package com.example.smartflowerpot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class account extends Fragment {
    private Button btnLogout, btnAccDelete, btnPWChange;
    private TextView txtEmail;
    private FirebaseAuth mAuth;
    private String email;
    private static final String TAG = "Account Delete:";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        btnLogout = (Button) view.findViewById(R.id.BtnLogout);
        btnAccDelete = (Button) view.findViewById(R.id.BtnAccDelete);
        btnPWChange = (Button) view.findViewById(R.id.BtnPWChange);
        txtEmail = (TextView)view.findViewById(R.id.TxtEmail);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        email = currentUser.getEmail();
        txtEmail.setText("Email : "+email);

        btnPWChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "이메일이 발송되었습니다.", Toast.LENGTH_SHORT)
                                            .show();
                                }
                                else{
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "이메일 발송에 실패했습니다.", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            }
                        });
            }
        });
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
        btnAccDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(getActivity());
                dlg.setTitle("정말 삭제하시겠습니까?");
                dlg.setMessage("삭제된 계정은 복구할 수 없습니다.");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        currentUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    email=null;
                                    txtEmail.setText("Email : "+email);
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "계정이 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });






        return view;
    }
}