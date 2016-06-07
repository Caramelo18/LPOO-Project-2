package com.mieicfeup.df.footpoly.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.mieicfeup.df.footpoly.R;
import com.mieicfeup.df.footpoly.controller.PlayerController;
import com.mieicfeup.df.footpoly.model.Stadium;

/**
 * Created by fabio on 02/05/2016.
 */
public class BuyStadiumDialog extends DialogFragment
{
    private Stadium stadium;
    private PlayerController player;
    private Dialog dialog;
    private AlertDialog.Builder builder;
    private Context context;

    public BuyStadiumDialog()
    {

    }

    public void setContext(Context context)
    {
        this.context = context;
    }

    public void setData(Stadium stadium, PlayerController player)
    {
        this.stadium = stadium;
        this.player = player;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        builder = new AlertDialog.Builder(getActivity());
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
                        if (!player.buyStadium(stadium))
                        notEnoughMoney();
                    else
                        player.updateText();
                    }
                })
                .setNegativeButton(R.string.dialogNoBuy, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private int getStadiumColor()
    {
        switch (this.stadium.getCountry()) {
            case "England":
                return getResources().getColor(R.color.stadiumWhite);
            case "Spain":
                return getResources().getColor(R.color.stadiumRed);
            case "Germany":
                return getResources().getColor(R.color.stadiumBlack);
            case "Italy":
                return getResources().getColor(R.color.stadiumBlue);
            default:
                return getResources().getColor(R.color.stadiumGreen);
        }
    }

    public Stadium getStadium() {
        return stadium;
    }

    private void notEnoughMoney() {
        dialog.dismiss();
        builder = new AlertDialog.Builder(context);

        builder.setMessage("You don't have enough money to buy this Stadium.")
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        dialog = builder.create();
        dialog.show();
    }
}
