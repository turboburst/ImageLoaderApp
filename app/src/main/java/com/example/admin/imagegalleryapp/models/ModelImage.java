package com.example.admin.imagegalleryapp.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ModelImage implements Serializable{
    private String imageId;
    private String imageOwner;
    private String imageSecret;
    private int serverId;
    private int imageWidth;
    private int imageHeight;
    private int farmId;
    private String title;
    private String imageURL;
    private boolean isPublic;
    private boolean isFriend;
    private boolean isFamily;
    private String imageSize;
    private double imageSizeKB;

    public int getImageWidth(){return imageWidth;}
    public int getImageHeight(){return imageHeight;}
    public double getImageSizeKB(){return imageSizeKB;}
    public String getImageId(){return imageId;}
    public String getImageOwner(){return imageOwner;}
    public String getImageSecret(){return imageSecret;}
    public int getImageServer(){return serverId;}
    public int getImageFarm(){return farmId;}
    public String getImageTitle(){return title;}
    public String getImageURL(){return imageURL;}
    public boolean getImageIspublic(){return isPublic;}
    public boolean getImageIsfriend(){return isFriend;}
    public boolean getImageIsfamily(){return isFamily;}
    public String getImageSize(){return imageSize;}

    public void setImageId(String imageId){this.imageId = imageId;}
    public void setImageOwner(String imageOwner){this.imageOwner = imageOwner;}
    public void setImageSecret(String imageSecret){this.imageSecret = imageSecret;}
    public void setImageServer(int serverId){this.serverId = serverId;}
    public void setImageFarm(int farmId){this.farmId = farmId;}
    public void setImageTitle(String title){this.title = title;}
    public void setImageURL(String imageURL){this.imageURL = imageURL;}
    public void setImageIspublic(boolean isPublic){this.isPublic = isPublic;}
    public void setImageIsfriend(boolean isFriend){this.isFriend = isFriend;}
    public void setImageIsfamily(boolean isFamily){this.isFamily = isFamily;}
    public void setImageSize(String imageSize){this.imageSize = imageSize;}
    public void setImageSizeKB(double imageSizeKB){this.imageSizeKB = imageSizeKB;}
    public void setImageWidth(int imageWidth){this.imageWidth = imageWidth;}
    public void setImageHeight(int imageHeight){this.imageHeight = imageHeight;}

    public ModelImage clone(){
        ModelImage clonedImage = new ModelImage();
        clonedImage.setImageId(this.imageId);
        clonedImage.setImageOwner(this.imageOwner);
        clonedImage.setImageSecret(this.imageSecret);
        clonedImage.setImageServer(this.serverId);
        clonedImage.setImageFarm(this.farmId);
        clonedImage.setImageTitle(this.title);
        clonedImage.setImageURL(this.imageURL);
        clonedImage.setImageIspublic(this.isPublic);
        clonedImage.setImageIsfriend(this.isFriend);
        clonedImage.setImageIsfamily(this.isFamily);
        clonedImage.setImageSize(this.imageSize);
        clonedImage.setImageSizeKB(this.imageSizeKB);
        clonedImage.setImageWidth(this.imageWidth);
        clonedImage.setImageHeight(this.imageHeight);
        return clonedImage;
    }
}
