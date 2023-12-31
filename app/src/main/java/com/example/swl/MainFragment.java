package com.example.swl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;

import java.net.HttpURLConnection;

public class MainFragment extends Fragment {

    Button button1, button2;
    Switch aSwitch_B1;
    ImageView StatusBall_B1;

    public ButtonModel B1, B2;

    //DBHelper dbHelper;
    //SQLiteDatabase db;
    //Cursor cursor;
    //String IPAddress;


    EventHandler eventHandler;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        eventHandler = (EventHandler) context;
    }

    public MainFragment() {
    }

    public static Fragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        B1 = new ButtonModel(getContext(), "B1");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button1 = (Button) view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventHandler.Execute(B1);
            }
        });


        StatusBall_B1 = (ImageView) view.findViewById(R.id.StatusBall);
        StatusBall_B1.setImageResource(R.drawable.grey_ball);
        StatusBall_B1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getStateRelay();
            }
        });


        aSwitch_B1 = (Switch) view.findViewById(R.id.switch1);
        aSwitch_B1.setVisibility(View.INVISIBLE);
        aSwitch_B1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Switch_ON_OFF(B1.GetURL_ON());
                }else{
                    Switch_ON_OFF(B1.GetURL_OFF());
                }
            }
        });

       getStateRelay();

    }

    private void getStateRelay(){


        B1.setOnLineState(false);
        StatusBall_B1.setImageResource((B1.getOnLineState()) ? R.drawable.green_ball : R.drawable.grey_ball);
        aSwitch_B1.setVisibility(View.INVISIBLE);

        new APIAccessTask(getContext(), B1.GetURL_getState(), "GET",
                new APIAccessTask.OnCompleteListener() {
                    @Override
                    public void onComplete(APIResponseObject result) throws JSONException {

                        if (result != null) {

                            if (result.responseCode == HttpURLConnection.HTTP_OK) {
                                String str = result.response;

                                B1.setOnLineState(true);

                                StatusBall_B1.setImageResource((B1.getOnLineState()) ? R.drawable.green_ball : R.drawable.grey_ball);
                                aSwitch_B1.setVisibility((B1.getOnLineState()) ? View.VISIBLE : View.INVISIBLE);

                                if (str.equals("ON")){
                                    aSwitch_B1.setChecked(true);
                                } else if (str.equals("OFF")) {
                                    aSwitch_B1.setChecked(false);
                                }
                                

                            } else {
                                //Toast.makeText(getContext(), "Модуль не доступен (1)", Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            //Toast.makeText(getContext(), "Модуль не доступен (2)", Toast.LENGTH_SHORT).show();
                        }


                    }}).execute();
    }

    @Override
    public void onResume() {
        super.onResume();

        //Toast.makeText(getContext(),B1.getIP_Addr(), Toast.LENGTH_SHORT).show();
    }

    private void Switch_ON_OFF(String url){

        //Toast.makeText(getContext(),B1.getIP_Addr(), Toast.LENGTH_SHORT).show();

        String method = "GET";
        new APIAccessTask(getContext(), url, method,
                new APIAccessTask.OnCompleteListener() {
                    @Override
                    public void onComplete(APIResponseObject result) throws JSONException {

                        if (result != null) {

                            if (result.responseCode == HttpURLConnection.HTTP_OK) {
                                String str = result.response;

                            } else {
                                Toast.makeText(getContext(), "Модуль не доступен (1)", Toast.LENGTH_SHORT).show();
                                /*AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                                adb.setTitle("ВНИМАНИЕ!");
                                adb.setMessage("Не удалось связаться с устройством");
                                adb.setPositiveButton("ЗАКРЫТЬ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });

                                adb.create();
                                adb.show();*/
                            }

                        }else {
                            Toast.makeText(getContext(), "Модуль не доступен (2)", Toast.LENGTH_SHORT).show();
                        }


                    }}).execute();
    }
}