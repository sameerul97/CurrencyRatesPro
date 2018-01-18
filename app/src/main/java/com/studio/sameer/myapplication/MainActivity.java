package com.studio.sameer.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    android.support.v4.app.Fragment currentFragment;

    Boolean aIsActive = false;
    Boolean bIsActive = false;
    Boolean cIsActive = false;
    homeFragment homeFragment= new homeFragment();
    currencyconvertFragment currencyconvertFragment= new currencyconvertFragment();
    lastFragment lastFragment= new lastFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    android.app.FragmentManager manager=getFragmentManager();


                    if(!aIsActive)
                    {
                        getSupportFragmentManager().beginTransaction().add(R.id.contentLayout,homeFragment).commit();
                        aIsActive = true;
                    }
                    else
                    {
                        getSupportFragmentManager().beginTransaction().hide(currentFragment).show(homeFragment).commit();
                    }
                    currentFragment = homeFragment;
//                    manager.beginTransaction().replace(R.id.contentLayout,
//                            homeFragment,//change import statement import android.app.Fragment; with this
//                            homeFragment.getTag()).commit();

                    return true;
                case R.id.navigation_dashboard:
                    android.app.FragmentManager manager2=getFragmentManager();

                    if(!bIsActive)
                    {
                        getSupportFragmentManager().beginTransaction().hide(currentFragment).add(R.id.contentLayout,currencyconvertFragment).commit();
                        bIsActive = true;
                    }
                    else
                    {
                        getSupportFragmentManager().beginTransaction().hide(currentFragment).show(currencyconvertFragment).commit();
                    }
                    currentFragment = currencyconvertFragment;

//                    manager2.beginTransaction().replace(R.id.contentLayout,
//                            currencyconvertFragment,
//                            currencyconvertFragment.getTag()).commit();
                    return true;
                case R.id.navigation_notifications:
                    android.app.FragmentManager manager3 =getFragmentManager();

                    if(!cIsActive)
                    {
                        getSupportFragmentManager().beginTransaction().hide(currentFragment).add(R.id.contentLayout,lastFragment).commit();
                        cIsActive = true;
                    }
                    else
                    {
                        getSupportFragmentManager().beginTransaction().hide(currentFragment).show(lastFragment).commit();
                    }
//                    manager3.beginTransaction().replace(R.id.contentLayout,
//                            lastFragment,//change import statement import android.app.Fragment; with this
//                            lastFragment.getTag()).commit();

                    currentFragment = lastFragment;
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_home); // change back to navigation_home
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbarmenu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent newIntent;
        switch (item.getItemId()) {
            case R.id.about:
                System.out.println("About ");
                newIntent = new Intent(this,About.class);
                startActivity(newIntent);
                return true;

            case R.id.feedback:
                System.out.println("Feedback ");
                newIntent = new Intent(this,FeedBack.class);
                startActivity(newIntent);
                return true;

            case R.id.helpPage:
                System.out.println("Help Page ");
                newIntent = new Intent(this,Help_page.class);
                startActivity(newIntent);
                return true;

            case R.id.legalPage:
                System.out.println("Legal Page ");
                newIntent = new Intent(this,Legal_License.class);
                startActivity(newIntent);
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}
