package fbevent.widm.fbevent;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import DataType.FBShopActivityDescription;
import Solr.SearchResultShopData;
import Solr.ShopData;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    protected OnBackPressedListener onBackPressedListener;
    private Toolbar toolbar;

    static String address = "powerpoi.widm.csie.ncu.edu.tw";
    static int port = 30;

    // parameter
    private ArrayList<ShopData> shopDataList;
    private String ResultStr="";
    private int isSuccess = 0;
    private String GPS = "";// "lat,lng"
    private String radius = "1";// /km
    private String quantity = "10";// don't need to care
    private int whichFucntion = 0;// don't need to care

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Set an OnMenuItemClickListener to handle menu item clicks
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remove the searchResultFragment and back to the map
                FragmentManager manager = getFragmentManager();
                SearchResultFragment searchResultFragment =
                        (SearchResultFragment) manager.findFragmentByTag("searchResultFragment");

                if(searchResultFragment != null){
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.remove(searchResultFragment);
                    transaction.commit();
                } // end if
            } // end onClick
        }); // end View.OnClickListener()

        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    } // end onCreate()

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    } // end setOnBackPressedListener()

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null) {
            onBackPressedListener.doBack();
            Log.d("back","doBack");
        }
        else {
            super.onBackPressed();
            Log.d("back","else");
        } // end if/else
    } // end onBackPressed();

    private void getSelectedMarker(){
        /*try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            double latitude = bundle.getDouble("Latitude");
            double longitude = bundle.getDouble("Longitude");
            mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude)));
        }catch (NullPointerException ex){
            ex.printStackTrace();
        }*/
    } // end getSelectedMarker()

    private void query() {

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
        } // end try/catch
    } // end query()

    private void addToMap(){
        for (ShopData sd : shopDataList)
            mMap.addMarker(new MarkerOptions()
                             .position(new LatLng(sd.getLatitude(),sd.getLongitude()))
                             .title(sd.getTitle()));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(12.0f));
    } // end addToMap()

    private void sendEventInfo(){
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SearchResultFragment searchResultFragment = new SearchResultFragment();

        // step 3. Send ResultStr to SearchResultFragment
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("shopDataList", shopDataList);

        Log.d("size",String.valueOf(shopDataList.size()));

        transaction.replace(R.id.searchFragment, searchResultFragment, "searchResultFragment");
        searchResultFragment.setArguments(bundle);
        transaction.commit();
    } // end sendEventInfo()

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng NCU = new LatLng(24.9684297,121.1959266);
        mMap.addMarker(new MarkerOptions().position(NCU).title("Marker in NCU"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(NCU,16.0f));
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
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String s) {
                Thread thread = new Thread(){
                    public void run(){
                        query();
                        sendEventInfo();
                    }
                };
                thread.start();
                try {
                    thread.join();
                    addToMap();
                }catch (Exception ex){
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
} // end MapsActivity
