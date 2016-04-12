package com.fragilebytes.asos;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.fragilebytes.asos.fragments.ItemViewFragment;
import com.fragilebytes.asos.fragments.ListingFragment;
import com.fragilebytes.asos.fragments.SideMenuFragment;
import com.fragilebytes.asos.fragments.EmptyFragment;
import com.fragilebytes.asos.models.category.CatalogModel;
import com.fragilebytes.asos.services.ItemClickListener;
import com.fragilebytes.asos.services.OnDataSend;


public class MainActivity extends ActionBarActivity implements OnDataSend {
    SharedPreferences sharedpreferences;

    public static final String mypreference = "mypref";
    public static final String Title = "nameKey";
    public static final String Id = "emailKey";


    private Toolbar mToolbar;
    private SideMenuFragment drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Title)) {
            //name.setText(sharedpreferences.getString(Name, ""));
        }
        if (sharedpreferences.contains(Id)) {
            //email.setText(sharedpreferences.getString(Email, ""));

        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (SideMenuFragment)
                getSupportFragmentManager().findFragmentById(R.id.sideMenuFragment);
        drawerFragment.setUp(R.id.sideMenuFragment, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);

        displayView();
    }

    public void PopUpWindow(View view){
        startActivity(new Intent(MainActivity.this, PopUp.class));
    }

    public void Save(View view) {
       // String n = name.getText().toString();
        //String e = email.getText().toString();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Title, "SOME NAME");
        editor.putString(Id, "SOME ID");
        Toast.makeText(getApplication().getApplicationContext(), "FAVORITES: " + Title + Id, Toast.LENGTH_SHORT).show();

        editor.commit();
    }

    public void Get(View view) {
        //name = (TextView) findViewById(R.id.etName);
        //email = (TextView) findViewById(R.id.etEmail);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        if (sharedpreferences.contains(Title)) {
            //name.setText(sharedpreferences.getString(Title, ""));
        }
        if (sharedpreferences.contains(Id)) {
            //email.setText(sharedpreferences.getString(Id, ""));

        }
    }

    private void displayView() {
        Fragment fragment = null;
                fragment = new EmptyFragment();

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onSendId(String id, int parameter) {
        Log.d("LOG","ID: " + id);
        switch (parameter){
            case 1:{

                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                ListingFragment listingFragment = new ListingFragment();
                listingFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, listingFragment);
                fragmentTransaction.commit();
            } break;
            case 2:{
                Bundle bundle = new Bundle();
                bundle.putString("p_id", id);
                ItemViewFragment itemViewFragment = new ItemViewFragment();
                itemViewFragment.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container_body, itemViewFragment);
                fragmentTransaction.commit();
            }
            break;
        }

    }
}
