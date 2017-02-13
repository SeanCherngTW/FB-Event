package fbevent.widm.fbevent;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;

/**
 * Created by Sean on 2017/2/11.
 */

public class BaseBackPressedListener implements OnBackPressedListener {
    private final FragmentActivity mapsActivity;

    public BaseBackPressedListener(FragmentActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    @Override
    public void doBack() {
        mapsActivity.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
}