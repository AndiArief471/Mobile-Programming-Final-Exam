package com.example.mobileprogfinalexam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Enrollment extends AppCompatActivity {
    RecyclerView recyclerView;
    FirebaseFirestore db;
    List<SubjectEnroll> subjects = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_enrollment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        String UserEmail = getIntent().getStringExtra("UserEmail");


        recyclerView = findViewById(R.id.recyclerView);

        db = FirebaseFirestore.getInstance();

        fetchItemData(subjects, UserEmail);
    }
    private void fetchItemData(List<SubjectEnroll> subjectList, String userEmail) {
        db.collection("Enrollment Subjects")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        SubjectEnroll subject = document.toObject(SubjectEnroll.class);
                        if (subject != null) {
                            subjectList.add(subject);
                            Log.e("TestLog", "Document data: " + document.getData());
                        }
                    }
                    handleItemList(subjects, userEmail);
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching documents", e));
    }
    private void handleItemList(List<SubjectEnroll> itemLists, String userEmail) {

        recyclerView.setAdapter(new EnrollmentAdapter(Enrollment.this, itemLists, userEmail));
        recyclerView.setLayoutManager(new LinearLayoutManager(Enrollment.this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String UserEmail = getIntent().getStringExtra("UserEmail");
        String UserName = getIntent().getStringExtra("UserName");
        Intent openMainActivity = new Intent(Enrollment.this, MainActivity.class);
        openMainActivity.putExtra("UserEmail", UserEmail);
        openMainActivity.putExtra("UserName", UserName);
        startActivity(openMainActivity);
        finish();
    }
}