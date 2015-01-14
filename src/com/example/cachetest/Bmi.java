package com.example.cachetest;
 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
 
public class Bmi extends Activity {
    private static final String TAG = "Bmi";
    private static final String PREF = "BMI_PREF";
    private static final String PREF_HEIGHT = "BMI_Height";
    private static final String PREF_W = "BMI_Weight";
     
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.activity_main);
        findViews();
        restorePrefs();
        setListensers();
    }
     
    private Button button_calc;
    private EditText field_height;
    private EditText field_weight;
    private void findViews()
    {
        button_calc = (Button)findViewById(R.id.submit);
        field_height = (EditText)findViewById(R.id.height);
        field_weight = (EditText)findViewById(R.id.weight);
    }
    private void setListensers()
    {
        button_calc.setOnClickListener(calcBMI);
    }
    private Button.OnClickListener calcBMI = new Button.OnClickListener()
    {
        public void onClick(View arg0)
        {
            //switch to report page
            Intent intent = new Intent();
            intent.setClass(Bmi.this, Report.class);
            Bundle bundle = new Bundle();
            bundle.putString("KEY_HEIGHT", field_height.getText().toString());
            bundle.putString("KEY_WEIGHT", field_weight.getText().toString());
            intent.putExtras(bundle);
            //Uri uri = Uri.parse(getString(R.string.homepage_uri));
            //intent.setData(uri); //ok
            //intent.setString("123"); //err
            //intent.setInt(124); //err
            startActivity(intent);
        }
    };
     
    private void openOptionsDialog()
    {
        new AlertDialog.Builder(Bmi.this)
        .setTitle(R.string.about_title)
        .setMessage(R.string.about_message)
        .setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener()
        {            
            public void onClick(DialogInterface dialog, int which)
            {
            }
        })
        .setNegativeButton(R.string.homepage_label, new DialogInterface.OnClickListener()
        {            
            public void onClick(DialogInterface dialog, int which)
            {
                //go to url
                Uri uri = Uri.parse(getString(R.string.homepage_uri));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        })
        .show();
    }
     
    protected static final int MENU_ABOUT = Menu.FIRST;
    protected static final int MENU_Quit = Menu.FIRST + 1;
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, MENU_ABOUT, Menu.NONE, "¹ØÓÚ...");
        menu.add(Menu.NONE, MENU_Quit, Menu.NONE, "½áÊø");
        return true;
    }
     
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        super.onOptionsItemSelected(item);
        switch (item.getItemId())
        {
        case MENU_ABOUT:
            openOptionsDialog();
            break;
        case MENU_Quit:
            finish();
            break;
        }
        return true;
    }
     
    @Override
    public void onStart()
    {
        super.onStart();
        Log.v(TAG, "onStart");
    }
    @Override
    public void onResume()
    {
        super.onResume();
        Log.v(TAG, "onResume");
    }
    @Override
    public void onPause()
    {
        super.onPause();
        Log.v(TAG, "onPause");
    }
    @Override
    public void onStop()
    {
        super.onStop();
        Log.v(TAG, "onStop");
        //save user preferences. use editor object to make change.
        SharedPreferences settings = getSharedPreferences(PREF, 0);
        settings.edit();
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PREF_HEIGHT, field_height.getText().toString());
        editor.putString(PREF_W, field_weight.getText().toString());
        editor.commit();
    }
    @Override
    public void onRestart()
    {
        super.onRestart();
        Log.v(TAG, "onRestart");
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Log.v(TAG, "onDestroy");
    }
     
    //restore preferences
    private void restorePrefs()
    {
        SharedPreferences settings = getSharedPreferences(PREF, 0);
        String pref_height = settings.getString(PREF_HEIGHT, "");
        if (!"".equals(pref_height))
        {
            field_height.setText(pref_height);
            field_weight.requestFocus();
        }
    }
}