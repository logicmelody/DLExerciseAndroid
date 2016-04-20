package com.dl.dlexerciseandroid.spring;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.utility.utils.URIUtils;
import com.dl.dlexerciseandroid.utility.utils.Utils;

import java.util.HashMap;

/**
 * Created by logicmelody on 2016/4/1.
 */

// http://spring.io/guides/gs/consuming-rest-android/
// Demo fragment for consuming Restful web service with Spring
public class ConsumingRestfulWebServiceFragment extends Fragment implements View.OnClickListener,
        GetRequestAsyncTask.FinishedGetRequestListener {

    public static final String TAG = "com.dl.dlexerciseandroid.ConsumingRestfulWebServiceFragment";

    private Context mContext;

    private EditText mUserName;
    private Button mSendRequestButton;

    private TextView mIdText;
    private TextView mContentText;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consuming_restful_web_service, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
    }

    private void findViews() {
        mUserName = (EditText) getView().findViewById(R.id.edit_text_consuming_restful_web_service_user_name);
        mSendRequestButton = (Button) getView().findViewById(R.id.button_consuming_restful_web_service_send_request);
        mIdText = (TextView) getView().findViewById(R.id.text_view_consuming_restful_web_service_id);
        mContentText = (TextView) getView().findViewById(R.id.text_view_consuming_restful_web_service_content);
    }

    private void setupViews() {
        mSendRequestButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_consuming_restful_web_service_send_request:
                sendRequestToSpringServer();
                break;
        }
    }

    private void sendRequestToSpringServer() {
        HashMap<String, String> map = new HashMap<>();
        String userName = mUserName.getText().toString();

        if (!TextUtils.isEmpty(userName)) {
            map.put("name", userName);
        }

        String uri = URIUtils.buildURIString(Utils.URI_SPRING_WEB_SERVICE, map);

        new GetRequestAsyncTask(this).execute(uri);
    }

    @Override
    public void onGetRequestSuccessful(Greeting greeting) {
        if (greeting == null) {
            Toast.makeText(mContext, "Greeting is null", Toast.LENGTH_SHORT).show();
            return;
        }

        mIdText.setText("ID = " + greeting.getId());
        mContentText.setText("Content = " + greeting.getContent());
    }

    @Override
    public void onGetRequestFailed(Greeting greeting) {

    }
}
