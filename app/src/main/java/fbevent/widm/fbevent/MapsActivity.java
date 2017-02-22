package fbevent.widm.fbevent;

import android.app.FragmentManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import Solr.SearchResultShopData;
import Solr.ShopData;

import static android.R.attr.width;
import static fbevent.widm.fbevent.R.attr.height;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    protected OnBackPressedListener onBackPressedListener;
    private Toolbar toolbar;
    private FragmentManager manager;
    private SearchResultFragment searchResultFragment;
    private EventDetailFragment eventDetailFragment;
    private SupportMapFragment mapFragment;
    private static final String SEARCH_RESULT_TAG = "searchResultFragment";
    private static final String EVENT_DETAIL_TAG = "eventDetailFragment";

    static final String address = "powerpoi.widm.csie.ncu.edu.tw";
    static final int port = 30;

    // parameter
    private ArrayList<ShopData> shopDataList;
    private ArrayList<ShopData> filertedShopDataList;
    private String ResultStr = "";
    private int isSuccess = 0;
    private String GPS = "";// "lat,lng"
    private String radius = "1";// /km
    private String quantity = "10";// don't need to care
    private int whichFucntion = 0;// don't need to care

    /*******************************
     * Layout and Map
     ******************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        manager = getFragmentManager();
        searchResultFragment = new SearchResultFragment();
        eventDetailFragment = new EventDetailFragment();

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // go back to previous page
                getFragmentManager().popBackStack(null, android.app.FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } // end onClick
        }); // end View.OnClickListener()

        setSupportActionBar(toolbar);

        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    } // end onCreate()

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(24.9684297, 121.1959266), 16.0f));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                createEventDetailFragment(marker.getTitle());
                return false;
            } // end onMarkerClick()
        }); // end setOnMarkerClickListener()
    } // end onMapReady()

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem menuSearchItem = menu.findItem(R.id.my_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView =
                (android.support.v7.widget.SearchView) menuSearchItem.getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                final String Query = s;
                Thread thread = new Thread() {
                    public void run() {
                        query(Query);
                        createSearchResultFragment();
                    } // end run()
                }; // end thread
                thread.start();
                try {
                    thread.join();
                    addMarkerToMap();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } // end try/catch
                return true;
            } // end onQueryTextSubmit()

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        }); // end setOnQueryTextListener()
        return true;
    } // end onCreateOptionsMenu()

    /******************************* Layout and Map ******************************/

    /*******************************
     * Get Events
     ******************************/

    private void query(String s) {

        GPS = "24.9684297,121.1959266";

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();

        Calendar cal = Calendar.getInstance();
        String startDayStr = sdFormat.format(cal.getTime());
        cal.setTime(nowDate);
        cal.add(Calendar.MONTH, 1);
        String endDayStr = sdFormat.format(cal.getTime());

        String query = String.format("▽活動搜尋▼▽start_day▼%s▽end_day▼%s", startDayStr, endDayStr);

        System.out.println("伺服器狀態測試查詢... " + query + " " + GPS + " " + radius);
        Socket client = new Socket();
        InetSocketAddress isa = new InetSocketAddress(address, port);
        try {
            client.connect(isa, 10000);

            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            out.writeUTF(query + " " + GPS + " " + radius + " " + whichFucntion + " " + "test " + quantity);
            client.shutdownOutput();
            ObjectInputStream in = new ObjectInputStream(client.getInputStream());
            Object obj = in.readObject();

            SearchResultShopData searchResultShopData = (SearchResultShopData) obj;
            isSuccess = searchResultShopData.getStaut();
            shopDataList = searchResultShopData.getShopDataList();
            filertedShopDataList = new ArrayList<>();

            if (s.equals("test"))
                filertedShopDataList.addAll(shopDataList);
            else
                for (ShopData sd : shopDataList)
                    if (sd.getTitle().contains(s))
                        filertedShopDataList.add(sd);

            out.flush();
            out.close();
            in.close();
            client.close();
        } catch (java.io.IOException e) {
            System.err.println("SocketClient 連線有問題 !");
            System.err.println("IOException :" + e.toString());
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException :" + e.toString());
        } catch (Exception e) {
            System.err.println("Exception :" + e.toString());
        }// end try/catch
    } // end query()

    private void addMarkerToMap() {
        for (ShopData sd : filertedShopDataList)
            mMap.addMarker(new MarkerOptions()
                                   .position(new LatLng(sd.getLatitude(), sd.getLongitude()))
                                   .title(sd.getTitle()));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f));
    } // end addToMap()

    private void createSearchResultFragment() {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("shopDataList", filertedShopDataList);

        // check whether searchResultFragment is exist
        if (searchResultFragment.getArguments() == null)
            searchResultFragment.setArguments(bundle);
        else
            searchResultFragment.getArguments().putAll(bundle);

        // check whether searchResultFragment is exist
        //if (searchResultFragment != null && searchResultFragment instanceof SearchResultFragment)
        manager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left,
                                     R.animator.slide_out_right, 0, 0)
                .replace(R.id.searchFragment, searchResultFragment, SEARCH_RESULT_TAG)
                .addToBackStack(SEARCH_RESULT_TAG)
                .commit();
    } // end sendEventInfo()

    private void createEventDetailFragment(String EventTitle) {

        for (ShopData sd : filertedShopDataList)
            if (sd.getTitle().equals(EventTitle)) {
                String Title = sd.getTitle();
                String Description = sd.getDescription();
                String Http = sd.getHttp();

                Bundle bundle = new Bundle();
                bundle.putString("Title", Title);
                bundle.putString("Description", Description);
                bundle.putString("Http", Http);

                // check whether eventDetailFragment is exist
                //if (eventDetailFragment.getArguments() == null)
                eventDetailFragment.setArguments(bundle);
                //else
                //   eventDetailFragment.getArguments().putAll(bundle);

                // check whether eventDetailFragment is exist
                //if (eventDetailFragment != null && eventDetailFragment instanceof EventDetailFragment)
                manager.beginTransaction()
                        .replace(R.id.eventDetailFragment, eventDetailFragment, EVENT_DETAIL_TAG)
                        .addToBackStack(EVENT_DETAIL_TAG)
                        .commit();
                break;
            } // end if

    } // end createEventDetailFragment()

    @Override
    protected void onNewIntent(Intent intent) {
        // Get selected event information intent sent from searchResultFragment
        Bundle bundle = intent.getExtras();
        double latitude = bundle.getDouble("Latitude");
        double longitude = bundle.getDouble("Longitude");
        String Title = bundle.getString("Title");
        LatLng selectedLoc = new LatLng(longitude, latitude);
        mMap.addMarker
                (new MarkerOptions()
                         .position(selectedLoc)
                         .title(Title));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(selectedLoc, 16.0f));
    } // end onNewIntent()

    /****************************** Get Events ******************************/

} // end MapsActivity
