package cozycodes.project.realmsample;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Cozycodes on 5/22/2017.
 */

public class Sample extends RealmObject{

    @PrimaryKey
    private String name;
    private String address;
    private String age;
    private String image;


    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAge(){
        return age;
    }

    public void setAge(String age){
        this.age = age;
    }

    public String getImage(){
        return image;
    }

    public void setImage(String image){
        this.image = image;
    }
}
