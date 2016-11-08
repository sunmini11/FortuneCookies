package com.egco428.a13285;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    protected List<FortuneResults> data = new ArrayList<>();
    public static final int DETAIL_REQ_CODE = 1001;
    ArrayAdapter<FortuneResults> adapter;
    private DataSource dataSource;
    public static final long id = 1;
    public static final String message = "message";
    public static final String timestamp = "timestamp";
    public static final String imgname = "imagename";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView)findViewById(R.id.listView);

        dataSource = new DataSource(this);
        dataSource.open();

        data = dataSource.getAllComments();
        adapter = new CustomAdapter(this,0,data);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long id) {
                final FortuneResults dataDelete = (FortuneResults)adapter.getItem(position);
                dataSource.deleteFortuneResult(dataDelete);
                view.animate().setDuration(1000).alpha(0).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        adapter.remove(dataDelete);
                        adapter.notifyDataSetChanged();
                        view.setAlpha(1);
                    }
                });
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAIL_REQ_CODE) {
            if(resultCode == RESULT_OK){
                dataSource.open();
                String getImgname = data.getStringExtra(imgname);
                String getMessage = data.getStringExtra(message);
                String getTimestamp = data.getStringExtra(timestamp);

                FortuneResults result = dataSource.createMessage(getImgname,getMessage,getTimestamp);
                adapter.add(result);
                adapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this,NewFortuneActivity.class);
        startActivityForResult(intent,DETAIL_REQ_CODE);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.addbutton, menu);
        return true;
    }

    @Override
    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        dataSource.close();
        super.onPause();
    }
}