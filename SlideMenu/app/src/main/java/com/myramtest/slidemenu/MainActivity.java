package com.myramtest.slidemenu;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.myramtest.slidemenu.adapter.SlidingMenuAdapter;
import com.myramtest.slidemenu.fragment.Fragment1;
import com.myramtest.slidemenu.fragment.Fragment2;
import com.myramtest.slidemenu.fragment.Fragment3;
import com.myramtest.slidemenu.model.ItemSlideMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listViewSliding = (ListView) findViewById(R.id.lv_sliding_menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listSliding = new ArrayList<>();

        listSliding.add(new ItemSlideMenu(R.drawable.ic_action_tache, "Ajouter une tâche"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_action_taches, "Mes tâches"));
        listSliding.add(new ItemSlideMenu(R.mipmap.ic_launcher, "About"));
        adapter = new SlidingMenuAdapter(this, listSliding);
        listViewSliding.setAdapter(adapter);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle(listSliding.get(0).getTitle());//envoie le titre

        listViewSliding.setItemChecked(0, true);//sélectionner un item

        drawerLayout.closeDrawer(listViewSliding);//close le menu

        replaceFragment(0);

        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setTitle(listSliding.get(position).getTitle());
                listViewSliding.setItemChecked(position, true);
                replaceFragment(position);
                drawerLayout.closeDrawer(listViewSliding);

            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private void replaceFragment(int pos){
        android.app.Fragment fragment = null;
        switch (pos){
            case 0:
                fragment = new Fragment1();
                break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                fragment = new Fragment3();
                break;
            default:
                fragment = new Fragment1();

        }

        if(null!=fragment){
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

    }
}