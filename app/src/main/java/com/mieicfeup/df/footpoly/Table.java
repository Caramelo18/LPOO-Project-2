package com.mieicfeup.df.footpoly;

import android.util.Log;

/**
 * Created by fabio on 01/05/2016.
 */
public class Table
{
    Stadium[] places = new Stadium[20];

    public Table()
    {
        places[1] = new Stadium("Alvalade", "Portugal", 500, 251, 400, 700);
        places[2] = new Stadium("Luz", "Portugal", 500, 252, 400, 700);
        places[3] = new Stadium("Dragao", "Portugal", 500, 253, 400, 700);
        places[4] = new Stadium("Restelo", "Portugal", 500, 254, 400, 700);
        places[5] = new Stadium("Signal Iduna Park", "Alemanha", 500, 255, 400, 700);
        places[6] = new Stadium("White Hart Lane", "Inglaterra", 500, 256, 400, 700);
    }

    public Stadium getStadium(int index)
    {
        return places[index];
    }

    public int getRent(int index)
    {
        int rent = places[index].getRent();
        Log.w("Rent", String.valueOf(rent));
        return rent;
    }
}
