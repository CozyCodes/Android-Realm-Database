package cozycodes.project.realmsample;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Cozycodes on 5/22/2017.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration config=new RealmConfiguration.Builder(this).schemaVersion(4).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(config);
    }
}
