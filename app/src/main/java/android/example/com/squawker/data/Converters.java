package android.example.com.squawker.data;

import android.arch.persistence.room.TypeConverter;

import org.threeten.bp.Instant;

public class Converters {
    @TypeConverter
    public static Long instantToTimestamp(Instant instant) {
        return instant == null ? null : instant.toEpochMilli();
    }

    @TypeConverter
    public static Instant timestampToInstant(Long timestamp) {
        return timestamp == null ? null : Instant.ofEpochMilli(timestamp);
    }
}
