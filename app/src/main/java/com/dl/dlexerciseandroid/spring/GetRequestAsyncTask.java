package com.dl.dlexerciseandroid.spring;

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
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

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
