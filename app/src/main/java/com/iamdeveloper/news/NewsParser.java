package com.iamdeveloper.news;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IamDeveloper on 9/26/2016.
 */
public class NewsParser {
    private static final String TAG_NEWS = "news";
    private static final String TAG_TITLE = "title";
    private static final String TAG_LINK = "link";
    private String text_news;
    private NewsModel newsModel;
    private List<NewsModel> list_news = new ArrayList<>();


    public List<NewsModel> inputParser(String in) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            parser = factory.newPullParser();

            parser.setInput(new StringReader(in));

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String TAG_NAME = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (TAG_NAME.equalsIgnoreCase(TAG_NEWS)) {
                            Log.i("TAG_NAME", TAG_NAME + "");
                            newsModel = new NewsModel();

                        } else if (TAG_NAME.equalsIgnoreCase(TAG_TITLE)) {
                            Log.i("START", TAG_NAME + "");
                        } else if (TAG_NAME.equalsIgnoreCase(TAG_LINK)) {
                            Log.i("START", TAG_NAME + "");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text_news = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (TAG_NAME.equalsIgnoreCase(TAG_NEWS)) {
                            Log.i("TAG_NAME", text_news + "");


                        } else if (TAG_NAME.equalsIgnoreCase(TAG_TITLE)) {
                            Log.i("TITLE", text_news + "");
                            newsModel.setTitle(text_news);


                        } else if (TAG_NAME.equalsIgnoreCase(TAG_LINK)) {
                            Log.i("LINK", text_news + "");
                            newsModel.setLink(text_news);
                            list_news.add(newsModel);


                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();

            }
        } catch (XmlPullParserException e) {
            Log.i("XmlPullParserException", e.toString());
        }


        Log.i("END LOOP", "end");
        Log.i("END LOOP", list_news.size() +"");
        Log.i("END LOOP", list_news.get(1).getLink() +"");


        return list_news;

    }
}
