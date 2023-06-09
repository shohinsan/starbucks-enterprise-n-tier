/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/** Rewards Screen */
public class Rewards extends Screen
{

    public Rewards()
    {

    }

    /**
     * Return Rewards Screen Contents
     * @return Rewards Screen Contents
     */
    public String display() {
        return "Make Every\nVisit Count" ;
    }

    /**
     * Ignore Touch Events
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y) {
       System.err.println( "X: " + x + " Y: " + y ) ;
    }

}
