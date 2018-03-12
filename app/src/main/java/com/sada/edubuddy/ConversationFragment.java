package com.sada.edubuddy;


import android.media.Image;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ConversationFragment extends Fragment {

    private static final String TAG = "ConversationFragment";
    private LinearLayout messagesContainer;
    private EditText etMessage;
    private ImageButton bSendMessage, bRecordMMessage;
    private ScrollView scrollView;

    public static ConversationFragment newInstance() {

        Bundle args = new Bundle();

        ConversationFragment fragment = new ConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ConversationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_conversation, container, false);

        messagesContainer = (LinearLayout) rootView.findViewById(R.id.messagesContainer);
        etMessage = (EditText) rootView.findViewById(R.id.etMessage);
        bRecordMMessage = (ImageButton) rootView.findViewById(R.id.bRecordMessage);
        bSendMessage = (ImageButton) rootView.findViewById(R.id.bSendMessage);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
        bSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater1 = LayoutInflater.from(getContext());
                View userMessageView = inflater1.inflate(R.layout.message_from_user, null);
                TextView etIndividualMsg = (TextView) userMessageView.findViewById(R.id.userMessage);
                String message = etMessage.getText().toString().trim();
                if (message.length() != 0) {
                    etIndividualMsg.setText(message);
                    etMessage.setText("");
                    messagesContainer.addView(userMessageView);
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.scrollTo(0, scrollView.getBottom());
                        }
                    });
                }
            }
        });

        ConstraintLayout layout = new ConstraintLayout(getActivity());


        return rootView;
    }

}
