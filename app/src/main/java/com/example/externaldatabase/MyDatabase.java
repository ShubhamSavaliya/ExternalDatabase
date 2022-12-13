package com.example.externaldatabase;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

//import com.example.externaldatabase.models.StdCode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {

    private static final String TAG = "MyDatabase";
    static int valueOfQuotes;

    private static String DB_NAME = "quotesdb.db";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 1;

    private SQLiteDatabase mDataBase;
    private boolean mNeedUpdate = false;

    private Context mContext;

    public MyDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        if (Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }


    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DB_NAME);
        return dbFile.exists();
    }


    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            mNeedUpdate = true;
    }


    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DB_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }


    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }


    public ArrayList<QuotesModalClass> readData() {
        ArrayList<QuotesModalClass> modalArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from quotestb";
        Cursor c = db.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {

                int id = c.getInt(0);
                String quotes = c.getString(1);

                QuotesModalClass modalClass = new QuotesModalClass(id, quotes);
                modalArrayList.add(modalClass);

                Log.e(TAG, "readData:==> " + id + "   " + quotes);
            } while (c.moveToNext());
        }
        return modalArrayList;

    }


    public ArrayList<ShayriModalClass> readShayriData() {


        ArrayList<ShayriModalClass> modalArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from ShayriTb where Category_id=" + valueOfQuotes;

        Cursor c = db.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {
                int id = c.getInt(0);
                String shayri = c.getString(1);
                int category = c.getInt(2);
                int status = c.getInt(3);

                Log.e(TAG, "readShayriData:== " + category);
                ShayriModalClass shayriModalClass = new ShayriModalClass(id, shayri, category, status);
                modalArrayList.add(shayriModalClass);


            } while (c.moveToNext());
        }
        return modalArrayList;
    }

    public ArrayList<ShayriModalClass> LikeShayriData() {


        ArrayList<ShayriModalClass> modalArrayList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "select * from ShayriTb where status=1";
        Cursor c = db.rawQuery(sql, null);

        if (c.moveToFirst()) {
            do {

                int id = c.getInt(0);
                String shayri = c.getString(1);
                int category = c.getInt(2);
                int status = c.getInt(3);
                Log.e(TAG, "readShayriData:== " + status);
                ShayriModalClass shayriModalClass = new ShayriModalClass(id, shayri, category, status);
                modalArrayList.add(shayriModalClass);

            } while (c.moveToNext());
        }
        return modalArrayList;
    }

    void editstatus(int like, int id) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues fill = new ContentValues();
        fill.put("status", like);
        db.update("ShayriTb", fill, "Shayri_id=?", new String[]{String.valueOf(id)});
    }

    public void deleteData(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("ShayriTb","Shayri_id=?",new String[]{String.valueOf(id)});
    }
}
