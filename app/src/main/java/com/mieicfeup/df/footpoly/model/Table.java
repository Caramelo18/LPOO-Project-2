package com.mieicfeup.df.footpoly.model;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by fabio on 01/05/2016.
 */
public class Table implements Serializable {
    LinkedHashMap<Integer, Place> places = new LinkedHashMap();
    FreeParking freeParking;
    Jail jail;
    Luck luck;

    public Table()
    {
        freeParking = new FreeParking();
        jail = new Jail();
        luck = new Luck(freeParking);

        places.put(0, new StartingPoint());
        places.put(1, new Stadium("Emirates Stadium", "England", 500, 251));
        places.put(2, new Stadium("White Hart Lane", "England", 500, 251));
        places.put(3, new Stadium("Old Trafford", "England", 500, 251));
        places.put(4, new Stadium("Anfield", "England", 500, 251));

        places.put(5, jail);

        places.put(6, new Stadium("Camp Nou", "Spain", 500, 251));
        places.put(7, luck);
        places.put(8, new Stadium("Santiago Bernabeu", "Spain", 500, 251));
        places.put(9, new Stadium("Vicente Calderon", "Spain", 500, 251));

        places.put(10, freeParking);

        places.put(11, new Stadium("Signal Iduna Park", "Germany", 500, 251));
        places.put(12, new Stadium("Allianz Arena", "Germany", 500, 251));

        places.put(13, new Stadium("San Siro", "Italy", 500, 251));
        places.put(14, new Stadium("Juventus Stadium", "Italy", 500, 251));

        places.put(15, new GoToJail(jail));

        places.put(16, luck);

        places.put(17, new Stadium("Estadio da Luz", "Portugal", 500, 251));
        places.put(18, new Stadium("Estadio Jose Alvalade", "Portugal", 500, 251));
        places.put(19, new Stadium("Estadio do Dragao", "Portugal", 500, 251));
    }

    public Place getPlace(int index)
    {
        return places.get(index);
    }

    public boolean playerAtJail(Player player)
    {
        return jail.atJail(player);
    }


}
