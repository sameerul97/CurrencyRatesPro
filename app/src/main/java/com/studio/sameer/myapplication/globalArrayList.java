package com.studio.sameer.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Sameer on 15/09/2017.
 */

public class globalArrayList {

    static  ArrayList al = new ArrayList();
    Context context;
    public globalArrayList(Context gotContext){
        this.context = gotContext;
    }
    public void addItem(String arrayList){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        if(sharedPrefs.contains("secretKey")){
            System.out.println("This preferences already Exist in Phone");
            al = getAllItem();
            if(al.size()>10){
                if (al.contains(arrayList)) {
//                System.out.println("Not Inserting already exist");
                }
                else {
//                System.out.println("Deleting Now");
                    deleteItem();
                }
            }
            if (al.contains(arrayList)) {
//            System.out.println("Already Inserted");
            }
            else {
                al.add(0, arrayList);
                savingDataToPhone();
            }
        }
        else{ // Create a new SharedPreferences. This should happen only once ---- on first launch
            SharedPreferences.Editor editor = sharedPrefs.edit();
            Gson gson = new Gson();
            al.add(0, "AUD: Australian dollar ");
            al.add(1, "BGN: Bulgarian lev");
            String json = gson.toJson(al);

            editor.putString("secretKey", json);
            editor.commit();
            System.out.println("Firsttime Saving Data to phone");
        }






//        retreivingDataFromPhone();
//        System.out.println("Sizeeeee"+al.size());
    }
    public void savingDataToPhone() {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(al);

        editor.putString("secretKey", json);
        editor.commit();
        System.out.println("saved Data to phone ");
    }

//    public void retreivingDataFromPhone(){}
    public void deleteItem(){
        al.remove(9);
    }
    public ArrayList<String> getAllItem(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("secretKey", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> arrayList = gson.fromJson(json, type);
        System.out.println("Retreiving DAta from phone "+arrayList);
        return arrayList;
    }
    public void showItem(){
        System.out.println(al);
    }

}
