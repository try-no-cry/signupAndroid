package com.example.signup_sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class database extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SignUp.db";

    public static final String TABLE_NAME = "signup";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_TIMESTAMP = "timestamp";



    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + " TEXT,"
            + COLUMN_EMAIL + " TEXT," + COLUMN_PASSWORD + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    public database(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        //Drop com.example.signup_sql.User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);
    }

    public  void addUser(User user){
        //converting it to writable form
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(COLUMN_NAME,user.getName());
        values.put(COLUMN_EMAIL,user.getEmail());
        values.put(COLUMN_PASSWORD,user.getPassword());

        db.insert(TABLE_NAME,null,values);
        db.close();
    }


    //to fetch all the data

    public List<User> getAllUser(){
        //array of columns to fetch

        String[] columns={
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_PASSWORD
        };

        //sorting order
        String sortOrder =
                COLUMN_NAME + " ASC";

        List<User> userList=new ArrayList<User>();

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(TABLE_NAME,columns,null,
                null,null,null,sortOrder);

        //traversing all the rows


        if(cursor.moveToFirst()){
            do{
                User user=new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)));
            userList.add(user);
            }while(cursor.moveToNext());

        }

        cursor.close();
        db.close();

        return userList;
    }


    public void updateUser(User user){

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_EMAIL, user.getEmail());
        values.put(COLUMN_PASSWORD, user.getPassword());

        db.update(TABLE_NAME,values,COLUMN_ID + "= ?",new String[]{String.valueOf(user.getId())});
        db.close();


    }

    //delete a record/user

    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME,COLUMN_ID + "= ?",new String[]{String.valueOf(user.getId())});
        db.close();
    }

    //check for no duplication
    public boolean checkUser(String email){

        String[] columns={
                COLUMN_ID
        };

        SQLiteDatabase db=this.getReadableDatabase();
        // selection criteria

        String selection = COLUMN_EMAIL + " = ?" ;

        // selection arguments
        String[] selectionArgs = {email};

        Cursor cursor=db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

        if(cursorCount>0){
            return true;
        }
        else return false;
    }

    public boolean checkUser(String email, String password){

        String[] columns={
                COLUMN_ID
        };

        SQLiteDatabase db=this.getReadableDatabase();
        // selection criteria

        String selection = COLUMN_EMAIL + " = ?" +" AND "+ COLUMN_PASSWORD+ "= ? ";

        // selection arguments
        String[] selectionArgs = {email, password};

        Cursor cursor=db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

        if(cursorCount>0){
            return true;
        }
        else return false;
    }



}
