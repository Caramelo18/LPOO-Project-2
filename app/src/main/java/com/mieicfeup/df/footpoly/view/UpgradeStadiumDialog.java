package com.mieicfeup.df.footpoly.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.mieicfeup.df.footpoly.R;
import com.mieicfeup.df.footpoly.controller.PlayerController;
import com.mieicfeup.df.footpoly.model.Stadium;

import java.util.ArrayList;

/**
 * Created by fabio on 06/06/2016.
 */
public class UpgradeStadiumDialog
{
    private Context context;
    private ArrayList<Stadium> stadiums;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Stadium stadium;
    private TextView cost;
    private PlayerController player;
    private int currUpgrade;

    public UpgradeStadiumDialog()
    {

    }
    public void setContext(Context context)
    {
        this.context = context;
    }

    public void setData(ArrayList<Stadium> stadiums, PlayerController player)
    {
        this.stadiums = stadiums;
        this.player = player;
    }

    public void showDialog () {
        builder = new AlertDialog.Builder(context);

        final String[] stadiumNames = new String[stadiums.size()];

        for(int i = 0; i < stadiums.size(); i++)
            stadiumNames[i] = stadiums.get(i).getName();

        builder.setTitle("Select the stadium")
                .setItems(stadiumNames, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stadium = stadiums.get(which);
                        changeView();
                    }
                });


        dialog = builder.create();
        dialog.show();
    }

    private void changeView()
    {
        dialog.dismiss();
        builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.upgrade_stadium_dialog, null);
        builder.setView(view);

        TextView stadiumName = (TextView) view.findViewById(R.id.upgradeStadiumName);
        stadiumName.setText(stadium.getName());

        currUpgrade = stadium.getUpgradeLevel();

        NumberPicker picker = (NumberPicker) view.findViewById(R.id.picker);
        picker.setMaxValue(2);
        picker.setMinValue(0);
        picker.setValue(currUpgrade);



        cost = (TextView) view.findViewById(R.id.upgradeCost);
        cost.setText("Upgrade Cost: " + String.valueOf(stadium.getUpgradeCost(currUpgrade)));

        picker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                currUpgrade = newVal;
                cost.setText("Upgrade Cost: " + String.valueOf(stadium.getUpgradeCost(currUpgrade)));
            }
        });

        Button upgrade = (Button) view.findViewById(R.id.upgradeButton);
        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stadium.upgradeStadium(currUpgrade);
                player.updateText();
                dialog.dismiss();
            }
        });



        dialog = builder.create();
        dialog.show();
    }
}
