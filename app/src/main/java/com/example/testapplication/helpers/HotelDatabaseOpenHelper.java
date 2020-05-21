package com.example.testapplication.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.testapplication.utils.Constants;
import com.example.testapplication.generators.DebugData;
import com.example.testapplication.models.Hotel;

import java.util.ArrayList;
import java.util.List;

public final class HotelDatabaseOpenHelper extends SQLiteOpenHelper {

    public HotelDatabaseOpenHelper(@Nullable final Context context) {
        super(context, "hotels.sqlite", null, 1);
    }

    @Override
    public void onCreate(@NonNull final SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL(
                    "CREATE TABLE " + Hotel.Table.NAME + " ("
                            + Hotel.Table.Column.ID + " TEXT, "
                            + Hotel.Table.Column.NAME + " TEXT, "
                            + Hotel.Table.Column.PRICE + " TEXT, "
                            + Hotel.Table.Column.REVIEWS + " TEXT, "
                            + "PRIMARY KEY(" + Hotel.Table.Column.ID + ")"
                            + ")"
            );
            final List<Hotel> hotels = DebugData.getDebugHotels(50);
            for (int i = 0; i < hotels.size(); i++) {
                final Hotel hotel = hotels.get(i);
                final ContentValues cv = hotel.toContentValues();
                db.insert(Hotel.Table.NAME, null, cv);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(@NonNull final SQLiteDatabase db, final int oldVersion, final int newVersion) {
        // Nothing to do here
    }

    @NonNull
    public List<Hotel> loadHotelsPage(final int pageIndex) {

        List<Hotel> hotels = new ArrayList<>();
        Cursor getterCursor;

        getterCursor = this.getReadableDatabase().query(Hotel.Table.NAME, null, null, null, null, null, null);

        if (getterCursor != null) {
            if (getterCursor.moveToFirst()) {
                String str;
                do {
                    int idIndex = 0;
                    int nameIndex = 0;
                    int priceIndex = 0;
                    int reviewsIndex;
                    str = "";
                    for (String cn : getterCursor.getColumnNames()) {
                        str = str.concat(cn + " = "
                                + getterCursor.getString(getterCursor.getColumnIndex(cn)) + "; ");
                        switch (cn) {
                            case Constants.ID: {
                                idIndex = getterCursor.getColumnIndex(cn);
                                break;
                            }
                            case Constants.NAME: {
                                nameIndex = getterCursor.getColumnIndex(cn);
                                break;
                            }
                            case Constants.PRICE: {
                                priceIndex = getterCursor.getColumnIndex(cn);
                                break;
                            }
                            case Constants.REVIEWS: {
                                //reviewsIndex = getterCursor.getColumnIndex(cn);
                                break;
                            }
                        }
                        hotels.add(new Hotel(getterCursor.getString(idIndex),
                                getterCursor.getString(nameIndex),
                                getterCursor.getString(priceIndex),
                                null));
                    }
                    Log.d("HotelDatabase", str);

                } while (getterCursor.moveToNext());
            }
            getterCursor.close();
        }

        return hotels;
    }
}