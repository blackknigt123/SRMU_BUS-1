package com.example.vaibhav.srmu_bus;


import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class stu_main_profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseAuth mAuth;
    ImageView profile_pic;
    TextView stuname,email;
    public ListView noticeList;

    ArrayList notices = new ArrayList<String>();
    ArrayList noticeLinks = new ArrayList<String>();
    private Object Window;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stu_main_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("SRMU");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        profile_pic=headerView.findViewById(R.id.profile_pic);
        stuname=headerView.findViewById(R.id.name);
        email=headerView.findViewById(R.id.email);
        pb=(ProgressBar)findViewById(R.id.pb);

        loadUserInformation();

        noticeList = (ListView)findViewById(R.id.noticeList);

        // Notice Board
        RetrieveNoticesTask task = new RetrieveNoticesTask();
        task.execute();
        //task.onPostExecute();

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
        getMenuInflater().inflate(R.menu.stu_main_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {





        Fragment fragment = null;


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id==R.id.home)
        {
            startActivity(new Intent(this,stu_main_profile.class));
        }

        if (id == R.id.menu1) {
            // Handle the camera action
            fragment = new menu1();
        } else if (id == R.id.menu20) {
            fragment = new menu2();



        } else if (id == R.id.menu3) {
            fragment = new menu3();

        } else if (id == R.id.menu4) {
            fragment = new menu4();

            }

            else if (id == R.id.nav_feedback) {
           fragment = new feedback();

        }

        else if (id ==R.id.logout)
        {

           logout();
        }


        if(fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_area,fragment);

            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logout() {

        final Intent moveToLogin = new Intent(this,stu_home.class);

                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(moveToLogin);
            }




    private void loadUserInformation() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {


            if (user.getPhotoUrl() != null) {

                Glide.with(this)
                        .load(user.getPhotoUrl().toString())
                        .into( profile_pic);

            }

           if (user.getDisplayName() != null) {

                stuname.setText(user.getDisplayName());


            }

            if (user.getEmail() !=null)
            {
                email.setText(user.getEmail());
            }


        }
    }

    class RetrieveNoticesTask extends AsyncTask<Void, Void, ArrayList<String>> {

        private Exception exception;
       // String htmlContent = null;

        protected ArrayList<String> doInBackground(Void... args) {
            try {
                Document page = Jsoup.parse(new URL("https://srmu.ac.in/"), 5000);

                Elements elements = page.select("#masonry > div:nth-child(1) > div");
                Element element = elements.get(0);

                ArrayList notices = new ArrayList<String>();

                Elements items = element.select(".dez-post-header a");

                for(Element item : items)
                {
                    String link = item.attr("href");

                    if(link == null)
                        noticeLinks.add("");
                    else
                        noticeLinks.add(link);

                    notices.add(item.text());
                }

                return notices;
                //TextView box = (TextView)findViewById(R.id.jsoupTextView);

               // htmlContent = page.text();

               // return page.text();

            } catch(MalformedURLException e) {
                this.exception = e;
            }
            catch(IOException e) {
                this.exception = e;
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            noticeList.setVisibility(View.GONE);
            pb.setVisibility(View.VISIBLE);

        }



        protected void onPostExecute(ArrayList<String> result) {
            // TODO: check this.exception
            // TODO: do something with the feed
            //noticesBox.setText(result);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(stu_main_profile.this,
                    android.R.layout.simple_list_item_1,
                    result

            )
            {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View view= super.getView(position, convertView, parent);

                    TextView textView=(TextView) view.findViewById(android.R.id.text1);

                    /*YOUR CHOICE OF COLOR*/
                    String col="#FFFFFFFF";
                    textView.setTextColor(Color.parseColor(col));
                    textView.setTypeface(null, Typeface.ITALIC);

                    return view;
                }
            };



            noticeList.setAdapter(adapter);
            noticeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    if(noticeLinks.get(i).toString().length() == 0)
                    {
                        return;
                    }

                    Uri uri = Uri.parse("https://srmu.ac.in/" + noticeLinks.get(i).toString());
                    Log.e("item clicked", i + " was clicked " + uri);
                    Intent intent = new Intent(Intent.ACTION_VIEW);

                    intent.setDataAndType(uri,"application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

                    Intent chooser = Intent.createChooser(intent, "Open File");
                    try {
                        // Launch chooser
                        startActivity(chooser);
                    } catch (ActivityNotFoundException e) {
                        // No PDF Reader found
                        startActivity(intent);
                    }
                }
            });

            pb.setVisibility(View.GONE);
            noticeList.setVisibility(View.VISIBLE);

            //Log.e("async_task", htmlContent);
        }
    }
}