package cozycodes.project.realmsample;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import io.realm.Realm;
import io.realm.RealmChangeListener;

/**
 * Created by Cozycodes on 5/22/2017.
 */

public class MainActivity extends AppCompatActivity {

    Realm realm;
    RealmChangeListener realmChangeListener;
    SampleAdapter adapter;
    RecyclerView sample_recyclerview;
    EditText Et_name,Et_address,Et_age,Et_url;
    String name, address, age, image_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sample_recyclerview= (RecyclerView) findViewById(R.id.sample_recycler);
        sample_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        this.realm = RealmConnect.with(this).getRealm();

        final RealmConnect connect = new RealmConnect(realm);
        connect.selectDB();

        adapter=new SampleAdapter(this,connect.dataReload());
        sample_recyclerview.setAdapter(adapter);

        realmChangeListener=new RealmChangeListener() {
            @Override
            public void onChange() {

                adapter=new SampleAdapter(MainActivity.this,connect.dataReload());
                sample_recyclerview.setAdapter(adapter);
            }
        };

        realm.addChangeListener(realmChangeListener);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewUser();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
      // data changes are informed on the view
        realm.removeChangeListener(realmChangeListener);
        realm.close();
    }

    //Function to add new user
    private void addNewUser()
    {
        Dialog alert = new Dialog(this);
        alert.setTitle("New User");
        alert.setContentView(R.layout.input_dialog);

        Et_name = (EditText) alert.findViewById(R.id.Et_name);
        Et_address = (EditText) alert.findViewById(R.id.Et_address);
        Et_age = (EditText) alert.findViewById(R.id.Et_age);
        Et_url = (EditText) alert.findViewById(R.id.Et_url);
        Button addBtn = (Button) alert.findViewById(R.id.addBtn);


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 name = Et_name.getText().toString();
                 address = Et_address.getText().toString();
                 age = Et_age.getText().toString();
                 image_url = Et_url.getText().toString();

                if(name != null && name.length()>0)
                {

                    Sample sample = new Sample();
                    sample.setName(name);
                    sample.setAddress(address);
                    sample.setAge(age);
                    sample.setImage(image_url);

                    RealmConnect connect = new RealmConnect(realm);

                    if(connect.add(sample))
                    {
                        Et_name.setText("");
                        Et_address.setText("");
                        Et_age.setText("");
                        Et_url.setText("");

                    }else {
                        Toast.makeText(MainActivity.this, "Invalid", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(MainActivity.this, "Field Cannot be Empty", Toast.LENGTH_SHORT).show();
                }

            }
        });


        alert.show();

    }
}


