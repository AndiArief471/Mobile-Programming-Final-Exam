package com.example.mobileprogfinalexam;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText userEmail, userPassword;
    TextView loginBtn, signUpBtn;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        loginBtn = findViewById(R.id.loginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = userEmail.getText().toString();
                String password = userPassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this, "Input username and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    userLogin(email, password);
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openSignUp = new Intent(Login.this, SignUp.class);
                startActivity(openSignUp);
            }
        });
    }

    private void userLogin(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    DocumentReference doc = db.collection("Credentials").document(email);

                    doc.get().addOnCompleteListener(tasks -> {
                        DocumentSnapshot document = tasks.getResult();
                        String userEmail = document.getString("userEmail");
                        String userName = document.getString("userName");

                        Intent openMainActivity = new Intent(Login.this, MainActivity.class);
                        openMainActivity.putExtra("UserEmail", userEmail);
                        openMainActivity.putExtra("UserName", userName);
                        startActivity(openMainActivity);
                        finish();
                    });
                }
                else{
                    String fail = task.getException().getMessage();
                    Toast.makeText(Login.this, fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}