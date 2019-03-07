package uk.co.chrisconnor.mpdcw.ui.earthquakedetail;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.chrisconnor.mpdcw.R;

public class EarthquakeDetailFragment extends Fragment {

    private EarthquakeDetailViewModel mViewModel;

    public static EarthquakeDetailFragment newInstance() {
        return new EarthquakeDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.earthquake_detail_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EarthquakeDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}
