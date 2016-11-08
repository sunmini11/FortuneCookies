package com.egco428.a13285;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell pc on 31/10/2559.
 */
public class CustomAdapter extends ArrayAdapter<FortuneResults> {
    Context context;
    List<FortuneResults> objects;

    public CustomAdapter(Context context, int resource, List<FortuneResults> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        FortuneResults fortuneResults = objects.get(position);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE); //link with interface
        View view = inflater.inflate(R.layout.listviewrow,null);

        TextView txt = (TextView)view.findViewById(R.id.resultfortune);
        txt.setText(fortuneResults.getMessage());

        TextView txtDT = (TextView)view.findViewById(R.id.datetimeview);
        txtDT.setText(fortuneResults.getTimestamp());

        ImageView image = (ImageView)view.findViewById(R.id.imagecookie);
        int res = context.getResources().getIdentifier(fortuneResults.getImgname(),"drawable",context.getPackageName());
        image.setImageResource(res);
        if(fortuneResults.getImgname().equals("opened_cookie_2")|| fortuneResults.getImgname().equals("opened_cookie_4")){
            txt.setTextColor(Color.parseColor("#FFFF9500"));
        }
        else{
            txt.setTextColor(Color.parseColor("#4c8ecd"));
        }
        return view;
    }

}

