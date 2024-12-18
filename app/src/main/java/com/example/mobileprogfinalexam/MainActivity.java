package com.example.mobileprogfinalexam;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {
    TextView userName, totalCredit, enrollBtn;
    RecyclerView recyclerView;
    FirebaseFirestore db;
    List<SubjectEnroll> subjects = new ArrayList<>();
    List<SubjectEnroll> subjects2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String UserEmail = getIntent().getStringExtra("UserEmail");
        String UserName = getIntent().getStringExtra("UserName");

        userName = findViewById(R.id.userName);
        totalCredit = findViewById(R.id.totalCredit);
        enrollBtn = findViewById(R.id.enrollBtn);
        recyclerView = findViewById(R.id.recyclerView);

        db = FirebaseFirestore.getInstance();

        userName.setText(String.format("     %s", UserName));

//        subjects2.add(new SubjectEnroll("Abc", "3"));
//        subjects2.add(new SubjectEnroll("Subject 2", "3"));
//        subjects2.add(new SubjectEnroll("Subject 3", "3"));
//
//        recyclerView.setAdapter(new enrolledSubjectAdapter(MainActivity.this, subjects2));
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        fetchItemData(subjects, UserEmail);

        enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openEnrollment = new Intent(MainActivity.this, Enrollment.class);
                openEnrollment.putExtra("UserEmail", UserEmail);
                openEnrollment.putExtra("UserName", UserName);
                startActivity(openEnrollment);
                finish();
            }
        });

    }

    private void fetchItemData(List<SubjectEnroll> subjectList, String userEmail) {
        db.collection("Credentials").document(userEmail).collection("Enrolled Subject")
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
                    int totalCredits = 0;
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        SubjectEnroll subject = document.toObject(SubjectEnroll.class);
                        if (subject != null) {
                            subjectList.add(subject);

                            String creditString = document.getString("subjectCredit");
                            totalCredits += Integer.parseInt(creditString);
                        }
                    }
                    totalCredit.setText(String.valueOf(totalCredits));
                    handleItemList(subjects, userEmail);
                })
                .addOnFailureListener(e -> Log.e("Firestore", "Error fetching documents", e));
    }

    private void handleItemList(List<SubjectEnroll> itemLists, String userEmail) {

        recyclerView.setAdapter(new EnrolledSubjectAdapter(MainActivity.this, itemLists, userEmail));
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }
}