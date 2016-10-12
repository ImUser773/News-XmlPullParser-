package com.iamdeveloper.news;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String news_url = "http://iauser773.hostoi.com/News.xml";
    ListView listView;
    ArrayAdapter<NewsModel> adapter;
    List<NewsModel> list_news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);


        new ParserXml().execute(news_url);

    }

    private class ParserXml extends AsyncTask<String,Void,List<NewsModel>>{

        @Override
        protected List<NewsModel> doInBackground(String... strings) {
            try{
                return parserURL(strings[0]);
            }catch(IOException e){
                return null;
            }catch(XmlPullParserException e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<NewsModel> newsModels) {
            list_news = newsModels;
            if(list_news.size() != 0){
                adapter = new ArrayAdapter<NewsModel>(MainActivity.this,
                        android.R.layout.simple_list_item_1,android.R.id.text1,list_news);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                        CustomTabsIntent intent = builder.build();
                        intent.launchUrl(MainActivity.this, Uri.parse(list_news.get(i).getLink()));
                    }
                });
            }
        }

        private List<NewsModel> parserURL(String strings) throws XmlPullParserException,IOException {
            InputStream stream = null;
            NewsParser parser = new NewsParser();
            List<NewsModel> list = null;

            Log.i("parserURL","parserURL");
            try{
                stream = urlStream(strings);
                BufferedReader im = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();
                String read;
                while ((read = im.readLine()) != null){
                    builder.append(read)
                            .append("\n");

                }
                Log.i("conn",builder.toString());

                String in = builder.toString();
                list = parser.inputParser(in);
            }finally{
                if(stream != null){
                    stream.close();
                    Log.i("stream","stream.close();");
                }
            }



            return list;
        }

        private InputStream urlStream(String news_url) throws IOException {

            URL url = new URL(news_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            Log.i("conn",conn.getResponseMessage());

            return conn.getInputStream();
        }
    }
}
