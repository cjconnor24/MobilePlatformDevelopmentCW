package uk.co.chrisconnor.mpdcw;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "BaseActivity";
    static final String EARTHQUAKE_TRANSFER = "EARTHQUAKE_TRANSFER";

//    void activateToolbar(boolean enableHome){
//        Log.d(TAG, "activateToolbar: starting");
//        ActionBar actionBar = getSupportActionBar();
//
//        if(actionBar == null){
//            Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//
//            if(toolbar != null){
//                setSupportActionBar(toolbar);
//                actionBar = getSupportActionBar();
//            }
//
//        }
//
//        if(actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(enableHome);
//        }
//    }

}
