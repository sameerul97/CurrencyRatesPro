package com.studio.sameer.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class currencyconvertFragment extends Fragment {
    private RequestQueue queue;
    private LayoutInflater inflater;
    private View v;
    private ProgressBar progressBar;
    private TextView mTextView;
    private NumberPicker picker;
    private NumberPicker picker2;
    private String allcurrencies;

    public currencyconvertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_currencyconvert, container, false);
        loadActivity(v);



        return v;
    }
    public void updatePickerValues(String[] newValues,View v,Integer num){
        NumberPicker picker;
        if (num == 1){
            picker = (NumberPicker) v.findViewById(R.id.numberPicker1);}
        else {
            picker = (NumberPicker) v.findViewById(R.id.numberPicker2);
        }
        picker.setDisplayedValues(null);
        picker.setMinValue(0);
        picker.setMaxValue(newValues.length -1);
        picker.setWrapSelectorWheel(false);
        picker.setDisplayedValues(newValues);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

    }

    private void loadActivity(final View v){
        progressBar=(ProgressBar)v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        mTextView = (TextView) v.findViewById(R.id.text);
        picker= (NumberPicker) v.findViewById(R.id.numberPicker1);
        picker2= (NumberPicker) v.findViewById(R.id.numberPicker2);
        Context context = this.getContext();
        final  globalArrayList globalArrayList = new globalArrayList(context);
        ArrayList<String> arrayList = globalArrayList.getAllItem();
//        System.out.println(arrayList.toString());
        final String allcurrencies[] = arrayList.toArray(new String[arrayList.size()]);
        updatePickerValues(allcurrencies,v,1);
        updatePickerValues(allcurrencies,v,2);
        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                getString();
            }
        });
        picker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i2) {
                getString();
            }
        });
    }

    public void getString (){
        progressBar.setVisibility(View.GONE);
        mTextView.setVisibility(View.GONE);


        int pos1 = picker.getValue(); // getvalue() gets the index position for the picker
        int pos2 = picker2.getValue();
        Context context = this.getContext();
        final  globalArrayList globalArrayList = new globalArrayList(context);
        ArrayList<String> arrayList = globalArrayList.getAllItem();
        System.out.println(arrayList.toString());
        final String allcurrencies[] = arrayList.toArray(new String[arrayList.size()]);
        String tempstring = allcurrencies[pos1];
        String tempstring2 = allcurrencies[pos2];
        String[] parts = tempstring.split(":"); // Splits the string on semi colon into half we need the first half for the URL passing
        String part1 = parts[0];
        String[] parts2 = tempstring2.split(":");
        String part2 = parts2[0];
        if (part1.equals(part2)){
            mTextView.setVisibility(View.VISIBLE);
            System.out.println("Kello its the same currency\n");
            mTextView.setTextSize(15);
            mTextView.setText("Please select different currency!" );
        }
        else {
            mTextView.setTextSize(25);
            progressBar.setVisibility(View.VISIBLE);
            everythingsGood(part1,part2);
        }
    }

    public void everythingsGood(final String part1, final String part2) {
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        // Request a string response from the provided URL.
        String url = "http://api.fixer.io/latest?base=" + part1;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject currentrateinfo = jsonObject.getJSONObject("rates");
                            System.out.println(currentrateinfo);
                            System.out.println(currentrateinfo.getString(part2));
                            progressBar.setVisibility(View.INVISIBLE);
                            mTextView.setVisibility(View.VISIBLE);
                            mTextView.setText("Current Rate :" + currentrateinfo.getString(part2));
                            loadActivity(v);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.INVISIBLE);
                mTextView.setText("That didn't work! try again later");
            }
        });
        queue.add(stringRequest);
    }
}
