package com.mieicfeup.df.footpoly.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import com.mieicfeup.df.footpoly.R;
import com.mieicfeup.df.footpoly.controller.PlayerController;
import com.mieicfeup.df.footpoly.model.Player;
import com.mieicfeup.df.footpoly.model.Stadium;

import java.util.ArrayList;

/**
 * Created by Diogo on 05/06/2016.
 */
public class MortgageDialog extends DialogFragment {
    private PlayerController player;
    private boolean[] checkedArray;

    public void setPlayer(PlayerController player)
    {
        this.player = player;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ArrayList<Stadium> stadiumList = (ArrayList<Stadium>) getArguments().getSerializable("stadiumList");

        final ArrayList<Integer> mSelectedItems = new ArrayList();

        ArrayList<String> stadiumNames = new ArrayList<String>();
        ArrayList<Integer> stadiumMortgage = new ArrayList<Integer>();
        checkedArray = new boolean[stadiumList.size()];

        for (int i = 0; i < stadiumList.size();i++)
        {
            Stadium stadium = stadiumList.get(i);
            stadiumNames.add(stadium.getName());
            stadiumMortgage.add(stadium.getCost());
            if(stadium.getMortgaged())
                checkedArray[i] = true;
            else
                checkedArray[i] = false;
        }

        String[] tempArray = new String[stadiumNames.size()];
        tempArray = stadiumNames.toArray(tempArray);



        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select the stadium")
                .setMultiChoiceItems(tempArray, checkedArray,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked)
                                    checkedArray[which] = true;
                                else if (mSelectedItems.contains(which))
                                    checkedArray[which] = false;
                            }
                        })
                .setPositiveButton(R.string.mortgage, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        for(int i = 0; i < checkedArray.length; i++)
                        {
                            Stadium stadium = stadiumList.get(i);
                            stadium.setMortgaged(checkedArray[i]);
                            Log.w(stadium.getName(), String.valueOf(checkedArray[i]));
                        }
                        player.updateText();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
