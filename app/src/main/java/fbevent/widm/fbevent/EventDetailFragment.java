package fbevent.widm.fbevent;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Sean on 2017/2/22.
 */

public class EventDetailFragment extends Fragment {

    private TextView mTitleTxt, mDescriptionTxt, mHttpTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.d("event", "event");

        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);

        Activity activity = getActivity();
        ((MapsActivity) activity).setOnBackPressedListener
                (new BaseBackPressedListener((android.support.v4.app.FragmentActivity) activity));

        String Title = getArguments().getString("Title");
        String Description = getArguments().getString("Description");
        String Http = getArguments().getString("Http");

        Log.d("eventFrg", Title);
        Log.d("eventFrg", Description);
        Log.d("eventFrg", Http);

        mTitleTxt = (TextView) getActivity().findViewById(R.id.titleTxt);
        mDescriptionTxt = (TextView) getActivity().findViewById(R.id.descriptionTxt);
        mHttpTxt = (TextView) getActivity().findViewById(R.id.httpTxt);

        mTitleTxt.setText(Title);
        //mDescriptionTxt.setText(Description);
        //mHttpTxt.setText(Http);

        Log.d("event", "event");

        return view;
    } // end onCreateView()

} // end EventDetailFragment
