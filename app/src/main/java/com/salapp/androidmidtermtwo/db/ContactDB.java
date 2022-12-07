package com.salapp.androidmidtermtwo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.salapp.androidmidtermtwo.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactDB extends SQLiteOpenHelper {
    private static final String DB_NAME = "contact.db";
    private static final int VERSION_DB = 1;
    private static final String TABLE_NAME = "CONTACT";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_NAME = "NAME";
    private static final String COLUMN_PHONE = "PHONE";
    private static final String COLUMN_EMAIL = "EMAIL";
    private static final String COLUMN_ADDRESS = "ADDRESS";

    public ContactDB(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String db_creation = "CREATE TABLE " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " VARCHAR,"
                + COLUMN_PHONE + " VARCHAR, "
                + COLUMN_EMAIL + " VARCHAR, "
                + COLUMN_ADDRESS + " VARCHAR )";

        db.execSQL(db_creation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_PHONE, contact.getPhoneNumber());
        values.put(COLUMN_EMAIL, contact.getEmail());
        values.put(COLUMN_ADDRESS, contact.getAddress());

        db.insert(TABLE_NAME, null, values);
    }

    public void deleteContact(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlDelete = "DELETE FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + "=" + id + ";";
        db.execSQL(sqlDelete);
    }

    public void updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        /*String updateSQL = "UPDATE " + TABLE_NAME + " SET " + COLUMN_NAME + "=" + "\"" + contact.getName() + "\""
                + ", " + COLUMN_PHONE + "=" + "\"" + contact.getPhoneNumber() + "\""
                + ", " + COLUMN_EMAIL + "=" + "\"" + contact.getEmail() + "\""
                + ", " + COLUMN_ADDRESS + "=" +  "\"" + contact.getAddress() + "\""
                + " WHERE " + COLUMN_ID + "=" +  contact.getId();*/

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, contact.getName());
        contentValues.put(COLUMN_PHONE, contact.getPhoneNumber());
        contentValues.put(COLUMN_EMAIL, contact.getEmail());
        contentValues.put(COLUMN_ADDRESS, contact.getAddress());

        //db.execSQL(updateSQL);
        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = " + contact.getId(), null);
    }

    public List<Contact> getAllContacts() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlFindUser = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sqlFindUser, null);
        List<Contact> contacts = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(cursor.getInt(0));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));
                contact.setEmail(cursor.getString(3));
                contact.setAddress(cursor.getString(4));

                contacts.add(contact);
            } while (cursor.moveToNext());
        }
        return contacts;
    }
}
