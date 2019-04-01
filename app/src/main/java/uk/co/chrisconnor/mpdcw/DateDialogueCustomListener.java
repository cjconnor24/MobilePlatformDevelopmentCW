package uk.co.chrisconnor.mpdcw;


import android.app.DatePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

public class DateDialogueCustomListener implements DatePickerDialog.OnDateSetListener {

    private static final String TAG = "CustomDateDialogue";
    private EditText mEditText;
//    private Context mContext;

    public DateDialogueCustomListener(EditText textBox) {
//        this.mContext = c;
        this.mEditText = textBox;
        Log.d(TAG, "DateDialogueCustomListener: CALLED THE CONSTRUCTOR");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Log.d(TAG, "onDateSet: CALLED THE EVENT LISTENER ON DATE SET");
        String dateString = String.format("%d/%d/%d", dayOfMonth, month, year);
        mEditText.setText(dateString);

    }

    //    @Override
//    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth, EditText textBox) {
//
//        String dateString = String.format("%d/%d/%d",dayOfMonth,month,year);
//        textBox.setText(dateString);
//
//    }


}
