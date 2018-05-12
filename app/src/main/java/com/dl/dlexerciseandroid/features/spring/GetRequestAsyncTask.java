package com.dl.dlexerciseandroid.features.spring;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by logicmelody on 2016/4/1.
 */
public class GetRequestAsyncTask extends AsyncTask<String, Void, Greeting> {

    public interface FinishedGetRequestListener {
        void onGetRequestSuccessful(Greeting greeting);
        void onGetRequestFailed(Greeting greeting);
    }

    private FinishedGetRequestListener mFinishedGetRequestListener;


    public GetRequestAsyncTask(FinishedGetRequestListener listener) {
        mFinishedGetRequestListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Greeting doInBackground(String... url) {
        try {
            // Use Java object
            RestTemplate restTemplate = new RestTemplate();
            // 要丟一個MessageConverter給RestTemplate
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            // Get Json string from server
            // Assign true給RestTemplate，代表使用default的MessageConverter：
            // ByteArrayHttpMessageConverter, StringHttpMessageConverter, and ResourceHttpMessageConverter
            // 預設已經註冊的MessageConverter會因為Android的版本而不同
            //RestTemplate restTemplateString = new RestTemplate(true);
            //String result = restTemplateString.getForObject("http://rest-service.guides.spring.io/greeting?name={query}",
            //                                                String.class, "Danny");

            RestTemplate restTemplateString = new RestTemplate(true);
            String result = restTemplateString.getForObject(url[0], String.class);

            Log.d("danny", result);

            return restTemplate.getForObject(url[0], Greeting.class);

        } catch (Exception e) {
            Log.e("GetRequestAsyncTask", e.getMessage(), e);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    // 如果我們在doInBackground()中有call cancel(boolean)，則會呼叫這個method，onPostExecute()不會被呼叫
    @Override
    protected void onCancelled(Greeting greeting) {
        super.onCancelled(greeting);
        mFinishedGetRequestListener.onGetRequestFailed(greeting);
    }

    @Override
    protected void onPostExecute(Greeting greeting) {
        super.onPostExecute(greeting);
        mFinishedGetRequestListener.onGetRequestSuccessful(greeting);
    }
}
