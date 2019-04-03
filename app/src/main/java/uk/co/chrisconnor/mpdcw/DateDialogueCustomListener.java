/**
 * Chris Connor
 * S1715477
 * cconno208@caledonian.ac.uk
 */
package uk.co.chrisconnor.mpdcw;


import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Custom listener to handle datepicker dialouge. Allows the same handled to return and set a value on a text obkect
 */
public class DateDialogueCustomListener implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "CustomDateDialogue";
    private EditText mEditText;

    public DateDialogueCustomListener(EditText textBox) {
        this.mEditText = textBox;
    }

    /**
     * When the dateDate event is fired, update the relevant TextView
     * @param view Picker
     * @param year Year
     * @param month Month
     * @param dayOfMonth Day of Month
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        String dateString = String.format("%d/%d/%d", dayOfMonth, month+1, year);
        mEditText.setText(dateString);

    }


}
