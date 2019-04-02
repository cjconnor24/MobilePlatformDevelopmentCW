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


public class SearchFrament extends Fragment implements View.OnFocusChangeListener {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    private static final String TAG = "SearchFrament";

    private EditText mDatePickerFrom, mDatePickerTo, mDepth, mLocation;
    private Spinner mMagnitude;
    private Button mSubmitButton, mClearButton;


//    private OnFragmentInteractionListener mListener;

    public SearchFrament() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFrament.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFrament newInstance(String param1, String param2) {
        SearchFrament fragment = new SearchFrament();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mDatePickerFrom = (EditText) view.findViewById(R.id.datePickerFrom);
        mDatePickerTo = (EditText) view.findViewById(R.id.datePickerTo);
        mMagnitude = (Spinner) view.findViewById(R.id.magnitude);
        mDepth = (EditText) view.findViewById(R.id.depth);
        mLocation = (EditText) view.findViewById(R.id.location);
        mSubmitButton = (Button) view.findViewById(R.id.btnSearch);
        mClearButton = (Button) view.findViewById(R.id.btnClear);

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

                // NEW Placeholders
                Date sDate = null;
                Date eDate = null;
                Integer mag = null;
                Integer dep = null;
                String loc = null;

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

                } catch(ParseException e){
                    errors.add("The dates you entered are invalid");

                } catch(NumberFormatException e) {
                    errors.add("Please depth is a number");
                }

                if(errors.size() != 0){
                    Toast.makeText(getContext(), "Uh oh...errors", Toast.LENGTH_SHORT).show();
                } else {
                    eqs = EarthquakeDatabase.mEarthquakeDao.searchEarthquake(sDate,eDate,mag,dep,loc);
                    Toast.makeText(getContext(), "There are" + eqs.size(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "RETURNED:" + eqs.size() + " earthquakes - " + eqs.toString());
                }





                // MAKE SURE DATES FALL WITHIN RANGE
            }
        });


    }

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_frament, container, false);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

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

    public void onSubmitForm() {


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
