package edu.ewubd.mycontacts;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "my_contacts.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CONTACTS = "contacts";

    // Column names
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_HOME_PHONE = "home_phone";
    public static final String COLUMN_OFFICE_PHONE = "office_phone";
    public static final String COLUMN_IMAGE_URI = "image_uri";

    private static final String CREATE_TABLE_CONTACTS =
            "CREATE TABLE " + TABLE_CONTACTS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_HOME_PHONE + " TEXT NOT NULL, " +
                    COLUMN_OFFICE_PHONE + " TEXT NOT NULL, " +
                    COLUMN_IMAGE_URI + " TEXT" +
                    ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    // Add a new contact to the database
    public long addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_EMAIL, contact.getEmail());
        values.put(COLUMN_HOME_PHONE, contact.getHomePhone());
        values.put(COLUMN_OFFICE_PHONE, contact.getOfficePhone());
        values.put(COLUMN_IMAGE_URI, contact.getImageUri());

        return db.insert(TABLE_CONTACTS, null, values);
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_HOME_PHONE,
                COLUMN_OFFICE_PHONE,
                COLUMN_IMAGE_URI
        };

        Cursor cursor = db.query(
                TABLE_CONTACTS,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(
                        cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_HOME_PHONE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_OFFICE_PHONE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI))
                );

                contactList.add(contact);
            } while (cursor.moveToNext());

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return contactList;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, contact.getName());
        values.put(COLUMN_EMAIL, contact.getEmail());
        values.put(COLUMN_HOME_PHONE, contact.getHomePhone());
        values.put(COLUMN_OFFICE_PHONE, contact.getOfficePhone());
        values.put(COLUMN_IMAGE_URI, contact.getImageUri());

        return db.update(
                TABLE_CONTACTS,
                values,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(contact.getId())}
        );
    }
    public void deleteContact(long contactId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_CONTACTS,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(contactId)}
        );
    }

    @SuppressLint("Range")
    public Contact getContactById(long contactId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_EMAIL,
                COLUMN_HOME_PHONE,
                COLUMN_OFFICE_PHONE,
                COLUMN_IMAGE_URI
        };

        Cursor cursor = db.query(
                TABLE_CONTACTS,
                projection,
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(contactId)},
                null,
                null,
                null
        );

        Contact contact = null;

        if (cursor != null && cursor.moveToFirst()) {
            contact = new Contact(
                    cursor.getLong(cursor.getColumnIndex(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_HOME_PHONE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_OFFICE_PHONE)),
                    cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_URI))
            );

            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return contact;
    }
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMN_ID};

        Cursor cursor = db.query(
                TABLE_CONTACTS,
                projection,
                COLUMN_EMAIL + " = ?",
                new String[]{email},
                null,
                null,
                null
        );

        boolean exists = cursor != null && cursor.getCount() > 0;

        if (cursor != null) {
            cursor.close();
        }

        return exists;
    }
}
