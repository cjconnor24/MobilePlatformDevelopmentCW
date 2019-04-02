package uk.co.chrisconnor.mpdcw.helpers;

import android.graphics.Color;

/**
 * Helper class to colour magnitudes in severity
 */
public class ColorHelper {

    /**
     * Takes a magnitude and returns a colour
     * @param magnitude
     * @return colour enum for setting drawable colour
     */
    public static int getColor(double magnitude){

        int color;
        Double m = Math.abs(magnitude);
        int mag = m.intValue();

        switch(mag){
            case 0:
                color = Color.parseColor("#99FF99");
                break;
            case 1:
                color = Color.parseColor("#BBFF77");
                break;
            case 2:
                color = Color.parseColor("#DDFF55");
                break;
            case 3:
                color = Color.parseColor("#FFFF33");
                break;
            case 4:
                color = Color.parseColor("#FBD33D");
                break;
            case 5:
                color = Color.parseColor("#F8A746");
                break;
            case 6:
                color = Color.parseColor("#F47B50");
                break;
            case 7:
                color = Color.parseColor("#E35F44");
                break;
            case 8:
                color = Color.parseColor("#D24339");
                break;
            case 9:
                color = Color.parseColor("#C1272D");
                break;
            default:
                color = Color.parseColor("#99FF99");
                break;
        }

        return color;

    }

    /**
     * Get HSV float value based on severity of earthqyake
     * @param magnitude Magnitude of earthquake
     * @return float hue representation
     */
    public static float getHue(double magnitude){

        float color;
        Double m = Math.abs(magnitude);
        int mag = m.intValue();

        if(mag >= 0 && mag < 3){
        // GREEN - LOW
            return 120;
        } else

        // YELLOW - MEDIUM LOW
        if(mag >= 3 && mag < 6){
            return 60;
        } else

        // ORANGE - MEDIUM
        if(mag >= 6 && mag < 8){
            return 30;
        } else {
            return 0;
        }

    }
}
