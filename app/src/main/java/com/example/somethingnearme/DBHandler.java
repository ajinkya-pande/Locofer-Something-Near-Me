package com.example.somethingnearme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    // Table names
    static final String databaseName = "SnmAppDatabase";
    static final String userTable = "userdata";
    static final String contentTable = "contentdata";
    static final String orderTable = "orderdata";

    // Default Constructor
    public DBHandler(Context context) {
        super(context, databaseName, null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // User table creation.
        String userQuery = "CREATE TABLE " + userTable +" ( "
                + "email TEXT PRIMARY KEY,"
                + "password TEXT)";
        db.execSQL(userQuery);

        // Content table creation.
        String contentQuery = "CREATE TABLE " + contentTable +" ( "
                + "name TEXT PRIMARY KEY,"
                + "location TEXT,"
                + "tag TEXT,"
                + "price DOUBLE,"
                + "rating DOUBLE)";
        db.execSQL(contentQuery);

        // Order Table
        String orderQuery = "CREATE TABLE " + orderTable +" ( "
                + "email TEXT NOT NULL,"
                + "name TEXT NOT NULL,"
                + "PRIMARY KEY (email,name) )";
        db.execSQL(orderQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
//        db.execSQL("DROP TABLE IF EXISTS " + userTable);
        /*  onCreate(db);*/
        String orderQuery = "CREATE TABLE " + orderTable +" ( "
                + "email TEXT NOT NULL,"
                + "name TEXT NOT NULL,"
                + "PRIMARY KEY (email,name) )";
        db.execSQL(orderQuery);
    }

// User Functions

    // To add new user to the database
    public void addNewUser(String email, String password) {
        // creating a variable for our sqlite database
        SQLiteDatabase db = this.getWritableDatabase();
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        values.put("email", email);
        values.put("password", password);
        // passing content values to our table.
        db.insert(userTable, null, values);

        // at last we are closing our database
        db.close();
    }

    // we have created a new method for reading all the courses.
    public ArrayList<UserModel> readUserData() {
        // creating a database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // creating a cursor with query to read data from database.
        Cursor cursor = db.rawQuery("SELECT * FROM  " + userTable, null);

        // creating a new array list.
        ArrayList<UserModel> tableModalArrayList = new ArrayList<>();
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // adding the data from cursor to our array list.
                tableModalArrayList.add(
                        new UserModel(
                                cursor.getString(0),
                                cursor.getString(1)));
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        return tableModalArrayList;
    }

    public boolean isLoginValid(String email, String password) {
        // Get user data
        ArrayList<UserModel> list = readUserData();

        // Check for data of each user if it matches required condition
        for(UserModel user : list) {
            if(user.getEmail().equals(email) && user.getPassword().equals(password))
                    return true;
        }
        // if no such user exists
        return false;
    }

// Content Functions

    // To add new content to the store
    public void addNewContent(String name, String location , String tag, double price, double rating) {
        // creating a variable for our sqlite database
        SQLiteDatabase db = this.getWritableDatabase();
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        values.put("name", name);
        values.put("location", location);
        values.put("tag", tag);
        values.put("price", price);
        values.put("rating", rating);
        // passing content values to our table.
        db.insert(contentTable, null, values);

        // at last we are closing our database
        db.close();
    }


    public void deleteContent(String name) {
        // creating a database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // Delete Entry
        db.delete(contentTable, "name = ?", new String[]{name});
    }


    // Read content table
    public ArrayList<ContentModel> readContentData() {
        // creating a database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();
        // creating a cursor with query to read data from database.
        Cursor cursor = db.rawQuery("SELECT * FROM  " + contentTable, null);

        ArrayList<ContentModel> tableModalArrayList = new ArrayList<>();
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // adding the data from cursor to our array list.
                tableModalArrayList.add(
                        new ContentModel(
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getDouble(3),
                                cursor.getDouble(4)));
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        return tableModalArrayList;
    }

// Order Functions

    // Add new Order
    public void addNewOrder(String userEmail, String contentName) {
        // creating a variable for our sqlite database
        SQLiteDatabase db = this.getWritableDatabase();
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        values.put("email", userEmail);
        values.put("name", contentName);
        // passing content values to our table.
        db.insert(orderTable, null, values);

        // at last we are closing our database
        db.close();
    }

    // Get order data
    public OrderModel getCustomerOrderDetails(String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        // creating a cursor with query to read data from database.
        Cursor cursor = db.rawQuery("SELECT * FROM  " + orderTable + " WHERE email = ?", new String[]{userEmail});

        OrderModel order = new OrderModel( cursor.getString(0));
        // moving our cursor to first position.
        if (cursor.moveToFirst()) {
            do {
                // adding the data from cursor to object.
                order.addItemToList(cursor.getString(1));
            } while (cursor.moveToNext());
            // moving our cursor to next.
        }
        return order;
    }


// Class Declarations

    // User Model Class to store User data
    public static class UserModel implements Serializable {
        // User variables
        String email, password;

        // Default Constructor
        public UserModel(String email, String password) {
            this.email = email;
            this.password = password;
        }

        // Getter - Setter
        public String getPassword() { return password; }

        public void setPassword(String password) { this.password = password; }

        public String getEmail() { return email; }

        public void setEmail(String email) { this.email = email; }
    }


    // Content Model Class
    public static class ContentModel implements Serializable {
        // Content variables
        String name, location, tag;
        double price, rating;
        byte[] imageArray;

        // Default Constuctor
        public ContentModel(String name, String location, String tag, double price, double rating) {
            this.name = name;
            this.location = location;
            this.tag = tag;
            this.price = price;
            this.rating = rating;
            imageArray = null;
        }

        // Getter - Setter
        public String getName() {  return name; }

        public void setName(String name) { this.name = name;  }

        public String getLocation() { return location; }

        public void setLocation(String location) { this.location = location; }

        public String getTag() { return tag; }

        public void setTag(String tag) { this.tag = tag; }

        public double getPrice() { return price; }

        public void setPrice(double price) { this.price = price; }

        public double getRating() { return rating; }

        public void setRating(double rating) { this.rating = rating; }

        public Bitmap getImage() {
            return BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length);
        }

        public void setImage(Bitmap image) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 100, stream);
            this.imageArray = stream.toByteArray();
        }
    }

    // Order Model Class
    public static class OrderModel implements Serializable {
        String email;
        ArrayList<String> orderList;

        // Default Constructor
        public OrderModel(String email) {
            this.email = email;
            orderList = new ArrayList<>();
        }

        // Getter - Setter
        public String getEmail() { return email; }

        public void setEmail(String email) { this.email = email; }

        public void addItemToList(String item) { orderList.add(item); }

        public ArrayList<String> getOrderList() { return orderList; }
    }
}

