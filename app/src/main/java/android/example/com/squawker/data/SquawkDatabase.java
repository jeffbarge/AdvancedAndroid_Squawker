package android.example.com.squawker.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Squawk.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class SquawkDatabase extends RoomDatabase {
    private static volatile SquawkDatabase INSTANCE;

    public abstract SquawkDAO squawkDAO();

    public static SquawkDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SquawkDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SquawkDatabase.class, "squakw.db").build();
                }
            }
        }
        return INSTANCE;
    }
}
