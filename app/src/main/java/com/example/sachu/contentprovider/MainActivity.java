package com.example.sachu.contentprovider;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> audioList = new ArrayList<>();
    ArrayList<String> smsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.READ_SMS,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                },1);
        listView = (ListView)findViewById(R.id.listview);

    }

    public void audioFunc(View view){
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,null,null,null
        );
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String name = cursor.getString(
                        cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)
                );
                String album = cursor.getString(
                        cursor.getColumnIndex(MediaStore.Audio.AlbumColumns.ALBUM)
                );
                String path = cursor.getString(
                        cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)
                );
                audioList.add(album);
            }while(cursor.moveToNext());
            cursor.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                    audioList);
            listView.setAdapter(adapter);
        }
    }
    public void smsFunc(View view){
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://sms/inbox"),
                null,null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                String id = cursor.getString(
                        cursor.getColumnIndex("_id")
                );
                String address = cursor.getString(
                        cursor.getColumnIndex("address")
                );
                String body = cursor.getString(cursor.getColumnIndex("body"));

                smsList.add(id+" : "+address+" : "+body);
            }while (cursor.moveToNext());
            cursor.close();

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                    smsList);
            listView.setAdapter(adapter);
        }
    }
}