package com.fragilebytes.asos;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.TextView;

import butterknife.Bind;

public class PopUp extends Activity {
    @Bind(R.id.pop_up_text)
    TextView textView;
//THIS ACTIVITY WILL BE USED AS A POP UP SCREEN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
//SET POP UP SCREEN PARAMETERS
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int) (width*.8), (int) (height*.6));

//CREATE A LIST BY THE PARAMETERS RECEIVED
        //createList(listType);
    }

//FUNCTION THAT WILL CREATE A LIST FROM RECEIVED REQUEST
    void createList(int listType){

        switch (listType){
            case 1:{

                textView.setText("FAVORITES STARTED");

            }
            break;
            case 2: {

                textView.setText("BASKET STARTED");

            }
            break;
        }

    }
}
