package com.mieicfeup.df.footpoly.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.mieicfeup.df.footpoly.R;
import com.mieicfeup.df.footpoly.model.Stadium;

import java.util.ArrayList;

/**
 * Created by Diogo on 05/06/2016.
 */
public class MortgageDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<Stadium> stadiumList = (ArrayList<Stadium>) getArguments().getSerializable("stadiumList");
        final ArrayList<Integer> mSelectedItems = new ArrayList();

        ArrayList<String> stadiumNames = new ArrayList<String>();
        ArrayList<Integer> stadiumMortgage = new ArrayList<Integer>();

        for (Stadium stadium : stadiumList) {
            if (!stadium.getMortgaged()) {
                stadiumNames.add(stadium.getName());
                stadiumMortgage.add(stadium.getCost());
            }
        }

        String[] tempArray = new String[stadiumNames.size()];
        tempArray = stadiumNames.toArray(tempArray);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select the stadium")
                .setMultiChoiceItems(tempArray, null,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked)
                                    mSelectedItems.add(which);
                                else if (mSelectedItems.contains(which))
                                    mSelectedItems.remove((Integer) which);
                            }
                        })
                .setPositiveButton(R.string.mortgage, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

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
