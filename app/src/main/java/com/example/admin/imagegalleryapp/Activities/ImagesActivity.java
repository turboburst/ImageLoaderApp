package com.example.admin.imagegalleryapp.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.imagegalleryapp.Adapters.MyItemDecoration;
import com.example.admin.imagegalleryapp.Adapters.RecyclerAdapter;
import com.example.admin.imagegalleryapp.Constants.Constants;
import com.example.admin.imagegalleryapp.Constants.WebserverConstants;
import com.example.admin.imagegalleryapp.Dialogs.CustomAlertDialogListener;
import com.example.admin.imagegalleryapp.Dialogs.CustomImageDialogListener;
import com.example.admin.imagegalleryapp.R;
import com.example.admin.imagegalleryapp.Servers.OnFailedResponseListener;
import com.example.admin.imagegalleryapp.Servers.OnSuccessResponseListener;
import com.example.admin.imagegalleryapp.models.ImagesManager;
import com.example.admin.imagegalleryapp.models.ModelImage;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.Response;

public class ImagesActivity extends BaseActivity implements CustomAlertDialogListener, CustomImageDialogListener,
        OnSuccessResponseListener, OnFailedResponseListener {
    private ImagesManager imagesManager;
    private RecyclerView imageRecyclerView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imagesManager = ImagesManager.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        imageRecyclerView = findViewById(R.id.recyclerView);
        imageRecyclerView.addItemDecoration(new MyItemDecoration());
        progressBar = findViewById(R.id.images_progressbar);
        progressBar.setVisibility(View.VISIBLE);
        sendRequest(ImagesActivity.this, ImagesActivity.this, "GET", WebserverConstants.endPointURL);
    }



    public static byte[] getBytesFromInputStream(InputStream is) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();
        return outstream.toByteArray();
    }

    @Override
    public void onOKButtonClicked() {
        dismissAlert();
    }

    @Override
    public void onResponseFailed() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
            }
        });
        showAlert(ImagesActivity.this, ImagesActivity.this, null);
    }

    @Override
    public void onResponseSuccessed(Response response) {
        try {
            final InputStream dataInputStream = response.body().byteStream();
            imagesManager.getImagesFromInputstream(dataInputStream);
            if(imagesManager.getRecentImagesSize() != 0){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        RecyclerAdapter myAdapter = new RecyclerAdapter(ImagesActivity.this, imagesManager.getRecentImagesList());
                        imageRecyclerView.setAdapter(myAdapter);
                        GridLayoutManager layoutManager = new GridLayoutManager(ImagesActivity.this, 3);
                        imageRecyclerView.setLayoutManager(layoutManager);
                    }
                });
            }
            else{
                showAlert(ImagesActivity.this, ImagesActivity.this, response.message());
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onImageOKButtonClicked() {
        dismissImageDialog();
    }
}
