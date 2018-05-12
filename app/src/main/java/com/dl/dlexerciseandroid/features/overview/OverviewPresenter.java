package com.dl.dlexerciseandroid.features.overview;

import android.util.Log;

import com.dl.dlexerciseandroid.data.model.PersonData;
import com.google.gson.Gson;

public class OverviewPresenter implements OverviewContract.Presenter {

    private OverviewContract.View mView;


    public OverviewPresenter(OverviewContract.View view) {
        mView = view;
    }

    @Override
    public void start() {
        testGson();
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
