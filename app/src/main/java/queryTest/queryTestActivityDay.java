package queryTest;

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

public class queryTestActivityDay {

    //static String address = "140.115.54.62";
    static String address = "powerpoi.widm.csie.ncu.edu.tw";
    static int port = 30;

    // parameter
    private static String GPS = "";// "lat,lng"
    private static String radius = "100";// /km
    private static String quantity = "10";// don't need to care
    private static int whichFucntion = 0;// don't need to care

    public static void query() {

        GPS = "24.9684297,121.1959266";

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowDate = new Date();

        String startDayStr = "2017-01-09";
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        // cal.add(Calendar.MONTH, 3);
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

//			System.out.println(obj.toString());
            SearchResultShopData searchResultShopData = (SearchResultShopData) obj;
            ArrayList<ShopData> a = (ArrayList<ShopData>) searchResultShopData.getShopDataList();
//			System.out.println("searchResultShopData.getStaut() : " + searchResultShopData.getStaut());
            if (searchResultShopData.getStaut() == 1) {
                for (int i = 0; i < a.size(); i++) {
                    if (a.get(i).getSearchEngine().equals(ShopData.SEARCH_ENGINE_ATTRIBUTE_FB_ACTIVITY_SOLR)) {
                        FBShopActivityDescription fbShopActivityDescription = (FBShopActivityDescription) a.get(i);
                        System.out.println("/////////////////////////start////////////////////////");
                        System.out.println("活動名稱:" + fbShopActivityDescription.getTitle());
                        System.out.println("活動發文連結:" + fbShopActivityDescription.getHttp());
                        System.out.println("GPS:" + fbShopActivityDescription.getLatitude() + "," + fbShopActivityDescription.getLongitude());
                        System.out.println();
                        System.out.println("*******************活動摘要資訊********************");
                        System.out.println("活動開始時間:" + fbShopActivityDescription.getStartDate());
                        System.out.println("活動結束時間:" + fbShopActivityDescription.getEndDate());
                        System.out.println("活動地點:" + fbShopActivityDescription.getAddress());
                        System.out.println("~~~~~~~~~~~~~~~~~~HighlightFBPost~~~~~~~~~~~~~~~~~~~~");
                        System.out.println(fbShopActivityDescription.getDescription());
                        System.out.println("////////////////////////end/////////////////////////");
                        System.out.println();
                    }
                }
            }
            out.flush();
            out.close();
            out = null;
            in.close();
            in = null;
            client.close();
            client = null;
        } catch (java.io.IOException e) {
            System.err.println("SocketClient 連線有問題 !");
            System.err.println("IOException :" + e.toString());
        } catch (ClassNotFoundException e) {
            System.err.println("ClassNotFoundException :" + e.toString());
        } catch (Exception e) {
            System.err.println("Exception :" + e.toString());
        }

    }

    public static void main(String[] args) {
        query();
    }
}
