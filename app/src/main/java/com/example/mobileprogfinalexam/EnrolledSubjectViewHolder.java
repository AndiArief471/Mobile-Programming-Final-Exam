package com.example.mobileprogfinalexam;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EnrolledSubjectViewHolder extends RecyclerView.ViewHolder {
    TextView subjectName, credit, unenrollBtn;

    public EnrolledSubjectViewHolder(@NonNull View itemView) {
        super(itemView);
        subjectName = itemView.findViewById(R.id.subjectName);
        credit = itemView.findViewById(R.id.credit);
        unenrollBtn = itemView.findViewById(R.id.unenrollBtn);
    }
}
