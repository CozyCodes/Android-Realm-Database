package cozycodes.project.realmsample;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmException;

/**
 * Created by Cozycodes on 5/22/2017.
 */

public class RealmConnect {

    Realm realm;
    RealmResults<Sample> sample;
    Boolean add=null;
    private static RealmConnect instance;

    public RealmConnect(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public RealmConnect(Realm realm) {
        this.realm = realm;
    }


    public Boolean add(final Sample sample)
    {
        if(sample == null)
        {
            add = false;
        }else
        {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    try
                    {
                        Sample val = realm.copyToRealm(sample);
                        add = true;

                    }catch (RealmException e)
                    {
                        e.printStackTrace();
                        add = false;
                    }
                }
            });
        }

        return add;
    }


    public void selectDB()
    {
        sample = realm.where(Sample.class).findAll();
    }


    public ArrayList<Sample> dataReload()
    {
        ArrayList<Sample> latest=new ArrayList<>();
        for(Sample s : sample)
        {
            latest.add(s);
        }

        return latest;
    }



    public static RealmConnect getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    public static RealmConnect with(Activity activity) {

        if (instance == null) {
            instance = new RealmConnect(activity.getApplication());
        }
        return instance;
    }


}












