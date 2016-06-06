package com.mieicfeup.df.footpoly.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
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
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.buy_stadium_dialog_layout, null);
        builder.setView(view);

        ImageView colorBar = (ImageView) view.findViewById(R.id.colorBar);
        colorBar.setBackgroundColor(getStadiumColor());

        TextView stadiumName = (TextView) view.findViewById(R.id.stadiumName);
        stadiumName.setText(stadium.getName());

        TextView cost = (TextView) view.findViewById(R.id.cost);
        cost.setText("Cost: " + stadium.getCost());

        TextView baseRent = (TextView) view.findViewById(R.id.baseRent);
        baseRent.setText("Base Rent: " + stadium.getRent(0));

        TextView lvl1Rent = (TextView) view.findViewById(R.id.level1Rent);
        lvl1Rent.setText("Level 1 Rent: " + stadium.getRent(1));

        TextView lvl2Rent = (TextView) view.findViewById(R.id.level2Rent);
        lvl2Rent.setText("Level 2 Rent: " + stadium.getRent(2));


        builder.setPositiveButton(R.string.dialogBuy, new DialogInterface.OnClickListener() {
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

    public int getStadiumColor()
    {
        if(this.stadium.getCountry().equals("England"))
            return getResources().getColor(R.color.stadiumWhite);
        else if(this.stadium.getCountry().equals("Spain"))
            return getResources().getColor(R.color.stadiumRed);
        else if(this.stadium.getCountry().equals("Germany"))
            return getResources().getColor(R.color.stadiumBlack);
        else if(this.stadium.getCountry().equals("Italy"))
            return getResources().getColor(R.color.stadiumBlue);
        else
            return getResources().getColor(R.color.stadiumGreen);
    }
}
