package com.example.anvanthinh.lovediary.database;


import java.io.Serializable;

public class Story implements Serializable {
    private String id;
    private String title;
    private String content;
    private long date;
    private int poster;
    private int like;
    private int attach;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getPoster() {
        return poster;
    }

    public void setPoster(int poster) {
        this.poster = poster;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getAttach() {
        return attach;
    }

    public void setAttach(int attach) {
        this.attach = attach;
    }

}
