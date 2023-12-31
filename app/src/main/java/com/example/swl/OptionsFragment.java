package com.example.swl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class OptionsFragment extends Fragment {

    Button button1;
    ButtonModel B;
    EditText IPAddress_et;

    public OptionsFragment(ButtonModel B) {
        this.B = B;
    }

    public static OptionsFragment newInstance(ButtonModel buttonModel) {
        OptionsFragment fragment = new OptionsFragment(buttonModel);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button1 = (Button) view.findViewById(R.id.SaveParam);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                B.setIP_Addr(IPAddress_et.getText().toString());
            }});

        IPAddress_et = (EditText) view.findViewById(R.id.IPAddress);
        IPAddress_et.setText(B.getIP_Addr());

    }
}