package com.akingyin.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jakewharton.rxbinding.view.RxView;
import com.md.multipleapp.R;
import com.pddstudio.talking.Talk;
import com.pddstudio.talking.model.SpeechObject;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by zlcd on 2016/2/4.
 */
public class VoiceFragment extends Fragment implements Talk.Callback{

    public   static   VoiceFragment   newInstance(){
        VoiceFragment   fragment = new VoiceFragment();
        return  fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Talk.init(getContext(),this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Talk.getInstance().stopListening();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View   view   = inflater.inflate(R.layout.fragment_voice,null);
        return  view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RxView.clicks(view.findViewById(R.id.voice_add))
            .throttleFirst(1, TimeUnit.SECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if(hasPermission()) {
                    Talk.getInstance().startListening();
                } else {
                    requestPermission();
                }
            }
        });

    }


    private boolean hasPermission() {
        return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 42);
        }
    }

    @Override
    public void onStartListening() {

    }

    @Override
    public void onRmsChanged(float rms) {

    }

    @Override
    public void onFailedListening(int errorCode) {
         System.out.println("error="+errorCode);
    }

    @Override
    public void onFinishedListening(SpeechObject speechObject) {
       System.out.println("finish=="+speechObject.getVoiceString());
    }
}
