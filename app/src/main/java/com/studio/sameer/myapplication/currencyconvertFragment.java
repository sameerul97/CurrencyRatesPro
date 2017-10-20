package com.studio.sameer.myapplication;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static com.studio.sameer.myapplication.R.id.container;

/**
 * A simple {@link Fragment} subclass.
 */
public class currencyconvertFragment extends Fragment {
    public currencyconvertFragment() {
        // Required empty public constructor
    }
    private RequestQueue queue;
    private LayoutInflater inflater;
    private View v;
    private ProgressBar progressBar;
    private TextView mTextView;
    private NumberPicker picker;
    private NumberPicker picker2;
    private String allcurrencies;
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
//        final String allcurrencies[] = {  "AED:United Arab Emirates dirham",
//                "AFN:Afghan afghani",
//                "ALL:Albanian lek",
//                "AMD:Armenian dram",
//                "ALL:Albanian lek",
//                "ZWL:Zimbabwean dollar",
//                "GBP:Great Britain Pound",
//                "INR:Indian Rupees"};
//        ArrayList<String> list = new ArrayList<String>();
//        list.add("one");
//
//        list.add("two");
//        list.add("three");
//
//        StringBuilder sb = new StringBuilder();
//        for (String s : list)
//        {
//            sb.append(s);
//            sb.append("\t");
//        }
//
//        System.out.println(arrayList.toString());
////        picker.setMinValue(0);
////        picker.setMaxValue(allcurrencies.length-1);
//////        picker.setDisplayedValues( new String[] { "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BOV", "BRL", "BSD", "BTN", "BWP", "BYR", "BZD", "CAD", "CDF", "CHE", "CHF", "CHW", "CLF", "CLP", "CNY", "COP", "COU", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "INR", "IQD", "IRR", "ISK", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MXV", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "SSP", "STD", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "USN", "USS", "UYI", "UYU", "UZS", "VEF", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XBA", "XBB", "XBC", "XBD", "XCD", "XDR", "XFU", "XOF", "XPD", "XPF", "XPT", "XTS", "XXX", "YER", "ZAR", "ZMW" } );
////        picker.setDisplayedValues(allcurrencies);
////        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
//        picker2.setMinValue(0);
//        picker2.setMaxValue(allcurrencies.length-1);
////        picker2.setDisplayedValues( new String[] { "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN", "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BOV", "BRL", "BSD", "BTN", "BWP", "BYR", "BZD", "CAD", "CDF", "CHE", "CHF", "CHW", "CLF", "CLP", "CNY", "COP", "COU", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL", "HRK", "HTG", "HUF", "IDR", "ILS", "INR", "IQD", "IRR", "ISK", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MXV", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD", "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON", "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP", "SLL", "SOS", "SRD", "SSP", "STD", "SYP", "SZL", "THB", "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX", "USD", "USN", "USS", "UYI", "UYU", "UZS", "VEF", "VND", "VUV", "WST", "XAF", "XAG", "XAU", "XBA", "XBB", "XBC", "XBD", "XCD", "XDR", "XFU", "XOF", "XPD", "XPF", "XPT", "XTS", "XXX", "YER", "ZAR", "ZMW" } );
//        picker2.setDisplayedValues(allcurrencies);
//        picker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);


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

    public void everythingsGood(String part1,String part2){
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22"+part1+part2+"%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject currentrateinfo = jsonObject.getJSONObject("query");
                            JSONObject json_results = currentrateinfo.getJSONObject("results");
                            System.out.println(json_results);
                            progressBar.setVisibility(View.INVISIBLE);

                            JSONObject obj = json_results.getJSONObject("rate");

//                            String name = obj.getString("Name");
                            String url = obj.getString("Rate");
//                            String date = obj.getString("Date");
//                            String time = obj.getString("Time");
                            mTextView.setVisibility(View.VISIBLE);
                            mTextView.setText("Current Rate :"+url);
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
