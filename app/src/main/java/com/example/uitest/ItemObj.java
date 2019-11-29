package com.example.uitest;

public class ItemObj {
    private String title;
    private String detail;
    private String imgUrl;
    private String postUrl="";
    public ItemObj(String user, String detail) {
        this.title = user;
        this.detail = detail;

    }
    public ItemObj(String user, String detail, String ImgURL, String PostURL) {
        this.title = user;
        this.detail = detail;
        this.postUrl=PostURL;
        this.imgUrl=ImgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String user) {
        this.title = user;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }
}
