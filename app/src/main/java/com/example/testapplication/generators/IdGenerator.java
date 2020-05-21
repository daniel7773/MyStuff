package com.example.testapplication.generators;

import java.util.Arrays;
import java.util.Random;

import androidx.annotation.NonNull;

public final class IdGenerator {

    private static final int TIME_STAMP_CHARS_COUNT = 8;
    private static final int RAND_CHARS_COUNT = 12;
    private static final int ID_LENGTH = 20;

    private static final int RAND_CHARS_BOUND = 64;
    private static final long RAND_CHARS_BOUND_LONG = 64L;

    @SuppressWarnings("SpellCheckingInspection")
    private static final char[] PUSH_CHARS = "-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz".toCharArray();

    private State globalState = new State();

    @NonNull
    private Result generateNextId(@NonNull final State previousState, final long instant) {
        final char[] timeStampChars = new char[TIME_STAMP_CHARS_COUNT];
        long instantLeft = instant;
        for (int i = TIME_STAMP_CHARS_COUNT - 1; i >= 0; i--) {
            final long module = instantLeft % RAND_CHARS_BOUND_LONG;
            instantLeft /= RAND_CHARS_BOUND_LONG;
            timeStampChars[i] = PUSH_CHARS[(int) module];
        }
        if (instantLeft != 0L) {
            throw new AssertionError("We should have converted the entire timestamp.");
        }
        final int[] randChars;
        if (instant == previousState.lastInstant) {
            randChars = Arrays.copyOf(previousState.lastRandChars, previousState.lastRandChars.length);
            int lastNot63Index = 0;
            for (int i = randChars.length - 1; i >= 0; i--) {
                if (randChars[i] != RAND_CHARS_BOUND - 1) {
                    lastNot63Index = i;
                    break;
                }
            }
            Arrays.fill(randChars, lastNot63Index + 1, randChars.length, 0);
            randChars[lastNot63Index]++;
        } else {
            final Random random = new Random();
            randChars = new int[RAND_CHARS_COUNT];
            for (int i = 0; i < randChars.length; i++) {
                randChars[i] = random.nextInt(RAND_CHARS_BOUND);
            }
        }
        final StringBuilder randCharsAsStringBuilder = new StringBuilder(RAND_CHARS_COUNT);
        for (int i = 0; i < randChars.length; i++) {
            randCharsAsStringBuilder.append(PUSH_CHARS[randChars[i]]);
        }
        final String randCharsAsString = randCharsAsStringBuilder.toString();
        final String id = new String(timeStampChars) + randCharsAsString;
        if (id.length() != ID_LENGTH) {
            throw new AssertionError("Length should be " + ID_LENGTH + ".");
        }
        return new Result(id, new State(instant, randChars));
    }

    @NonNull
    private synchronized String generateId(final long instant) {
        final Result result = generateNextId(globalState, instant);
        globalState = result.nextState;
        return result.id;
    }

    @NonNull
    public String generate() {
        return generateId(System.currentTimeMillis());
    }

    private static final class State {

        private final long lastInstant;
        @NonNull
        private final int[] lastRandChars;

        State() {
            this.lastInstant = -1L;
            this.lastRandChars = new int[RAND_CHARS_COUNT];
        }

        State(final long lastInstant, @NonNull final int[] lastRandChars) {
            this.lastInstant = lastInstant;
            this.lastRandChars = lastRandChars;
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

            final State state = (State) o;

            if (lastInstant != state.lastInstant) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            return (int) (lastInstant ^ (lastInstant >>> 32));
        }

    }

    private static final class Result {

        @NonNull
        private final String id;
        @NonNull
        private final State nextState;

        Result(@NonNull final String id, @NonNull final State nextState) {
            this.id = id;
            this.nextState = nextState;
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

            final Result result = (Result) o;

            if (!id.equals(result.id)) {
                return false;
            }
            if (!nextState.equals(result.nextState)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = id.hashCode();
            result = 31 * result + nextState.hashCode();
            return result;
        }

    }

}