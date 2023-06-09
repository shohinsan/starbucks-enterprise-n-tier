/* (c) Copyright 2018 Paul Nguyen. All Rights Reserved */

package starbucks;

/** My Cards Screen */
public class MyCards extends Screen
{   
    Card card ;
    
    public MyCards()
    {
        card = Card.getNewInstance() ;
    }

    /**
     * Get Screen Display Contents
     * @return Screen Contents
     */
    public String display() {
        return card.getBalance() ;
    }

    /**
     * Touch (X,Y) Event
     * @param x X Coord
     * @param y Y Coord
     */
    public void touch(int x, int y) {
        AppController app = AppController.getInstance() ;
        if ( x == 3 && y == 3 ) {
            app.setScreen( AppController.SCREENS.MY_CARDS_PAY ) ;
        }   
        if ( x == 2 && y == 4 ) {
            app.setScreen( AppController.SCREENS.MY_CARDS_OPTIONS ) ;
        }           
    }

    /** Added Fall 2019 */
    @Override
    public boolean supportsLandscape() {
        return true ;
    }

}
