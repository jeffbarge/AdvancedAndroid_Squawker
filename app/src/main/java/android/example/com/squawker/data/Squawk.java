package android.example.com.squawker.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.Instant;

@Entity(tableName = "squawks")
public class Squawk {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "author")
    private String mAuthor;

    @ColumnInfo(name = "authorKey")
    private String mAuthorKey;

    @ColumnInfo(name = "message")
    private String mMessage;

    @ColumnInfo(name = "timestamp")
    private Instant mTimestamp;

    public Squawk(String author, String authorKey, String message, Instant timestamp) {
        mAuthor = author;
        mAuthorKey = authorKey;
        mMessage = message;
        mTimestamp = timestamp;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getAuthorKey() {
        return mAuthorKey;
    }

    public void setAuthorKey(String authorKey) {
        mAuthorKey = authorKey;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Instant getTimestamp() {
        return mTimestamp;
    }

    public void setTimestamp(Instant timestamp) {
        mTimestamp = timestamp;
    }
}
