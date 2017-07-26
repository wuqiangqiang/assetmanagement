package com.awlsoft.asset.model.entry;

public class ComImgTextInfo {

    public String imageUrl;

    public String contentText;

    public boolean isChoosed;

    public String getImageUrl(){
        return imageUrl;
    }

    public String getText(){
        return contentText;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }
}