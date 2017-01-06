package com.dl.dlexerciseandroid.ui.speechrecognition;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dl.dlexerciseandroid.R;

import java.util.List;
import java.util.Locale;

/**
 * Created by dannylin on 2017/1/6.
 */

// Reference: http://chengabriel.blogspot.tw/2016/04/android-dialog.html
// http://blog.ucan.csie.au.edu.tw/2012/07/android_11.html
// 用 Google 提供的 SpeechRecognizer 來實作 speech to text
public class SpeechRecognitionFragment extends Fragment implements View.OnClickListener, RecognitionListener {

    public static final String TAG = SpeechRecognitionFragment.class.getName();

    private Context mContext;

    private TextView mSpeechText;
    private ImageView mMicButton;

    private SpeechRecognizer mSpeechRecognizer;

    private boolean mIsListening = false;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_speech_recognition, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initialize();
    }

    private void initialize() {
        findViews();
        setupViews();
        setupSpeechRecognition();
    }

    private void findViews() {
        mSpeechText = (TextView) getView().findViewById(R.id.text_view_speech_recognition_text);
        mMicButton = (ImageView) getView().findViewById(R.id.image_view_speech_recognition_mic_button);
    }

    private void setupViews() {
        mMicButton.setOnClickListener(this);
    }

    private void setupSpeechRecognition() {
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(mContext);
        mSpeechRecognizer.setRecognitionListener(this);
    }

    private Intent getRecognizerIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // 設定語言的時候，可以使用Locale中的constant變數，正確且方便
        // 繁體中文：Locale.TRADITIONAL_CHINESE.toString()
        // 簡體中文：Locale.SIMPLIFIED_CHINESE.toString()
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.TRADITIONAL_CHINESE.toString());
        Log.d("danny", "EXTRA_LANGUAGE = " + Locale.TRADITIONAL_CHINESE.toString());

        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, mContext.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        return intent;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_speech_recognition_mic_button:
                if (!mIsListening) {
                    startListening();

                } else {
                    cancel();
                }

                break;
        }
    }

    private void startListening() {
        setMicButtonListening(true);
        mSpeechRecognizer.startListening(getRecognizerIntent());
    }

    private void setMicButtonListening(boolean isListening) {
        mMicButton.setImageResource(isListening ?
                R.drawable.ic_speech_recognition_mic_recording : R.drawable.ic_speech_recognition_mic);

        mIsListening = !mIsListening;
    }

    private void stopListening(){
        mSpeechRecognizer.stopListening();
        setMicButtonListening(false);
    }

    private void cancel(){
        mSpeechRecognizer.cancel();
        setMicButtonListening(false);
    }

    @Override
    public void onDestroy() {
        mSpeechRecognizer.destroy();

        super.onDestroy();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // RecognitionListener
    // 1. onReadyForSpeech
    // 2. onBeginningOfSpeech
    // 3. [trigger multiple times] onRmsChanged, onBufferReceived, onPartialResults
    // 4. onEndOfSpeech
    // 5. [one of them] onResult  /  onError
    //
    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.d("danny", "準備就緒，可以開始說話");
    }

    @Override
    public void onBeginningOfSpeech() {
        Log.d("danny", "檢測用戶已經開始說話");
    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        Log.d("danny", "檢測到用戶已經停止說話");
    }

    @Override
    public void onError(int error) {
        String errorMessage = getErrorText(error);
        Log.d("danny", "FAILED " + errorMessage);

        mSpeechText.setText("");
        setMicButtonListening(false);
    }

    public String getErrorText(int errorCode) {
        String message;

        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }

        return message;
    }

    @Override
    public void onResults(Bundle results) {
        Log.d("danny", "onResults");

        setMicButtonListening(false);

        // matches的ArrayList會儲存所有可能的辨識結果，通常我們會取index = 0，代表機率最高的辨識結果
        List<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        mSpeechText.setText(matches.get(0));
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        List<String> matches = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        mSpeechText.setText(matches.get(0));
    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
}
