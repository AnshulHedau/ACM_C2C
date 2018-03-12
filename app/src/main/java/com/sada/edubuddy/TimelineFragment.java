package com.sada.edubuddy;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimelineFragment extends Fragment {

    private static final String TAG = "TimelineFragment";
    RecyclerView rvTimeline;
    private FirebaseAuth firebaseAuth;

    public static TimelineFragment newInstance() {
        
        Bundle args = new Bundle();
        
        TimelineFragment fragment = new TimelineFragment();
        fragment.setArguments(args);
        return fragment;
    }
    public TimelineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);

        rvTimeline = rootView.findViewById(R.id.rvTimeline);
        rvTimeline.setHasFixedSize(true);
        rvTimeline.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid().toString();
        final DatabaseReference databaseTimelines = FirebaseDatabase.getInstance().getReference().child("users").child(userId).child("schedule");

        FirebaseRecyclerAdapter<Timeline, TimelineViewHolder> timelinesAdapter = new FirebaseRecyclerAdapter<Timeline, TimelineViewHolder>(
                Timeline.class,
                R.layout.timeline_item_layout,
                TimelineViewHolder.class,
                databaseTimelines
        ) {
            @Override
            protected void populateViewHolder(final TimelineViewHolder viewHolder, final Timeline model, final int position) {
                final String TimelineKey = getRef(position).getKey().toString();

                viewHolder.setFrom(model.getFrom());
                viewHolder.setTo(model.getTo());
                viewHolder.setDate(model.getDate());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setIconIdentifier(model.getIcon_identifier());
            }
        };

        timelinesAdapter.notifyDataSetChanged();
        rvTimeline.setAdapter(timelinesAdapter);

        ((FloatingActionButton) rootView.findViewById(R.id.logout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().finish();
            }
        });

        return rootView;
    }

}
