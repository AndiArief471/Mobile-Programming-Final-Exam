package com.example.mobileprogfinalexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class EnrolledSubjectAdapter extends RecyclerView.Adapter<EnrolledSubjectViewHolder> {
    Context context;
    List<SubjectEnroll> subjects;
    FirebaseFirestore db;
    String userEmail; //added

    public EnrolledSubjectAdapter(Context context, List<SubjectEnroll> subjects, String userEmail) {
        this.context = context;
        this.subjects = subjects;
        this.userEmail = userEmail; //added
    }

    @NonNull
    @Override
    public EnrolledSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EnrolledSubjectViewHolder(LayoutInflater.from(context).inflate(R.layout.enrolled_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EnrolledSubjectViewHolder holder, int position) {
        db = FirebaseFirestore.getInstance();

        SubjectEnroll currentSubject = subjects.get(position); //added
        String itemName = currentSubject.getSubjectName(); //added

        holder.subjectName.setText(subjects.get(position).getSubjectName());
        holder.credit.setText(subjects.get(position).getSubjectCredit());

        holder.unenrollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.collection("Credentials").document(userEmail)
                        .collection("Enrolled Subject").document(itemName).delete();

                Activity activity = (Activity) view.getContext();
                Intent intent = activity.getIntent();
                activity.finish();
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }
}
