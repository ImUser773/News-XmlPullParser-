package com.iamdeveloper.news;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IamDeveloper on 9/26/2016.
 */
public class NewsModel {

    private String title;
    private String link;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
