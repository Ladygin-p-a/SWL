package com.example.swl;

import static android.view.View.VISIBLE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONException;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements EventHandler{

    MainFragment mainFragment;
    OptionsFragment optionsFragment;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null){
            //getSupportActionBar().hide();
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MainFragment mainFragment = (MainFragment) MainFragment.newInstance();

        fragmentTransaction.add(R.id.flLists, mainFragment, Constants.TAG_MAIN_FRAG);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.flLists);

        if(currentFragment.getTag().equals(Constants.TAG_OPTION_FRAG)) {
            //Toast.makeText(this,"Возврат в окно настроек", Toast.LENGTH_SHORT).show();
        } else if (currentFragment.getTag().equals(Constants.TAG_MAIN_FRAG)){
            //Toast.makeText(this, "Возврат в главное окно", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void Execute(ButtonModel buttonModel) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        OptionsFragment optionsFragment = (OptionsFragment) OptionsFragment.newInstance(buttonModel);

        fragmentTransaction.replace(R.id.flLists, optionsFragment, Constants.TAG_OPTION_FRAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnON:
                Switch_ON_OFF("http://192.168.1.126/update?output=0&state=1");
                break;
            case R.id.btnOFF:
                Switch_ON_OFF("http://192.168.1.126/update?output=0&state=0");
                break;
            default:
                break;
        }
    }

    private void Switch_ON_OFF(String url){

        //String url = "http://192.168.7.20:25255/BD/hs/GetDataFromShipment/Autorization/";
        //String url = "http://192.168.1.70/5/on";
        String method = "GET";
        new APIAccessTask(MainActivity.this, url, method,
                new APIAccessTask.OnCompleteListener() {
                    @Override
                    public void onComplete(APIResponseObject result) throws JSONException {

                        if (result != null) {
                            if (result.responseCode == HttpURLConnection.HTTP_OK) {
                                String str = result.response;

                            } else {
                                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                                adb.setTitle("ВНИМАНИЕ!");
                                adb.setMessage("Ошибка соединения с базой.");
                                adb.setPositiveButton("ЗАКРЫТЬ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                adb.create();
                                adb.show();
                            }

                        }


                    }}).execute();
    }



 */
}