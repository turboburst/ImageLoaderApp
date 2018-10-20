package com.example.admin.imagegalleryapp.models;

import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.example.admin.imagegalleryapp.Constants.Constants;
import com.example.admin.imagegalleryapp.Constants.WebserverConstants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ImagesManager{
    private volatile static ImagesManager instance;
    protected ArrayList<ModelImage> recentImagesList = new ArrayList<ModelImage>();

    private ImagesManager(){}

    public static ImagesManager getInstance(){
        if(instance == null){
            synchronized (ImagesManager.class){
                if(instance == null){
                    instance = new ImagesManager();
                }
            }
        }
        return instance;
    }

    public int getRecentImagesSize(){
        if(recentImagesList != null){
            return recentImagesList.size();
        }
        return 0;
    }

    public void insertImage(ModelImage image){
        if(image != null && recentImagesList != null){
            recentImagesList.add(image);
        }
    }

    public void insertImages(ArrayList<ModelImage> images){
        if(recentImagesList != null && images != null){
            for(int i = 0; i < images.size(); i++){
                ModelImage eachImage = images.get(i).clone();
                if(eachImage != null){
                    recentImagesList.add(eachImage);
                }
            }
        }
    }

    public ModelImage getImageByImageId(String id){
        ModelImage targetImage = null;
        if(recentImagesList != null && recentImagesList.size() > 0){
            for(ModelImage eachImage: recentImagesList){
                if(eachImage.getImageId().equals(id)){
                    targetImage = eachImage;
                }
            }
        }
        return targetImage;
    }

    public ArrayList<ModelImage> getRecentImagesList() {
        return recentImagesList;
    }

    public ArrayList<ModelImage> getRecentImagesListByOwner(String owner){
        ArrayList<ModelImage> resultList = new ArrayList<ModelImage>();
        if(recentImagesList != null && recentImagesList.size() > 0){
            for(ModelImage eachImage: recentImagesList){
                if(eachImage.getImageOwner().equals(owner)){
                    resultList.add(eachImage);
                }
            }
        }
        return resultList;
    }

    public void getImagesFromInputstream(InputStream dataInputStream) throws XmlPullParserException {
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
                                insertImage(eachImage);
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
}
