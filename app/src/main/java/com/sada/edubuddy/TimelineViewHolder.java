package com.sada.edubuddy;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by Shantanu on 3/10/2018.
 */

public class TimelineViewHolder extends RecyclerView.ViewHolder {

    private static final String TAG = "TimelineViewHolder";
    View view;
    ImageView iconIdentifier;

    FirebaseAuth mAuth;
    DatabaseReference mDatabaseLikes;

    public TimelineViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        iconIdentifier = (ImageView) view.findViewById(R.id.iconIdentifier);
        mAuth = FirebaseAuth.getInstance();
    }

    public void setFrom(String from) {
        TextView tvFrom = (TextView) view.findViewById(R.id.tvFrom);
        tvFrom.setText(from);
    }

    public void setTo(String to) {
        TextView tvTo = (TextView) view.findViewById(R.id.tvTo);
        tvTo.setText(to);
    }

    public void setDate(String date) {
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        tvDate.setText(date);
    }

    public void setDescription(String description) {
        TextView tvDate = (TextView) view.findViewById(R.id.tvDescription);
        tvDate.setText(description);
    }

    public void setIconIdentifier(String iconIdentifierValue) {
        if (iconIdentifierValue == null) {
            iconIdentifier.setImageResource(R.drawable.ic_before);
        } else if (iconIdentifierValue.equals("after")) {
            iconIdentifier.setImageResource(R.drawable.ic_after);
        } else {
            iconIdentifier.setImageResource(R.drawable.ic_before);
        }
    }
}