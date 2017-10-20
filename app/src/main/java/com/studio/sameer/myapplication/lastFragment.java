package com.studio.sameer.myapplication;


import android.media.Image;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class lastFragment extends Fragment {

    public lastFragment() {
        // Required empty public constructor
    }
    private RequestQueue queue; //Variable Used by Volley Lib

    private TextView textView;
    private EditText editText;
    private SearchableSpinner searchableSpinner1;
    private SearchableSpinner searchableSpinner2;
    private String tempString;
    private String tempString2;
    private String currency1ChoseByUser;
    private String currency2ChoseByUser;
    double rate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_last, container, false);

        textView = (TextView) view.findViewById(R.id.textView);
        editText = (EditText) view.findViewById(R.id.editText);
        searchableSpinner1 = (SearchableSpinner)view.findViewById(R.id.searchableSpinner1);
        searchableSpinner2 = (SearchableSpinner)view.findViewById(R.id.searchableSpinner2);
//        editText.getText().toString();
        editText.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editText.getText().length()<1) {
                    String one = String.valueOf(1);

                }
                if(searchableSpinner1.getSelectedItem().toString().equals(searchableSpinner2.getSelectedItem().toString())){
                    textView.setText("Select different currency");
                }
                else {
                    if(editText.getText().length()>=1){
                        tempString = searchableSpinner1.getSelectedItem().toString();
                        String[] parts1 = tempString.split(":");
                        currency1ChoseByUser = parts1[0];
                        tempString2 = searchableSpinner2.getSelectedItem().toString();
                        String[] parts2 = tempString2.split(":");
                        currency2ChoseByUser = parts2[0];
                        System.out.println("sss"+everythingsGood(currency1ChoseByUser,currency2ChoseByUser));
                        double ans = everythingsGood(currency1ChoseByUser, currency2ChoseByUser);
    //                double d = Double.parseDouble(editText.getText().toString());
                        System.out.println(ans);
                }
                else {}
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
//        SearchableSpinner mySpinner = (SearchableSpinner)view.findViewById(R.id.spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                R.layout.row, R.id.weekofday, DayOfWeek);
//        mySpinner.setAdapter(adapter);
        return view;
    }
    public double everythingsGood(String part1,String part2){
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22"+part1+part2+"%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject currentrateinfo = jsonObject.getJSONObject("query");
                            JSONObject json_results = currentrateinfo.getJSONObject("results");
//                            System.out.println(json_results);
//                            progressBar.setVisibility(View.INVISIBLE);
//                                    mTextView.setText("Response is: "+ json_results);

                            JSONObject obj = json_results.getJSONObject("rate");
//                            System.out.println("\nLength of Array is " + jsonArray.length());
//                            System.out.println(json_results);
//                                    int rows = jsonArray.length();
//                                    int colums  = 3;

//                                System.out.println(obj);
                            String name = obj.getString("Name");
                            String url = obj.getString("Rate");
                            double rate = Double.valueOf(url);
                            String date = obj.getString("Date");
                            String time = obj.getString("Time");
//                            System.out.println(name);
//                            System.out.println(url);
                            System.out.println(rate);
//                            System.out.println(time);
//                            mTextView.setText("Current Rate :"+url);
                            double d;
                            if(editText.getText().length()<1){d = 1;}
                            else{d = Double.parseDouble(editText.getText().toString());}
                            double finalanswer = d * rate;
                            finalanswer = Math.round( finalanswer * 100.0 ) / 100.0;
                            String finalanswer2 = String.valueOf(finalanswer);
                            System.out.println(finalanswer2);

//                            textView.setText(tempString+":"+editText.getText().toString()+"\n is equal to "+"\n"+tempString2+":"+ finalanswer2);
                            textView.setText(currency1ChoseByUser+":"+editText.getText().toString()+"\n"+currency2ChoseByUser+":"+ finalanswer2);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                progressBar.setVisibility(View.INVISIBLE);
//                mTextView.setText("That didn't work! try again later");
            }
        });
        queue.add(stringRequest);
         return ((double)rate);
    }


}



//    final SearchableSpinner searchableSpinner = (SearchableSpinner) view.findViewById(R.id.searchableSpinner);
//
//        searchableSpinner.setTitle("Select Country");
//                searchableSpinner.setPositiveButton("OK");
//
//                searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//                {
//public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
//        {
//        String selectedItem = parent.getItemAtPosition(position).toString();
//        System.out.println(selectedItem);
//        } // to close the onItemSelected
//public void onNothingSelected(AdapterView<?> parent)
//        {
//
//        }
//        });