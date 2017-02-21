package fbevent.widm.fbevent;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import DataType.FBShopActivityDescription;
import Solr.ShopData;

/**
 * Created by Sean on 2017/2/7.
 */

public class SearchResultFragment extends Fragment {

    private ListView mEventListView;
    private ArrayList<ShopData> shopDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_search_result, container, false);

        Activity activity = getActivity();
        ((MapsActivity)activity).setOnBackPressedListener
                (new BaseBackPressedListener((android.support.v4.app.FragmentActivity)activity));

        // step 4. To receive in fragment in Fragment onCreateView method
        shopDataList = getArguments().getParcelableArrayList("shopDataList");

        mEventListView = (ListView) view.findViewById(R.id.eventList);
        mEventListView.setAdapter(new MemberAdapter(getActivity()));
        mEventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sendSelectedLoc((EventMember) adapterView.getItemAtPosition(i));
                hideFragment();
            } // end onItemClick()
        }); // end setOnItemClickListener()
        return view;
    } // end onCreateView()

    private void sendSelectedLoc(EventMember em){
        Intent intent = new Intent(getActivity().getBaseContext(), MapsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Bundle bundle = new Bundle();
        bundle.putDouble("Latitude", em.getLatitude());
        bundle.putDouble("Longitude", em.getLongitude());
        bundle.putString("Title", em.getTitle());
        bundle.putString("Description", em.getDescription());
        bundle.putString("Http", em.getHttp());
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    } // end sendSelectedLoc()

    private void hideFragment(){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(this);
        transaction.commit();
    } // end hideFragment()

    private class MemberAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;
        private List<EventMember> eventMemberList;

        public MemberAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
            eventMemberList = new ArrayList<>();

            for (int i = 0; i < shopDataList.size(); i++) {
                //if (shopDataList.get(i).getSearchEngine().equals(ShopData.SEARCH_ENGINE_ATTRIBUTE_FB_ACTIVITY_SOLR)) {
                FBShopActivityDescription event = (FBShopActivityDescription) shopDataList.get(i);
                eventMemberList.add
                        (new EventMember(event.getTitle(), event.getStartDate(),
                                         event.getAddress(), event.getHttp(),
                                         event.getLatitude(),event.getLongitude(),
                                         event.getDescription()));
            } // end for
        } // end MemberAdapter()

        @Override
        public int getCount() {
            return eventMemberList.size();
        }

        @Override
        public Object getItem(int i) {
            return eventMemberList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null)
                view = layoutInflater.inflate(R.layout.event_list_view_item, viewGroup, false);

            EventMember em = eventMemberList.get(i);
            TextView mTitleTxt = (TextView) view.findViewById(R.id.titleTxt);
            TextView mTimeTxt = (TextView) view.findViewById(R.id.timeTxt);
            TextView mAddressTxt = (TextView) view.findViewById(R.id.addressTxt);

            mTitleTxt.setText(em.getTitle());
            mTimeTxt.setText(em.getTime());
            mAddressTxt.setText(em.getAddress());

            return view;
        } // end getView()
    } // end MemberAdapter
} // end SearchResultFragment
