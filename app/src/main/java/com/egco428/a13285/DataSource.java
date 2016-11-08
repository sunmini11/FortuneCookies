package com.egco428.a13285;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell pc on 2/11/2559.
 */
public class DataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbhelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_IMGNAME, MySQLiteHelper.COLUMN_MESSAGE, MySQLiteHelper.COLUMN_TIMESTAMP};

    public DataSource(Context context){
        dbhelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLiteAbortException {
        database = dbhelper.getWritableDatabase();
    }

    public void close(){
        dbhelper.close();
    }

    public FortuneResults createMessage(String imgname,String message,String timestamp){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_IMGNAME, imgname);
        values.put(MySQLiteHelper.COLUMN_MESSAGE, message);
        values.put(MySQLiteHelper.COLUMN_TIMESTAMP, timestamp);
        open();
        long insertID = database.insert(MySQLiteHelper.TABLE_RESULTS, null, values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RESULTS, allColumns, MySQLiteHelper.COLUMN_ID + "=" + insertID, null, null, null, null);
        cursor.moveToFirst();
        FortuneResults newMessage = cursorToComment(cursor);
        cursor.close();
        return newMessage;
    }

    public void deleteFortuneResult(FortuneResults results){
        long id = results.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_RESULTS, MySQLiteHelper.COLUMN_ID + "=" + id,null);
    }

    public List<FortuneResults> getAllComments(){
        List<FortuneResults> results = new ArrayList<FortuneResults>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RESULTS, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            FortuneResults newresult = cursorToComment(cursor);
            results.add(newresult);
            cursor.moveToNext();
        }
        cursor.close();
        return results;
    }

    public FortuneResults cursorToComment(Cursor cursor){ //set value to comment
        FortuneResults results = new FortuneResults(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
        return results;
    }
}
