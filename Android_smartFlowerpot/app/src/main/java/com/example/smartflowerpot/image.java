package com.example.smartflowerpot;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class image extends Fragment {

    private static final String TAG = "ImageUpload";
    private ImageView imgPlant;
    private ProgressBar progressView;
    private Uri imageUri;
    private final FirebaseStorage storage = FirebaseStorage.getInstance();
    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference("Image");
    private StorageReference storageRef = storage.getReference();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);

        Button btnLogout = (Button) view.findViewById(R.id.BtnLogout);
        Button btnUploadPic = (Button) view.findViewById(R.id.BtnUploadPic);
        Button btnRegister = (Button) view.findViewById(R.id.BtnRegister);
        Button btnDeletePic = (Button) view.findViewById(R.id.BtnDeletePic);

        imgPlant = (ImageView) view.findViewById(R.id.ImgPlant);
        progressView = (ProgressBar) view.findViewById(R.id.ProgressView);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://fir-test-5e529.appspot.com/");
        storageRef = storage.getReference();
        if (storageRef.child("image.jpg") != null) {
            storageRef.child("image.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(getActivity().getApplicationContext()).load(uri).into(imgPlant);
                }
            });
        }

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

        btnUploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    mStartForResult.launch(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageUri != null){
                    uploadToFirebase(imageUri);
                }
                else{
                    Toast.makeText(getActivity(), "사진을 선택해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDeletePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    storageRef.child("image.jpg").delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "이미지가 성공적으로 삭제되었습니다.",
                                    Toast.LENGTH_SHORT).show();
                            imgPlant.setImageResource(R.drawable.plant1);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "이미지 삭제에 실패하였습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });
        return view;
    }
    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK && result.getData() != null){
                    try {
                        imageUri = result.getData().getData();
                        Glide.with(getActivity().getApplicationContext())
                                .load(imageUri).into(imgPlant);
                        Toast.makeText(getActivity(), "사진 불러오기에 성공하였습니다.",
                                Toast.LENGTH_SHORT).show();
                    }catch (Exception e) {
                        Toast.makeText(getActivity(), "사진을 불러오지 못했습니다.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
    private void uploadToFirebase(Uri uri){
        StorageReference fileRef = storageRef.child("image." + getFileExtension(uri));

        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Model model = new Model(uri.toString());
                        String modelId = root.push().getKey();
                        root.child(modelId).setValue(model);
                        progressView.setVisibility(View.INVISIBLE);
                        Toast.makeText(getActivity(),"업로드 성공", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressView.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressView.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(), "업로드 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private String getFileExtension(Uri uri){
        ContentResolver cr = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}