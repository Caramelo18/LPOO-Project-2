package com.mieicfeup.df.footpoly;

import android.util.Log;

import java.util.LinkedHashMap;

/**
 * Created by fabio on 01/05/2016.
 */
public class Table
{
    LinkedHashMap<Integer, Stadium> places = new LinkedHashMap();

    public Table()
    {
        places.put(1, new Stadium("Emirates Stadium", "England", 500, 251, 400, 700));
        places.put(2, new Stadium("White Hart Lane", "England", 500, 251, 400, 700));
        places.put(3, new Stadium("Old Trafford", "England", 500, 251, 400, 700));
        places.put(4, new Stadium("Anfield", "England", 500, 251, 400, 700));

        places.put(6, new Stadium("Camp Nou", "Spain", 500, 251, 400, 700));
        places.put(8, new Stadium("Santiago Bernabeu", "Spain", 500, 251, 400, 700));
        places.put(9, new Stadium("Vicente Calderon", "Spain", 500, 251, 400, 700));

        places.put(11, new Stadium("Signal Iduna Park", "Germany", 500, 251, 400, 700));
        places.put(12, new Stadium("Allianz Arena", "Germany", 500, 251, 400, 700));

        places.put(13, new Stadium("San Siro", "Italy", 500, 251, 400, 700));
        places.put(14, new Stadium("Juventus Stadium", "Italy", 500, 251, 400, 700));

        places.put(17, new Stadium("Estadio da Luz", "Portugal", 500, 251, 400, 700));
        places.put(18, new Stadium("Estadio Jose Alvalade", "Portugal", 500, 251, 400, 700));
        places.put(19, new Stadium("Estadio do Dragao", "Portugal", 500, 251, 400, 700));
    }

    public Stadium getStadium(int index)
    {
        return places.get(index);
    }

    public int getRent(int index)
    {
        int rent = places.get(index).getRent();
        Log.w("Rent", String.valueOf(rent));
        return rent;
    }
}
