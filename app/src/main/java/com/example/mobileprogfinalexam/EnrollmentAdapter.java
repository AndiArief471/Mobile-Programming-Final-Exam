package com.example.mobileprogfinalexam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EnrollmentAdapter extends RecyclerView.Adapter<EnrollmentViewHolder>{
    Context context;
    List<SubjectEnroll> subjects;
    FirebaseFirestore db;
    String userEmail; //added

    public EnrollmentAdapter(Context context, List<SubjectEnroll> subjects, String userEmail) {
        this.context = context;
        this.subjects = subjects;
        this.userEmail = userEmail; //added
    }

    @NonNull
    @Override
    public EnrollmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EnrollmentViewHolder(LayoutInflater.from(context).inflate(R.layout.enrollment_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EnrollmentViewHolder holder, int position) {
        db = FirebaseFirestore.getInstance();

        SubjectEnroll currentSubject = subjects.get(position); //added
        String itemName = currentSubject.getSubjectName(); //added
        String itemCredit = currentSubject.getSubjectCredit();

        holder.subjectName.setText(subjects.get(position).getSubjectName());
        holder.credit.setText(subjects.get(position).getSubjectCredit());

        holder.enrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubjectEnroll subject = new SubjectEnroll();

                subject.setSubjectName(itemName);
                subject.setSubjectCredit(itemCredit);

                db.collection("Credentials").document(userEmail)
                        .collection("Enrolled Subject").document(itemName).set(subject);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
