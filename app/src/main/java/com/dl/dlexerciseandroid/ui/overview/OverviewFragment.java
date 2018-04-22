package com.dl.dlexerciseandroid.ui.overview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;
import com.dl.dlexerciseandroid.model.PersonData;
import com.google.gson.Gson;

/**
 * Created by logicmelody on 2016/3/30.
 */
public class OverviewFragment extends Fragment {

    // 以後Fragment的tag name都用此class的name來命名比較方便
    // e.g. MusicPlayerFragment
    public static final String TAG = OverviewFragment.class.getName();

    private Context mContext;

    private TextView mGtmText;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        testGson();
    }

    private void findViews() {
        mGtmText = (TextView) getView().findViewById(R.id.text_view_overview_gtm_text);
    }

    /**
     * 在使用Gson的時候，要把思維轉換成Json字串跟物件之間的轉換
     * Serialization:   Object -> Json string
     * Deserialization: Json string -> Object
     *
     * 只要把Java object設定好，基本上就沒有問題了
     * 這裡的例子是將一串Json的字串轉換成Java object，是個很常見的使用情境
     */
    private void testGson() {
        String jsonString = "{\n" +
                "  \"age\": \"23\",\n" +
                "  \"weight\": \"58\",\n" +
                "  \"height\": \"168\"\n" +
                "}";

        Gson gson = new Gson();
        PersonData personData = gson.fromJson(jsonString, PersonData.class);

        Log.d("danny", "testGson() " + personData.toString());
    }
}
