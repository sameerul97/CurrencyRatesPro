package com.studio.sameer.myapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
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

//import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class lastFragment extends Fragment {

    double rate;
    private RequestQueue queue; //Variable Used by Volley Lib

    private TextView textView;
    private EditText editText;
    private SearchableSpinner searchableSpinner1;
    private SearchableSpinner searchableSpinner2;
    private String tempString;
    private String tempString2;
    private ProgressBar progressBar;
    private String currency1ChoseByUser;
    private String currency2ChoseByUser;

    public lastFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_last, container, false);

        textView = (TextView) view.findViewById(R.id.textView);
        editText = (EditText) view.findViewById(R.id.editText);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        searchableSpinner1 = (SearchableSpinner)view.findViewById(R.id.searchableSpinner1);
        searchableSpinner2 = (SearchableSpinner)view.findViewById(R.id.searchableSpinner2);
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
                        progressBar.setVisibility(View.VISIBLE);
                        tempString = searchableSpinner1.getSelectedItem().toString();
                        String[] parts1 = tempString.split(":");
                        currency1ChoseByUser = parts1[0];
                        tempString2 = searchableSpinner2.getSelectedItem().toString();
                        String[] parts2 = tempString2.split(":");
                        currency2ChoseByUser = parts2[0];
                        System.out.println("sss"+everythingsGood(currency1ChoseByUser,currency2ChoseByUser));
                        double ans = everythingsGood(currency1ChoseByUser, currency2ChoseByUser);
                        //                  double d = Double.parseDouble(editText.getText().toString());
                        System.out.println(ans);
                }
                else {}
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

    /*Once every data is collected call this function */
    public double everythingsGood(String part1, final String part2) {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        // Request a string response from the provided URL.
        String url = "http://api.fixer.io/latest?base=" + part1;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        System.out.println(response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject currentrateinfo = jsonObject.getJSONObject("rates");
                            currentrateinfo.getString(part2);
                            System.out.println(currentrateinfo.getString(part2));

                            double rate = currentrateinfo.getDouble(part2);

                            System.out.println(rate);

                            double d;
                            if(editText.getText().length()<1){d = 1;}
                            else{d = Double.parseDouble(editText.getText().toString());}
                            double finalanswer = d * rate;
                            finalanswer = Math.round( finalanswer * 100.0 ) / 100.0;
                            String finalanswer2 = String.valueOf(finalanswer);
                            System.out.println(finalanswer2);
                            progressBar.setVisibility(View.INVISIBLE);

//                            textView.setText(tempString+":"+editText.getText().toString()+"\n is equal to "+"\n"+tempString2+":"+ finalanswer2);
                            textView.setText(currency1ChoseByUser+":"+editText.getText().toString()+"\n"+currency2ChoseByUser+":"+ finalanswer2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                textView.setText("That didn't work! try again later");
            }
        });
        queue.add(stringRequest);
        return rate;
    }


}