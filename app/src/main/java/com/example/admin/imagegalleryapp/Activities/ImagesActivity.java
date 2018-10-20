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
import android.widget.ImageView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        imagesManager = ImagesManager.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        imageRecyclerView = findViewById(R.id.recyclerView);
        imageRecyclerView.addItemDecoration(new MyItemDecoration());
//        imageRecyclerView.setNestedScrollingEnabled(false);

        sendRequest(ImagesActivity.this, ImagesActivity.this, "GET", WebserverConstants.endPointURL);
    }

    private void dealResponse(InputStream dataInputStream) throws XmlPullParserException {
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(dataInputStream, "UTF-8");
            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_TAG:
                        String tag = parser.getName();
                        if(tag.equalsIgnoreCase("photo")){
                            ModelImage eachImage = new ModelImage();
                            eachImage.setImageId(parser.getAttributeValue(null, "id"));
                            eachImage.setImageOwner(parser.getAttributeValue(null, "owner"));
                            eachImage.setImageSecret(parser.getAttributeValue(null, "secret"));
                            eachImage.setImageServer(Integer.valueOf(parser.getAttributeValue(null, "server")));
                            eachImage.setImageFarm(Integer.valueOf(parser.getAttributeValue(null, "farm")));
                            eachImage.setImageTitle(parser.getAttributeValue(null, "title"));
                            eachImage.setImageIspublic(parser.getAttributeValue(null, "ispublic").equalsIgnoreCase("1")? true: false);
                            eachImage.setImageIsfriend(parser.getAttributeValue(null, "isfriend").equalsIgnoreCase("1")? true: false);
                            eachImage.setImageIsfamily(parser.getAttributeValue(null, "isfamily").equalsIgnoreCase("1")? true: false);
                            String eachImageURL = String.format(WebserverConstants.imageURLFormat, eachImage.getImageFarm(),eachImage.getImageServer(),
                                    eachImage.getImageId(), eachImage.getImageSecret(), Constants.imageSmalllong240);
                            eachImage.setImageURL(eachImageURL);
                            Log.i("debugtest", eachImage.getImageURL());

                            if(!TextUtils.isEmpty(eachImageURL)){
                                imagesManager.insertImage(eachImage);
                            }
                        }
                        break;
                }
                eventType = parser.next();
            }
        }
        catch (XmlPullParserException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
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
        showAlert(ImagesActivity.this, ImagesActivity.this, null);
    }

    @Override
    public void onResponseSuccessed(Response response) {
        try {
            final InputStream dataInputStream = response.body().byteStream();
            dealResponse(dataInputStream);
            if(imagesManager.getRecentImagesSize() != 0){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
