package com.sada.edubuddy;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

public class ConversationFragment extends Fragment implements LoaderManager.LoaderCallbacks<HashMap<String, String>> {

    private static final String TAG = "ConversationFragment";
    private static final int API_LOADER_ID = 1;
    private LinearLayout messagesContainer;
    private EditText etMessage;
    private ImageButton bSendMessage, bRecordMMessage;
    private ScrollView scrollView;
    final private String BASE_URL = "https://edu-buddy.herokuapp.com/query?query=";
    private String QUERY_URL;
    private boolean loaderInitiated = false;

    public static ConversationFragment newInstance() {

        Bundle args = new Bundle();

        ConversationFragment fragment = new ConversationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    public ConversationFragment() {
        // Required empty public constructor
    }

    private void initiateLoader() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {

            LoaderManager loaderManager = getActivity().getSupportLoaderManager();
            if (!loaderInitiated) {
                loaderManager.initLoader(API_LOADER_ID, null, this);
                loaderInitiated = true;
            } else {
                loaderManager.restartLoader(API_LOADER_ID, null, this);
            }


        } else {
            Toast.makeText(getActivity(), "No INTERNET connection", Toast.LENGTH_SHORT).show();
//            View loadingIndicator = findViewById(R.id.loading_indicator);
//            loadingIndicator.setVisibility(View.GONE);
//            avi.hide();
//
//            ((TextView) errorContainer.findViewById(R.id.tvErrorDesc)).setText(R.string.no_conn_error_message);
//            errorContainer.setVisibility(View.VISIBLE);
        }
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
                String message = etMessage.getText().toString().trim();
                if (message.length() != 0) {
                    View messageView = generateView(message, "user");
                    etMessage.setText("");
                    messagesContainer.addView(messageView);
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollView.scrollTo(0, scrollView.getBottom());
                        }
                    });
                    QUERY_URL = BASE_URL + message;
                    initiateLoader();
                }
            }
        });

        ConstraintLayout layout = new ConstraintLayout(getActivity());


        return rootView;
    }

    public View generateView(CharSequence message, String data_1) {
        LayoutInflater inflater1 = LayoutInflater.from(getContext());
        View view = null;
        TextView etIndividualMsg = null;
        if (data_1.equals("user")) {
            view = inflater1.inflate(R.layout.message_from_user, null);
            etIndividualMsg = (TextView) view.findViewById(R.id.userMessage);
        } else {
            view = inflater1.inflate(R.layout.message_from_bot, null);
            etIndividualMsg = (TextView) view.findViewById(R.id.botMessage);
        }
        etIndividualMsg.setText(message);
        return view;
    }

    @Override
    public Loader<HashMap<String, String>> onCreateLoader(int id, Bundle args) {
        Log.i(TAG, "onCreateLoader: QUERY URL = " + QUERY_URL);
        return new BotMessageLoader(getActivity(), QUERY_URL);
    }

    @Override
    public void onLoadFinished(Loader<HashMap<String, String>> loader, HashMap<String, String> data) {
        CharSequence msg = data.get("intent") + " : " + data.get("data_1") + " : " + data.get("data_2");
        Log.i(TAG, "onLoadFinished: " + msg);
//        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();

        String intent = data.get("intent");
        String data_1 = data.get("data_1");
        String data_2 = data.get("data_2");

        switch (intent) {
            case "showTimeline":
                msg = "Taking you to your Timeline";
                ViewPager viewPager = getActivity().findViewById(R.id.container);
                viewPager.setCurrentItem(0);
                break;
            case "addEvent":
                addEvent();
                break;
            case "getNews":
                int textSize1 = getResources().getDimensionPixelSize(R.dimen.text_size_1);
                int textSize2 = getResources().getDimensionPixelSize(R.dimen.text_size_2);

                String text1 = data_1;
                String text2 = data_2;

                SpannableString span1 = new SpannableString(text1);
                span1.setSpan(new AbsoluteSizeSpan(textSize1), 0, text1.length(), SPAN_INCLUSIVE_INCLUSIVE);

                SpannableString span2 = new SpannableString(text2);
                span2.setSpan(new AbsoluteSizeSpan(textSize2), 0, text2.length(), SPAN_INCLUSIVE_INCLUSIVE);

                msg = TextUtils.concat(span1, "\n", span2);
                break;
            case "foundError":
                msg = "Sorry I did not understand that!!!";
                break;
        }
        View messageView = generateView(msg, "bot");
        etMessage.setText("");
        messagesContainer.addView(messageView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.scrollTo(0, scrollView.getBottom());
            }
        });

    }

    private void addEvent() {

    }


    @Override
    public void onLoaderReset(Loader<HashMap<String, String>> loader) {

    }
}
