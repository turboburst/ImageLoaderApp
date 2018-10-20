package com.example.admin.imagegalleryapp.models;

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
}
