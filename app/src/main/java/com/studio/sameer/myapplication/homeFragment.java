package com.studio.sameer.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class homeFragment extends Fragment {
    private RequestQueue queue;
    private String currencyChoseByUser;
    private SearchableSpinner spinner;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView mTextview;
    private TextView heading;
    private TableLayout table;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<card_item> card_items;
    private RelativeLayout rl;
    public homeFragment() {}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressBar=(ProgressBar)view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
//        table = (TableLayout) view.findViewById(R.id.displayLinear);
        mTextview = (TextView) view.findViewById(R.id.textView);
        heading = (TextView) view.findViewById(R.id.textview);
        spinner = (SearchableSpinner) view.findViewById(R.id.searchableSpinner);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        spinner.setTitle("Select Country");
        spinner.setPositiveButton("OK");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        rl = (RelativeLayout)view.findViewById(R.id.relativeLayout2); // heading layout
        // Spinner Drop down elements

//        List<String> categories = new ArrayList<String>();
//        categories.add("AED:United Arab Emirates dirham");
//        categories.add("AFN:Afghan afghani");
//        categories.add("ALL:Albanian lek");
//        categories.add("AMD:Armenian dram");
//        categories.add("ALL:Albanian lek");
//        categories.add("ZWL:Zimbabwean dollar");
//        categories.add("GBP:Great Britain Pound");
//        categories.add("INR:Indian Rupees");
//        // Creating adapter for spinner
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
//        // Drop down layout style - list view with radio button
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
////        simple_spinner_dropdown_item  Old Spinner Option above
//        // attaching data adapter to spinner
//        spinner.setAdapter(dataAdapter);

        Context context = this.getContext();
        final  globalArrayList globalArrayList = new globalArrayList(context);
//        final  globalArrayList globalArrayList = new globalArrayList();
        String[]a ={ "AED", "AFN"};
        System.out.println("trying to get data:"+globalArrayList.getAllItem());
//        if (globalArrayList.getAllItem().size()<1){
//            System.out.println("Storing");
//            globalArrayList.addItem(a[1]);
//        }
        progressBar.setDrawingCacheBackgroundColor(Color.BLACK);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                    @Override
                                                    public void onRefresh() {
//                                                        table.removeAllViews();
                                                        swipeRefreshLayout.setColorSchemeColors(Color.RED);
                                                        System.out.println("Refreshing Data");

                                                        currencyChoseByUser = getUserInput();
                                                        getData(currencyChoseByUser);
                                                    }

                                                }

        );


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.bringToFront();
//                table.removeAllViews();

                String tempString = spinner.getSelectedItem().toString();
                String[] parts2 = tempString.split(":");
                String part1 = parts2[0];
                String currencyChoseByUser = part1;
                System.out.println("ss");
                globalArrayList.getAllItem();

                globalArrayList.addItem(tempString);// This inserrt statement fuking it up :(
//                globalArrayList.showItem();
                currencyChoseByUser = getUserInput();
                System.out.println(currencyChoseByUser);

//                heading.setText("Current rate for " + currencyChoseByUser);
                getData(currencyChoseByUser);
