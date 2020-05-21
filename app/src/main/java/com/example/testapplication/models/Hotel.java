package com.example.testapplication.models;

import android.content.ContentValues;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public final class Hotel {

    @NonNull
    private final String id;
    @NonNull
    private final String name;
    @NonNull
    private final String price;
    @Nullable
    private final List<Review> reviews;

    public Hotel(@NonNull final String id,
                 @NonNull final String name,
                 @NonNull final String price,
                 @Nullable final List<Review> reviews) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.reviews = reviews;
    }

    @NonNull
    public ContentValues toContentValues() {
        final ContentValues cv = new ContentValues();
        cv.put(Table.Column.ID, this.id);
        cv.put(Table.Column.NAME, this.name);
        cv.put(Table.Column.PRICE, this.price);
        if (reviews != null) {
            final JSONArray reviewsJsonArray = new JSONArray();
            for (int i = 0; i < reviews.size(); i++) {
                final Review review = reviews.get(i);
                final JSONObject json;
                try {
                    json = review.toJson();
                } catch (final JSONException e) {
                    e.printStackTrace();
                    continue;
                }
                reviewsJsonArray.put(json);
            }
            cv.put(Table.Column.REVIEWS, reviewsJsonArray.toString());
        }
        return cv;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getPrice() {
        return price;
    }

    @Nullable
    public List<Review> getReviews() {
        return reviews;
    }

    @Override
    @SuppressWarnings({"SimplifiableIfStatement", "RedundantIfStatement"})
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Hotel hotel = (Hotel) o;

        if (!id.equals(hotel.id)) {
            return false;
        }
        if (!name.equals(hotel.name)) {
            return false;
        }
        if (!price.equals(hotel.price)) {
            return false;
        }
        if (reviews != null ? !reviews.equals(hotel.reviews) : hotel.reviews != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + (reviews != null ? reviews.hashCode() : 0);
        return result;
    }

    @Override
    @NonNull
    public String toString() {
        return "Hotel{"
            + "id='" + id + '\''
            + ", name='" + name + '\''
            + ", price='" + price + '\''
            + ", reviews=" + reviews
            + '}';
    }

    public static final class Table {

        public static final String NAME = "hotels";

        public static final class Column {

            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String PRICE = "price";
            public static final String REVIEWS = "reviews_json_array_str";

            private Column() {}

        }

        private Table() {}

    }

}