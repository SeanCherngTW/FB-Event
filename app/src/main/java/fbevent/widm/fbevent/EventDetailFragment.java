package fbevent.widm.fbevent;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Sean on 2017/2/22.
 */

public class EventDetailFragment extends Fragment implements View.OnKeyListener{

    private TextView mTitleTxt, mDescriptionTxt, mHttpTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*Activity activity = getActivity();
        ((MapsActivity) activity).setOnBackPressedListener
                (new BaseBackPressedListener((android.support.v4.app.FragmentActivity) activity));*/

        return inflater.inflate(R.layout.fragment_event_detail, container, false);
    } // end onCreateView()

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        String Title = getArguments().getString("Title");
        String Description = getArguments().getString("Description");
        String Http = getArguments().getString("Http");

        mTitleTxt = (TextView) getActivity().findViewById(R.id.titleTxt);
        mDescriptionTxt = (TextView) getActivity().findViewById(R.id.descriptionTxt);
        mHttpTxt = (TextView) getActivity().findViewById(R.id.httpTxt);

        mTitleTxt.setText(Title);
        mDescriptionTxt.setText(Description);
        mHttpTxt.setText(Http);

        Log.d("eventFrg", mTitleTxt.getText().toString());
        Log.d("eventFrg", mDescriptionTxt.getText().toString());
        Log.d("eventFrg", mHttpTxt.getText().toString());

    } // end onActivityCreated()

    @Override
    public void onPause(){
        super.onPause();
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();
    } // end onPause()

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if( i == KeyEvent.KEYCODE_BACK ) {
            getActivity().getFragmentManager().beginTransaction().remove(this).commit();
            getFragmentManager().popBackStack(null, android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
            return true;
        } // end if
        return false;
    } // end onKey()
} // end EventDetailFragment
