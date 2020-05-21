package com.example.testapplication.models;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;

public final class Review {

    @NonNull
    private final String id;
    @NonNull
    private final String mark;
    @NonNull
    private final String text;

    public Review(@NonNull final String id,
                  @NonNull final String mark,
                  @NonNull final String text) {
        this.id = id;
        this.mark = mark;
        this.text = text;
    }

    public Review(@NonNull final JSONObject json) {
        this.id = json.optString("id");
        this.mark = json.optString("mark");
        this.text = json.optString("text");
    }

    @NonNull
    public JSONObject toJson() throws JSONException {
        final JSONObject json = new JSONObject();
        json.put("id", this.id);
        json.put("mark", this.mark);
        json.put("text", this.text);
        return json;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getMark() {
        return mark;
    }

    @NonNull
    public String getText() {
        return text;
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

        final Review review = (Review) o;

        if (!id.equals(review.id)) {
            return false;
        }
        if (!mark.equals(review.mark)) {
            return false;
        }
        if (!text.equals(review.text)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + mark.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }

    @Override
    @NonNull
    public String toString() {
        return "Review{"
            + "id='" + id + '\''
            + ", mark='" + mark + '\''
            + ", text='" + text + '\''
            + '}';
    }

}