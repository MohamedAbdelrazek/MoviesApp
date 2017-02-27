package com.zoka.moviesapp.Models;

/**
 * Created by Mohamed AbdelraZek on 2/27/2017.
 */

public class ReviewModel {
    private String authorName;
    private String content;
    private String url;

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
