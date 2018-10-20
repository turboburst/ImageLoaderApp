package com.example.admin.imagegalleryapp.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.imagegalleryapp.Dialogs.CustomAlertDialog;
import com.example.admin.imagegalleryapp.Dialogs.CustomAlertDialogListener;
import com.example.admin.imagegalleryapp.Dialogs.CustomImageDialog;
import com.example.admin.imagegalleryapp.Dialogs.CustomImageDialogListener;
import com.example.admin.imagegalleryapp.Servers.OnFailedResponseListener;
import com.example.admin.imagegalleryapp.Servers.OnSuccessResponseListener;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BaseActivity extends AppCompatActivity {
    private CustomAlertDialog customAlertDialog;
    private CustomImageDialog customImageDialog;
    private OnSuccessResponseListener onResponseSuccessListener;
    private OnFailedResponseListener onResponseFailedListener;

    protected void sendRequest(final OnSuccessResponseListener onResponseSuccessListener, final OnFailedResponseListener onResponseFailedListener,
                               String method, String endpointURL){
        this.onResponseSuccessListener = onResponseSuccessListener;
        this.onResponseFailedListener = onResponseFailedListener;
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(endpointURL)
                .method(method, null).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onResponseFailedListener.onResponseFailed();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200){
                    onResponseSuccessListener.onResponseSuccessed(response);
                }
                else{
                    onResponseFailedListener.onResponseFailed();
                }
            }
        });

    }

    public void showAlert(final Context context, final CustomAlertDialogListener listener, final String message){
        if(context != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    customAlertDialog = new CustomAlertDialog(context, listener, message, false);
                    customAlertDialog.show();
                }
            });

        }
    }

    public void showImageDialog(final Context context, final CustomImageDialogListener listener,
                                final String message, final String imageURL){
        if(context != null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    customImageDialog = new CustomImageDialog(context, listener, message, imageURL, false);
                    customImageDialog.show();
                }
            });
        }
    }

    public void dismissAlert(){
        if(customAlertDialog != null && customAlertDialog.isShowing()){
            customAlertDialog.dismiss();
        }
    }
    public void dismissImageDialog(){
        if(customImageDialog != null && customImageDialog.isShowing()){
            customImageDialog.dismiss();
        }
    }
}
