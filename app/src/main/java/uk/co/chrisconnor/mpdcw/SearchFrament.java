/**
 * Chris Connor
 * S1715477
 * cconno208@caledonian.ac.uk
 */
package uk.co.chrisconnor.mpdcw;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import uk.co.chrisconnor.mpdcw.DAO.EarthquakeDatabase;
import uk.co.chrisconnor.mpdcw.DAO.IEarthquakeTableSchema;
import uk.co.chrisconnor.mpdcw.models.Earthquake;

/**
 * Fragment handles searching the database
 */
public class SearchFrament extends Fragment implements View.OnFocusChangeListener {

    private static final String TAG = "SearchFrament";

    // SETUP VARIABLES
    private EditText mDatePickerFrom, mDatePickerTo, mDepth, mLocation;
    private Spinner mMagnitude, mSortOrder;
    private Button mSubmitButton, mClearButton;
    private RadioGroup mSortBy;

    // HANDLE EARTHQUAKE CLICKED
    private OnSearchFragmentInteractionListener mCallback;

    public SearchFrament() {
        // Required empty public constructor
    }


    /**
     * Get new instance of fragments
     * @return
     */
    public static SearchFrament newInstance() {
        SearchFrament fragment = new SearchFrament();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // GET THE VALUES FROM EACH OF THE FIELDS
        mDatePickerFrom = (EditText) view.findViewById(R.id.datePickerFrom);
        mDatePickerTo = (EditText) view.findViewById(R.id.datePickerTo);
        mMagnitude = (Spinner) view.findViewById(R.id.magnitude);
        mDepth = (EditText) view.findViewById(R.id.depth);
        mLocation = (EditText) view.findViewById(R.id.location);
        mSortOrder = (Spinner)view.findViewById(R.id.sortBy);
        mSubmitButton = (Button) view.findViewById(R.id.btnSearch);
        mClearButton = (Button) view.findViewById(R.id.btnClear);
        mSortBy = (RadioGroup)view.findViewById(R.id.sortOrder);

        // ADD LISTENERS
        mDatePickerFrom.setOnFocusChangeListener(this);
        mDatePickerTo.setOnFocusChangeListener(this);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearForm();
            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // VALIDATE VALUES
                String startDate = mDatePickerFrom.getText().toString();
                String endDate = mDatePickerTo.getText().toString();
                int magnitude = mMagnitude.getSelectedItemPosition();
                String depth = mDepth.getText().toString();
                String location = mLocation.getText().toString();
                int sortOrder = mSortOrder.getSelectedItemPosition();
                int sortById = mSortBy.getCheckedRadioButtonId();

                // NEW Placeholders
                Date sDate = null;
                Date eDate = null;
                Integer mag = null;
                Integer dep = null;
                String loc = null;
                Integer sort = null;
                String sortBy = null;

                // GETTING THE SORT ORDER
                if(sortById == R.id.sortASC){
                    sortBy = "ASC";
                } else {
                    sortBy = "DESC";
                }

                // PLACE HOLDERS FOR EQS
                List<Earthquake> eqs;
                ArrayList<String> errors = new ArrayList<>();

                // CHECK THE DATES
                String pattern = "dd/MM/yyyy";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                // CARRYOUT THE TRANSFORMATIONS
                try {

                    // START DATE
                    if (!startDate.equals("")) {
                        sDate = simpleDateFormat.parse(startDate);
                    }

                    // END DATE
                    if (!endDate.equals("")) {
                        eDate = simpleDateFormat.parse(endDate);
                    }

                    // MAGNITUDE
                    if (magnitude == 0) {
                        mag = null;
                    } else {
                        mag = magnitude;
                    }

                    // DEPTH
                    if (!depth.equals("")) {
                        dep = Integer.parseInt(depth);
                    }

                    // LOCATION
                    if (!location.equals("")) {
                        loc = location;
                    }

                    // SORTORDER
                    sort = sortOrder;

                } catch(ParseException e){
                    errors.add("The dates you entered are invalid");

                } catch(NumberFormatException e) {
                    errors.add("Please depth is a number");
                }

                if(errors.size() != 0){
                    Toast.makeText(getContext(), "Uh oh...errors", Toast.LENGTH_SHORT).show();
                } else {

                    eqs = EarthquakeDatabase.mEarthquakeDao.searchEarthquake(sDate,eDate,mag,dep,loc, sort, sortBy);

                    // RETURN THESE BACK TO MAIN ACTIVITY TO DISPLAY RESULTS
                    if(eqs.size() > 0){
                        mCallback.onSearchResultsReturned(eqs);
                    } else {
                        Toast.makeText(getContext(), "Sorry, no Earthquakes found", Toast.LENGTH_LONG).show();
                        mCallback.onSearchResultsReturned(null);
                    }

                }

            }
        });


    }

    /**
     * Clear the Form of values
     */
    private void clearForm() {

        mDatePickerFrom.setText("");
        mDatePickerTo.setText("");
        mMagnitude.setSelection(0, true);
        mDepth.setText("");
        mLocation.setText("");

        Toast.makeText(getContext(), "Search cleared", Toast.LENGTH_SHORT).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_frament, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchFragmentInteractionListener) {
            mCallback = (OnSearchFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    /**
     * Show the datedialogue when focused. Get the date 2 weeks ago
     * @param v
     * @param hasFocus
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (hasFocus) {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -14);
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH);
            int d = calendar.get(Calendar.DAY_OF_MONTH);

            if (v.getId() == R.id.datePickerFrom || v.getId() == R.id.datePickerTo) {

                DatePickerDialog datePicker = new DatePickerDialog(getContext(), R.style.DialogTheme, new DateDialogueCustomListener((EditText) v), y, m, d);

                // SET MINIMUM AND MAXIMUM DATES
                long DAY_IN_MS = 1000 * 60 * 60 * 24;
                datePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - (100 * DAY_IN_MS));

                // SHOW THE DIALOGUE
                datePicker.show();
            }

        }


    }


    /**
     * Interface to make sure event is handled when results are returned
     */
    public interface OnSearchFragmentInteractionListener {
        // TODO: Update argument type and name
        void onSearchResultsReturned(List<Earthquake> earthquakes);
    }
}
