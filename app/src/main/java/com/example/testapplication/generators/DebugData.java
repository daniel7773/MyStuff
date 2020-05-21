package com.example.testapplication.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;

import com.example.testapplication.models.Review;
import com.example.testapplication.models.Hotel;

public final class DebugData {

    private static final double NO_REVIEWS_CHANCE = 0.5d;

    @SuppressWarnings("SpellCheckingInspection")
    private static final List<String> REVIEW_TEXTS = Collections.unmodifiableList(Arrays.asList(
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "Praesent dapibus risus nec urna gravida egestas.",
        "Phasellus posuere mi mollis leo finibus, finibus aliquam nisl pharetra.",
        "Praesent porttitor sapien a molestie imperdiet.",
        "Sed facilisis nunc ac ante consectetur lacinia.",
        "Morbi mattis dui quis vestibulum imperdiet.",
        "Etiam ac est ut odio vestibulum lobortis.",
        "Aliquam quis lacus sodales, tempor est a, aliquet libero.",
        "Praesent eget dui fringilla, porttitor risus nec, semper justo.",
        "Phasellus mollis purus elementum velit posuere imperdiet.",
        "Etiam luctus urna ut leo eleifend dapibus.",
        "Nunc at metus a ipsum interdum vulputate ac non tellus.",
        "Morbi eget tellus at sapien imperdiet laoreet.",
        "Pellentesque feugiat metus sit amet sem porta consequat.",
        "Vestibulum ut diam blandit justo dictum bibendum vel non sem.",
        "Vivamus vehicula dui vel enim faucibus eleifend.",
        "Pellentesque ut orci in nisl laoreet pellentesque.",
        "Vestibulum in orci interdum, commodo sapien et, eleifend nunc.",
        "Donec vel metus id risus porta fringilla.",
        "Etiam a nibh ullamcorper, dignissim ante id, iaculis odio.",
        "Duis venenatis dolor vitae porttitor congue.",
        "Morbi eget orci consectetur, varius nisi in, bibendum erat.",
        "Praesent eget urna sit amet ligula pulvinar gravida.",
        "Vivamus a nunc a neque tempor porttitor.",
        "Ut non enim sit amet nunc iaculis convallis.",
        "In volutpat nisl non ligula vestibulum, sed mattis diam molestie.",
        "Aliquam volutpat eros egestas, venenatis nibh at, ornare enim.",
        "Aenean semper tortor non nisl condimentum, a pretium nunc scelerisque.",
        "Integer consectetur lectus eget auctor accumsan.",
        "Integer sed massa aliquet, porta velit nec, blandit mi.",
        "Mauris non magna rhoncus, mollis purus non, semper odio."
    ));

    @SuppressWarnings("SpellCheckingInspection")
    private static final List<String> HOTEL_NAMES = Collections.unmodifiableList(Arrays.asList(
        "Rowntree Crescent",
        "Brunthill Road",
        "Court Approach",
        "Gaskell Rise",
        "Marykirk Road",
        "Chase Meadows",
        "Dryden's Close",
        "Philpott Avenue",
        "Hawkhope Hill",
        "Airds Court",
        "Lane Ends Close",
        "St Normans Way",
        "Park Link",
        "Brent Drive",
        "Pencepool Orchard",
        "Newchurch Old Road",
        "Irwell",
        "Field Row",
        "Shuart Lane",
        "Almond Green"
    ));

    private DebugData() {}

    @NonNull
    public static List<Hotel> getDebugHotels(final int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count < 0: " + count);
        }
        final IdGenerator idGenerator = new IdGenerator();
        final List<Hotel> hotels = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            final List<Review> reviews;
            if (Double.compare(Math.random(), NO_REVIEWS_CHANCE) >= 0) {
                reviews = null;
            } else {
                final int reviewsCount = (int) (Math.random() * 8 + 3);
                reviews = getDebugReviews(idGenerator, reviewsCount);
            }
            final Hotel hotel = new Hotel(idGenerator.generate(), getHotelName(), getHotelPrice(), reviews);
            hotels.add(hotel);
        }
        return hotels;
    }

    @NonNull
    private static List<Review> getDebugReviews(@NonNull final IdGenerator idGenerator, final int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count < 0: " + count);
        }
        final List<Review> reviews = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            final Review review = new Review(idGenerator.generate(), getReviewMark(), getReviewText());
            reviews.add(review);
        }
        return reviews;
    }

    @NonNull
    private static String getReviewMark() {
        final double mark = Math.random() * 10d + 1d;
        return String.format(Locale.getDefault(), "%.1f", mark);
    }

    @NonNull
    private static String getReviewText() {
        final int index = (int) (Math.random() * REVIEW_TEXTS.size());
        return REVIEW_TEXTS.get(index);
    }

    @NonNull
    private static String getHotelName() {
        final int index = (int) (Math.random() * HOTEL_NAMES.size());
        return HOTEL_NAMES.get(index);
    }

    @NonNull
    private static String getHotelPrice() {
        final double mark = Math.random() * 900d + 100d;
        return String.format(Locale.getDefault(), "$%.2f", mark);
    }

}