//                String url = "https://query.yahooapis.com/v1/public/yql?q=" +
//                        "select%20*%20from%20yahoo.finance.xchange%20%20where%20pair%20in%20(%22" +
//                        currencyChoseByUser+"EUR%22%2C%20%22"+currencyChoseByUser+"JPY%22%2C%20%22"+
//                        currencyChoseByUser+"BGN%22%2C%20%22"+currencyChoseByUser+"CZK%22%2C%20%22"+
//                        currencyChoseByUser+"DKK%22%2C%20%22"+currencyChoseByUser+"GBP%22%2C%20%22"+
//                        currencyChoseByUser+"HUF%22%2C%20%22"+currencyChoseByUser+"LTL%22%2C%20%22"+
//                        currencyChoseByUser+"LVL%22%2C%20%22"+currencyChoseByUser+"PLN%22%2C%20%22"+
//                        currencyChoseByUser+"RON%22%2C%20%22"+currencyChoseByUser+"SEK%22%2C%20%22"+
//                        currencyChoseByUser+"CHF%22%2C%20%22"+currencyChoseByUser+"NOK%22%2C%20%22"+
//                        currencyChoseByUser+"HRK%22%2C%20%22"+currencyChoseByUser+"RUB%22%2C%20%22"+
//                        currencyChoseByUser+"TRY%22%2C%20%22"+currencyChoseByUser+"AUD%22%2C%20%22"+
//                        currencyChoseByUser+"BRL%22%2C%20%22"+currencyChoseByUser+"CAD%22%2C%20%22"+
//                        currencyChoseByUser+"CNY%22%2C%20%22"+currencyChoseByUser+"HKD%22%2C%20%22"+
//                        currencyChoseByUser+"IDR%22%2C%20%22"+currencyChoseByUser+"ILS%22%2C%20%22"+
//                        currencyChoseByUser+"INR%22%2C%20%22"+currencyChoseByUser+"KRW%22%2C%20%22"+
//                        currencyChoseByUser+"MXN%22%2C%20%22"+currencyChoseByUser+"MYR%22%2C%20%22"+
//                        currencyChoseByUser+"NZD%22%2C%20%22"+currencyChoseByUser+"PHP%22%2C%20%22"+
//                        currencyChoseByUser+"SGD%22%2C%20%22"+currencyChoseByUser+"THB%22%2C%20%22"+
//                        currencyChoseByUser+"ZAR%22%2C%20%22"+currencyChoseByUser+"ISK%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
//                String exampleURL = "https://query.yahooapis.com/v1/public/yql?q=select%20%2a%20from%20yahoo.finance.xchange%20where%20pair%20in%20%28%22USDEUR%22,%20%22USDJPY%22,%20%22USDBGN%22,%20%22USDCZK%22,%20%22USDDKK%22,%20%22USDGBP%22,%20%22USDHUF%22,%20%22USDLTL%22,%20%22USDLVL%22,%20%22USDPLN%22,%20%22USDRON%22,%20%22USDSEK%22,%20%22USDCHF%22,%20%22USDNOK%22,%20%22USDHRK%22,%20%22USDRUB%22,%20%22USDTRY%22,%20%22USDAUD%22,%20%22USDBRL%22,%20%22USDCAD%22,%20%22USDCNY%22,%20%22USDHKD%22,%20%22USDIDR%22,%20%22USDILS%22,%20%22USDINR%22,%20%22USDKRW%22,%20%22USDMXN%22,%20%22USDMYR%22,%20%22USDNZD%22,%20%22USDPHP%22,%20%22USDSGD%22,%20%22USDTHB%22,%20%22USDZAR%22,%20%22USDISK%22%20)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
//                queue = Volley.newRequestQueue(getActivity().getApplicationContext());
////        String url ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22"+part1+part2+"%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
//                // Request a string response from the provided URL.
//                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                // Display the first 500 characters of the response string.
//                                System.out.println(response);
//                                try {
//                                    progressBar.setVisibility(View.INVISIBLE);
//                                    JSONObject jsonObject = new JSONObject(response);
//                                    JSONObject currentrateinfo = jsonObject.getJSONObject("query");
//                                    JSONObject json_results = currentrateinfo.getJSONObject("results");
//                                    JSONArray jsonArray = json_results.getJSONArray("rate");
////                            System.out.println("\nLength of Array is " + jsonArray.length());
////                            System.out.println(json_results);
//                                    int rows = jsonArray.length();
//                                    int colums  = 3;
//                                    table.setStretchAllColumns(true);
//                                    for(int i = 0; i < rows; i++){
//                                        JSONObject obj = jsonArray.getJSONObject(i);
////                                System.out.println(obj);
//                                        String name = obj.getString("Name");
//                                        String url  = obj.getString("Rate");
//                                        String date = obj.getString("Date");
//                                        String time = obj.getString("Time");
////                                System.out.println(name);
////                                System.out.println(url);
////                                System.out.println(date);
//                                        TableRow tr =  new TableRow(getActivity());
//
//                                        for(int j = 0; j < colums; j++)
//                                        {
//                                            TextView txtGeneric = new TextView(getActivity());
//                                            txtGeneric.setTextSize(15);
//                                            switch (j){
//                                                case 0:
//                                                    txtGeneric.setText( name );
//                                                    txtGeneric.setPadding(30,0,0,0);
//                                                    break;
//                                                case 1:
//                                                    txtGeneric.setText(url);
//                                                    txtGeneric.setPadding(0,0,0,0);
//                                                    break;
//                                                case 2:
//                                                    txtGeneric.setPadding(0,0,0,0);
//                                                    txtGeneric.setText("Rate last updated:" + date +" "+time);
//                                                    break;
//                                            }
//                                            tr.addView(txtGeneric);
//                                            txtGeneric.setWidth(10);
//                                            txtGeneric.setTextColor(Color.BLACK);
//                                        }
//                                table.addView(tr);
//                                    }
////                            mTextView.setText("Response is: "+ json_results);
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
////                        TextView txtGeneric = new TextView(getActivity());
////                        txtGeneric.setTextSize(15);
////                        txtGeneric.setText("Something Went wrong!");
//                        mTextview.setText("Something Went wrong!");
//                    }
//                });
//                queue.add(stringRequest);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

         return view;
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu, menu);
        return true;
    }
    public String getUserInput(){
        String tempString = spinner.getSelectedItem().toString();
        String[] parts2 = tempString.split(":");
        String part1 = parts2[0];
        return currencyChoseByUser = part1;
    }


    public void getData(final String currencyChoseByUser){
        String url = "https://query.yahooapis.com/v1/public/yql?q=" +
                "select%20*%20from%20yahoo.finance.xchange%20%20where%20pair%20in%20(%22" +
                currencyChoseByUser+"EUR%22%2C%20%22"+currencyChoseByUser+"JPY%22%2C%20%22"+
                currencyChoseByUser+"BGN%22%2C%20%22"+currencyChoseByUser+"CZK%22%2C%20%22"+
                currencyChoseByUser+"DKK%22%2C%20%22"+currencyChoseByUser+"GBP%22%2C%20%22"+
                currencyChoseByUser+"HUF%22%2C%20%22"+currencyChoseByUser+"LTL%22%2C%20%22"+
                currencyChoseByUser+"LVL%22%2C%20%22"+currencyChoseByUser+"PLN%22%2C%20%22"+
                currencyChoseByUser+"RON%22%2C%20%22"+currencyChoseByUser+"SEK%22%2C%20%22"+
                currencyChoseByUser+"CHF%22%2C%20%22"+currencyChoseByUser+"NOK%22%2C%20%22"+
                currencyChoseByUser+"HRK%22%2C%20%22"+currencyChoseByUser+"RUB%22%2C%20%22"+
                currencyChoseByUser+"TRY%22%2C%20%22"+currencyChoseByUser+"AUD%22%2C%20%22"+
                currencyChoseByUser+"BRL%22%2C%20%22"+currencyChoseByUser+"CAD%22%2C%20%22"+
                currencyChoseByUser+"CNY%22%2C%20%22"+currencyChoseByUser+"HKD%22%2C%20%22"+
                currencyChoseByUser+"IDR%22%2C%20%22"+currencyChoseByUser+"ILS%22%2C%20%22"+
                currencyChoseByUser+"INR%22%2C%20%22"+currencyChoseByUser+"KRW%22%2C%20%22"+
                currencyChoseByUser+"MXN%22%2C%20%22"+currencyChoseByUser+"MYR%22%2C%20%22"+
                currencyChoseByUser+"NZD%22%2C%20%22"+currencyChoseByUser+"PHP%22%2C%20%22"+
                currencyChoseByUser+"SGD%22%2C%20%22"+currencyChoseByUser+"THB%22%2C%20%22"+
                currencyChoseByUser+"ZAR%22%2C%20%22"+currencyChoseByUser+"ISK%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        String exampleURL = "https://query.yahooapis.com/v1/public/yql?q=select%20%2a%20from%20yahoo.finance.xchange%20where%20pair%20in%20%28%22USDEUR%22,%20%22USDJPY%22,%20%22USDBGN%22,%20%22USDCZK%22,%20%22USDDKK%22,%20%22USDGBP%22,%20%22USDHUF%22,%20%22USDLTL%22,%20%22USDLVL%22,%20%22USDPLN%22,%20%22USDRON%22,%20%22USDSEK%22,%20%22USDCHF%22,%20%22USDNOK%22,%20%22USDHRK%22,%20%22USDRUB%22,%20%22USDTRY%22,%20%22USDAUD%22,%20%22USDBRL%22,%20%22USDCAD%22,%20%22USDCNY%22,%20%22USDHKD%22,%20%22USDIDR%22,%20%22USDILS%22,%20%22USDINR%22,%20%22USDKRW%22,%20%22USDMXN%22,%20%22USDMYR%22,%20%22USDNZD%22,%20%22USDPHP%22,%20%22USDSGD%22,%20%22USDTHB%22,%20%22USDZAR%22,%20%22USDISK%22%20)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());
//        String url ="https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22"+part1+part2+"%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=";
        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        System.out.println(response);
//                        try {
//                            progressBar.setVisibility(View.INVISIBLE);
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONObject currentrateinfo = jsonObject.getJSONObject("query");
//                            JSONObject json_results = currentrateinfo.getJSONObject("results");
//                            JSONArray jsonArray = json_results.getJSONArray("rate");
////                            System.out.println("\nLength of Array is " + jsonArray.length());
////                            System.out.println(json_results);
//                            int rows = jsonArray.length();
//                            int colums  = 3;
////                            table.setStretchAllColumns(true);
//                            for(int i = 0; i < rows; i++){
//                                JSONObject obj = jsonArray.getJSONObject(i);
////                                System.out.println(obj);
//                                String name = obj.getString("Name");
//                                String url  = obj.getString("Rate");
//                                String date = obj.getString("Date");
//                                String time = obj.getString("Time");
////                                System.out.println(name);
////                                System.out.println(url);
////                                System.out.println(date);
//                                TableRow tr =  new TableRow(getActivity());
//
//                                for(int j = 0; j < colums; j++)
//                                {
//                                    TextView txtGeneric = new TextView(getActivity());
//                                    txtGeneric.setTextSize(15);
//                                    switch (j){
//                                        case 0:
//                                            txtGeneric.setText( name );
//                                            txtGeneric.setPadding(30,0,0,0);
//                                            break;
//                                        case 1:
//                                            txtGeneric.setText(url);
//                                            txtGeneric.setPadding(0,0,0,0);
//                                            break;
//                                        case 2:
//                                            txtGeneric.setPadding(0,0,0,0);
//                                            txtGeneric.setText("Rate last updated:" + " "+time);
//                                            break;
//                                    }
//                                    tr.addView(txtGeneric);
//
//                                    txtGeneric.setWidth(10);
//                                    txtGeneric.setTextColor(Color.BLACK);
//                                }
////                                table.addView(tr);
//                                swipeRefreshLayout.setRefreshing(false);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //                        TextView txtGeneric = new TextView(getActivity());
//                //                        txtGeneric.setTextSize(15);
//                //                        txtGeneric.setText("Something Went wrong!");
//                swipeRefreshLayout.setRefreshing(false);
//                mTextview.setText("Something Went wrong!");
//            }
//        });
//        queue.add(stringRequest);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        mTextView.setText("Response is: "+ response.toString());
                        try {
                            heading.setText("Current rate for " + currencyChoseByUser );
                            rl.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);

                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject currentrateinfo = jsonObject.getJSONObject("query");
                            JSONObject json_results = currentrateinfo.getJSONObject("results");
                            JSONArray jsonArray = json_results.getJSONArray("rate");

//                            System.out.println("\nLength of Array is " + jsonArray.length());
                            System.out.println(json_results);
                            System.out.println(jsonArray);
                            int rows = jsonArray.length();
                            card_items = new ArrayList<>();
                            int colums  = 3;
                            for (int i = 0; i < jsonArray.length();i++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                                String name = jsonObject1.getString("Name");
//                                String rate  = jsonObject1.getString("Rate");
//                                String date = jsonObject1.getString("Date");
                                card_item card_item = new card_item(jsonObject1.getString("Name"),
                                        jsonObject1.getString("Rate"),
                                        jsonObject1.getString("Date")
                                );
                                card_items.add(card_item);
                            }
                            adapter = new MyAdapter(card_items,getActivity());
                            recyclerView.setAdapter(adapter);
                            swipeRefreshLayout.setRefreshing(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mTextView2.setText("That didn't work!");
//                heading.setTextSize(15);
                heading.setText("Try Again");
                swipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.INVISIBLE);

                rl.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
//                clearData();

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);




    }
    public void clearData() {
        card_items.clear(); //clear list
        adapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }


}
