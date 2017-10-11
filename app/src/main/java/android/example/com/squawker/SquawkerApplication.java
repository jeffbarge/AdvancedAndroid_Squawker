package android.example.com.squawker;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.jakewharton.threetenabp.AndroidThreeTen;


public class SquawkerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);
        Stetho.initializeWithDefaults(this);
    }
}
