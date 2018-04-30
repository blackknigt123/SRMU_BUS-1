package com.example.admin;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.admin.model.recyclerview_model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class admin_main_profile extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private add_bus add_bus;
    private menu4 menu4;
    private menu3 menu3;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<recyclerview_model> items;

    DatabaseReference databaseReference;
    private long count_child;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

//        databaseReference= FirebaseDatabase.getInstance().getReference("Bus Details");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                items= new ArrayList<>();
//                recyclerView =(RecyclerView)findViewById(R.id.rv);
//                recyclerView.setLayoutManager(new LinearLayoutManager(admin_main_profile.this));
//                recyclerView.addItemDecoration(new DividerItemDecoration(admin_main_profile.this,LinearLayoutManager.VERTICAL));
//
//                for (DataSnapshot snap:dataSnapshot.getChildren())
//                {
//                    recyclerview_model model = new recyclerview_model(
//                            "bus Number"+(snap.getKey()),
//                            "startpoint",
//                            "current stop",
//                            "nextstop",
//                            12);
//                    items.add(model);
//
//                }
//
//                adapter= (RecyclerView.Adapter)new recyclerview_adapter(admin_main_profile.this,items);
//                recyclerView.setAdapter(adapter);
//
//            }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });





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
        getMenuInflater().inflate(R.menu.admin_main_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {


         startActivity(new Intent(this,admin_main_profile.class));
        } else if (id == R.id.add_bus) {

            add_bus = new add_bus();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout, add_bus).commit();


        } else if (id == R.id.bus_details) {

            menu3 =new menu3();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout, menu3).commit();


        } else if (id == R.id.bus_route) {

            menu4=new menu4();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.mainLayout, menu4).commit();



       }


        else if (id == R.id.logout) {

            final Intent moveToLogin = new Intent(this, admin_login.class);

            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(moveToLogin);

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (add_bus!=null){
                add_bus.setBusStopPoints(data);
            }
            Toast.makeText(this, "null h fragment", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no data recieved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Toast.makeText(this, "yaha pe fragment ka object save kro", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "restore fragment object here", Toast.LENGTH_SHORT).show();
    }
}
