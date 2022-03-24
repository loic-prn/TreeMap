package com.lolopixel.treemap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class TreeAdapter extends BaseAdapter {
    ArrayList<Tree> treeArray;
    Context context;

    public TreeAdapter(ArrayList<Tree> treeArray, Context context){
        this.treeArray = treeArray;
        this.context = context;
    }

    @Override
    public int getCount() {
        return treeArray.size();
    }

    @Override
    public Object getItem(int i) {
        return treeArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ConstraintLayout layoutItem;
        LayoutInflater inf = LayoutInflater.from(context);

        if(view == null){
            layoutItem = (ConstraintLayout) inf.inflate(R.layout.item_layout, viewGroup, false);
        }
        else {
            layoutItem = (ConstraintLayout) view;
        }

        TextView tv = (TextView) layoutItem.findViewById(R.id.textViewTree);
        tv.setText(treeArray.get(i).toString());
        return layoutItem;
    }
}
