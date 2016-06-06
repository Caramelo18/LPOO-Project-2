package com.mieicfeup.df.footpoly.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.mieicfeup.df.footpoly.R;
import com.mieicfeup.df.footpoly.controller.PlayerController;
import com.mieicfeup.df.footpoly.model.Player;
import com.mieicfeup.df.footpoly.model.Stadium;

/**
 * Created by fabio on 02/05/2016.
 */
public class BuyStadiumDialog extends DialogFragment
{
    private Stadium stadium;
    private PlayerController player;
    public BuyStadiumDialog()
    {

    }

    public void setData(Stadium stadium, PlayerController player)
    {
        this.stadium = stadium;
        this.player = player;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
       AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message;
        message = "Cost: " + stadium.getCost() +"\n";
        message += "Base Rent: " + stadium.getRent(0) + "\n";
        message += "Level 1 Rent: " + stadium.getRent(1) + "\n";
        message += "Level 2 Rent: " + stadium.getRent(2);
        builder.setTitle(stadium.getName());
        builder.setMessage(message)
                .setPositiveButton(R.string.dialogBuy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        player.buyStadium(stadium);
                        player.updateText();
                    }
                })
                .setNegativeButton(R.string.dialogNoBuy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
