package com.studio.sameer.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
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

import java.util.ArrayList;
import java.util.List;

//import android.app.Fragment;

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

        Context context = this.getContext();
        final  globalArrayList globalArrayList = new globalArrayList(context);
//        final  globalArrayList globalArrayList = new globalArrayList();
        String[] a = {"USD", "JPY"};
        System.out.println("trying to get data:"+globalArrayList.getAllItem());
//        if (globalArrayList.getAllItem().size()<1){
//            System.out.println("Storing");
//            globalArrayList.addItem(a[1]);
//        }
        progressBar.setDrawingCacheBackgroundColor(Color.BLACK);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                                                    @Override
                                                    public void onRefresh() {
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
                System.out.println("CURRENCY CHOSE BY USER" + currencyChoseByUser);

//                heading.setText("Current rate for " + currencyChoseByUser);
                getData(currencyChoseByUser);


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
        String url = "http://api.fixer.io/latest?base=" + currencyChoseByUser;
        queue = Volley.newRequestQueue(getActivity().getApplicationContext());


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
                            String jsonData = jsonObject.getString("base");
                            String date = jsonObject.getString("date");

                            System.out.println("Currency selected by user: " + jsonData);
                            System.out.println("Date: " + date);

                            JSONObject currentrateinfo = jsonObject.getJSONObject("rates");

                            System.out.println("rates  " + currentrateinfo);
                            int rows = currentrateinfo.length();
                            System.out.println("Length of rates object  " + rows);
                            String[] myCurrencyList = {"AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK", "GBP", "HKD", "HRK", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "TRY", "USD", "ZAR"};
                            card_items = new ArrayList<>();
                            for (int a = 0; a < myCurrencyList.length; a++) {
                                System.out.println(myCurrencyList[a]);
                                if (currencyChoseByUser.equals(myCurrencyList[a])) {
                                } else {
                                    System.out.println(currentrateinfo.getString(myCurrencyList[a]));
                                    card_item card_item = new card_item(myCurrencyList[a] + "/" + currencyChoseByUser,
                                            currentrateinfo.getString(myCurrencyList[a]),
                                            date
                                    );
                                    card_items.add(card_item);
                                }
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
