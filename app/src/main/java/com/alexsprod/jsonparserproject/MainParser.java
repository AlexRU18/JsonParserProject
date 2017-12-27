package com.alexsprod.jsonparserproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alexsprod.jsonparserproject.dir.CatOneFragment;
import com.alexsprod.jsonparserproject.dir.CatThreeFragment;
import com.alexsprod.jsonparserproject.dir.CatTwoFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainParser extends AppCompatActivity {
    private NavigationView nviewDrawer;
    private Toolbar toolbar;
    private DrawerLayout mDrawer;
    private String TAG = MainParser.class.getSimpleName();

    Fragment fragment = new CatOneFragment();
    Class fragmentClass;
    private ListView lv;
    private ArrayList<HashMap<String, String>> newslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_parser);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();


        nviewDrawer = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(nviewDrawer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        nviewDrawer.setCheckedItem(0);

        newslist = new ArrayList<>();
        lv = (ListView) findViewById(R.id.list_cat1);

        new GetData().execute();
    }

    private class GetData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(MainParser.this,"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "http://legs-legs.ru/cat1json.php?json=cat1";
            String jsonStr = sh.makeServiceCall(url);
            newslist=new ArrayList<>();    //Arraylist initialize
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {

                    JSONArray array = new JSONArray(jsonStr);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject c= null;
                        c = array.getJSONObject(i);
                        String id = c.getString("id");
                        String title_cat1 = c.getString("title_cat1");
                        String fulltext_cat1 = c.getString("fulltext_cat1");
                        // tmp hash map for single contact
                        HashMap<String, String> news_item = new HashMap<>();
                        news_item.put("id", id);
                        news_item.put("title_cat1", title_cat1);
                        news_item.put("fulltext_cat1", fulltext_cat1);
                        newslist.add(news_item);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            SimpleAdapter adapter = new SimpleAdapter(MainParser.this, newslist,
                    R.layout.item_cat1, new String[]{ "title_cat1","fulltext_cat1"},
                    new int[]{R.id.title_cat1, R.id.fulltext_cat1});
            lv.setAdapter(adapter);
        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_parser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_cat1:
                fragmentClass = CatOneFragment.class;
                break;
            case R.id.nav_cat2:
                fragmentClass = CatTwoFragment.class;
                break;
            case R.id.nav_cat3:
                fragmentClass = CatThreeFragment.class;
                break;
            default:
                fragmentClass = CatOneFragment.class;
        }
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Вставить фрагмент, заменяя любой существующий
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Выделение существующего элемента выполнено с помощью
        // NavigationView
        menuItem.setChecked(true);
        // Установить заголовок для action bar'а
        setTitle(menuItem.getTitle());
        // Закрыть navigation drawer
        mDrawer.closeDrawers();
    }
}
