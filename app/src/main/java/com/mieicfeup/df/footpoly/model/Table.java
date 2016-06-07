package com.mieicfeup.df.footpoly.model;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Created by fabio on 01/05/2016.
 */
public class Table implements Serializable {
    private final LinkedHashMap<Integer, Place> places = new LinkedHashMap();
    private final Jail jail;
    private final Luck luck;

    /**
     * Builds a table
     */
    public Table()
    {
        FreeParking freeParking = new FreeParking();
        jail = new Jail();
        luck = new Luck(freeParking);

        places.put(0, new StartingPoint());
        places.put(1, new Stadium("Emirates Stadium", "England", 450, 300));
        places.put(2, new Stadium("White Hart Lane", "England", 400, 250));
        places.put(3, new Stadium("Old Trafford", "England", 500, 325));
        places.put(4, new Stadium("Anfield", "England", 400, 250));

        places.put(5, jail);

        places.put(6, new Stadium("Camp Nou", "Spain", 550, 350));
        places.put(7, luck);
        places.put(8, new Stadium("Santiago Bernabeu", "Spain", 550, 350));
        places.put(9, new Stadium("Vicente Calderon", "Spain", 500, 325));

        places.put(10, freeParking);

        places.put(11, new Stadium("Signal Iduna Park", "Germany", 650, 425));
        places.put(12, new Stadium("Allianz Arena", "Germany", 650, 425));

        places.put(13, new Stadium("Juventus Stadium", "Italy", 600, 400));
        places.put(14, new Stadium("San Siro", "Italy", 600, 400));

        places.put(15, new GoToJail(jail));

        places.put(16, luck);

        places.put(17, new Stadium("Estadio da Luz", "Portugal", 750, 500));
        places.put(18, new Stadium("Estadio Jose Alvalade", "Portugal", 700, 450));
        places.put(19, new Stadium("Estadio do Dragao", "Portugal", 700, 450));
    }

    /**
     * @return returns table size
     */
    public int getPlaceSize()
    {
        return places.size();
    }

    /**
     * @return Jail place
     */
    public Jail getJail()
    {
        return this.jail;
    }

    /**
     * @return Luck place
     */
    public Luck getLuck()
    {
        return this.luck;
    }

    /**
     * @param index given index
     * @return Place at a given index
     */
    public Place getPlace(int index)
    {
        return places.get(index);
    }

    /**
     * @param player player to check at jail
     * @return true if player is at jail, false otherwise
     */
    public boolean playerAtJail(Player player)
    {
        return jail.atJail(player);
    }


}
