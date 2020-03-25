package com.ivettevaldez.multithreading.demos;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ivettevaldez.multithreading.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UiThreadDemoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UiThreadDemoFragment extends Fragment {

    private static final String TAG = UiThreadDemoFragment.class.getSimpleName();

    public UiThreadDemoFragment() {
        // Required empty public constructor
    }

    public static UiThreadDemoFragment newInstance() {
        return new UiThreadDemoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        logThreadInfo("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_thread_demo_fragment, container, false);

        Button buttonCallback = view.findViewById(R.id.ui_thread_demo_button);
        buttonCallback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logThreadInfo("Button callback");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        logThreadInfo("Background thread");
                    }
                }).start();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logThreadInfo("onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        logThreadInfo("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        logThreadInfo("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        logThreadInfo("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        logThreadInfo("onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        logThreadInfo("onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        logThreadInfo("onDestroy");
    }

    private void logThreadInfo(String eventName) {
        Log.d(
                TAG,
                String.format("Event: %s;\t\tThread name: %s;\tThread ID: %s",
                        eventName,
                        Thread.currentThread().getName(),
                        Thread.currentThread().getId())
        );
    }
}
