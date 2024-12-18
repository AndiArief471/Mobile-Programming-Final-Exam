package com.example.mobileprogfinalexam;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EnrollmentViewHolder extends RecyclerView.ViewHolder {
    TextView subjectName, credit, enrollBtn;

    public EnrollmentViewHolder(@NonNull View itemView) {
        super(itemView);
        subjectName = itemView.findViewById(R.id.subjectName);
        credit = itemView.findViewById(R.id.credit);
        enrollBtn = itemView.findViewById(R.id.enrollBtn);
    }
}
