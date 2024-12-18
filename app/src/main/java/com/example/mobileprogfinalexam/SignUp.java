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
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {
    EditText userName, userEmail, userPassword;
    TextView signUpBtn;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        signUpBtn = findViewById(R.id.signUpBtn);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = userName.getText().toString();
                String email = userEmail.getText().toString().toLowerCase();
                String password = userPassword.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(SignUp.this, "Input name, username, and password", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerUser(name, email, password);
                    Intent openLogin = new Intent(SignUp.this, Login.class);
                    startActivity(openLogin);
                    finish();
                }
            }
        });
    }

    private void registerUser(String name, String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignUp.this, "Register Complete", Toast.LENGTH_SHORT).show();
                    addUser(email, name);
                }
                else{
                    String fail = task.getException().getMessage();
                    Toast.makeText(SignUp.this, fail, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addUser(String userEmail, String userName){
        Credentials user = new Credentials();

        user.setUserEmail(userEmail);
        user.setUserName(userName);

        db.collection("Credentials").document(userEmail).set(user);
    }
}