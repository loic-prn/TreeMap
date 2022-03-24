package com.lolopixel.treemap;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String KEY_FROM_MAIN_INFO = "key_contact";
    public static final String KEY_FROM_MAIN_LIST = "key_list";
    private int index = 0;
    private TreeAdapter ada;
    private ArrayList<Tree> treeList;
    private String API = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=arbresremarquablesparis2011&q=&rows=30&start=" + index + "&facet=libellefrancais&facet=genre&facet=espece";
    private String API_LOAD_ALL = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=arbresremarquablesparis2011&q=&rows=200&start=0&facet=libellefrancais&facet=genre&facet=espece";
    final private String URL_TIME_JSON =  "http://worldtimeapi.org/api/timezone/Europe/paris";
    final private String HTML_STR_DEFAULT = "<iframe src=\"https://public.opendatasoft.com/explore/embed/dataset/arbresremarquablesparis2011/map/?lang=&dataChart=eyJxdWVyaWVzIjpbeyJjaGFydHMiOlt7InR5cGUiOiJsaW5lIiwiZnVuYyI6IkFWRyIsInlBeGlzIjoiaWRiYXNlIiwic2NpZW50aWZpY0Rpc3BsYXkiOnRydWUsImNvbG9yIjoiIzI2Mzg5MiJ9XSwieEF4aXMiOiJnZW5yZSIsIm1heHBvaW50cyI6IiIsInRpbWVzY2FsZSI6bnVsbCwic29ydCI6IiIsImNvbmZpZyI6eyJkYXRhc2V0IjoiYXJicmVzcmVtYXJxdWFibGVzcGFyaXMyMDExIiwib3B0aW9ucyI6eyJiYXNlbWFwIjoiamF3Zy5kYXJrIiwibG9jYXRpb24iOiIxMiw0OC44ODE5OSwyLjMzMzIyIn19fV0sImRpc3BsYXlMZWdlbmQiOnRydWUsImFsaWduTW9udGgiOnRydWUsInRpbWVzY2FsZSI6IiJ9&basemap=jawg.dark&location=13,48.85912,2.3267&static=false&datasetcard=false&scrollWheelZoom=false\" width=\"400\" height=\"300\" frameborder=\"0\"></iframe>";

    final private String KEY_FROM_MAIN ="Key";

    private void incrementURL(){
        index += 20;
        this.API = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=arbresremarquablesparis2011&q=&rows=20&start=" + index + "&facet=libellefrancais&facet=genre&facet=espece";
    }

    private void decrementURL(){
        if(index > 0) {
            index -= 20;
        }
        this.API = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=arbresremarquablesparis2011&q=&rows=20&start=" + index + "&facet=libellefrancais&facet=genre&facet=espece";
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        treeList = new ArrayList<>();
        ListView listViewTree = findViewById(R.id.listView);
        ada = new TreeAdapter(treeList, MainActivity.this);
        listViewTree.setAdapter(ada);
        if(this.isNetworkAvailable()){
            new HttpRequest().execute(API, ada, treeList);
        }
        else {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }


        listViewTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent i = new Intent(MainActivity.this, TreeInfo.class);
                i.putExtra(KEY_FROM_MAIN_INFO, treeList.get(position));
                startActivityForResult(i, 1);
            }
        });

        listViewTree.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                return true;
            }
        });

        listViewTree.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                if(listViewTree.getLastVisiblePosition() == listViewTree.getAdapter().getCount() - 1 && listViewTree.getChildAt(listViewTree.getChildCount()- 1).getBottom() <= listViewTree.getHeight()){
                    incrementURL();
                    new HttpRequest().execute(API, ada, treeList);
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int first, int count, int total) {
            }
        });
    }

    public void onClickMap(View view) {
        Intent intent = new Intent(MainActivity.this, MapsActivity.class);
        intent.putExtra(KEY_FROM_MAIN_LIST, treeList);
        startActivityForResult(intent, 1);
    }

    public void onClickRefresh(View view) {
        if(this.isNetworkAvailable()){
            String reqUrl = "https://public.opendatasoft.com/api/records/1.0/search/?dataset=arbresremarquablesparis2011&q=&rows="+ (30 + index) +"&start=0&facet=libellefrancais&facet=genre&facet=espece";
            this.treeList.clear();
            new HttpRequest().execute(reqUrl, ada, treeList);
        }
        else {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickLoadAll(View view) {
        if(this.isNetworkAvailable()){
            new HttpRequest().execute(API_LOAD_ALL, ada, treeList);
        }
        else {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_LONG).show();
        }
    }
}