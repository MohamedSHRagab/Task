package com.mohamedragab.androidtask.models;

public class UserModel {

    private String albumId , userId , name,url,thumbnailUrl;

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public UserModel(String albumId , String userId , String name , String url, String thumbnailUrl){
        this.albumId = albumId;
        this.userId = userId;
        this.name = name;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }


}
