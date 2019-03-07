package uk.co.chrisconnor.mpdcw;

import org.junit.Test;

import uk.co.chrisconnor.mpdcw.models.Location;

import static org.junit.Assert.*;

public class LocationTest {

    @Test
    public void testParser(){
        Location x = new Location("Barnard Castle, Durhum", "53.080,-2.112 ;");
        Location y = new Location("Barnard Castle, Durhum", 53.080, -2.112);
        assertEquals(y.toString(),x.toString());
    }

}