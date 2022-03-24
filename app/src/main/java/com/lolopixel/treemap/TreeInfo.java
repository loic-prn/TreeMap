package com.lolopixel.treemap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TreeInfo extends AppCompatActivity {
    public static final String KEY_FROM_INFO = "key_info";
    Tree myTree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tree_info);

        Tree t = (Tree) getIntent().getSerializableExtra(MainActivity.KEY_FROM_MAIN_INFO);
        this.myTree = t;
        ((TextView)findViewById(R.id.name)).setText(t.getName());
        ((TextView)findViewById(R.id.cirecumference)).setText(String.valueOf(t.getCircumference()) + "cm");
        ((TextView)findViewById(R.id.height)).setText(String.valueOf(t.getHeight()) + "cm");
        ((TextView)findViewById(R.id.type)).setText(t.getType());
        ((TextView)findViewById(R.id.address)).setText(t.getAddress());
    }

    public void onClickMapSingle(View view) {
        Intent i = new Intent(TreeInfo.this, MapsActivity.class);
        i.putExtra(KEY_FROM_INFO, myTree);
        startActivityForResult(i, 2);
    }
}