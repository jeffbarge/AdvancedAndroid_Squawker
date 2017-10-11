package android.example.com.squawker.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;

@Dao
public interface SquawkDAO {
    @Query("SELECT * FROM squawks WHERE authorKey IN (:authorKeys)")
    Flowable<List<Squawk>> getSubscribedSquawks(List<String> authorKeys);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSquawk(Squawk squawk);
}
