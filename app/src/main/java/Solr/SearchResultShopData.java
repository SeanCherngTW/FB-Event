package Solr;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchResultShopData implements Serializable{
    private int staut; // 1  成功 0失敗
    ArrayList<ShopData> shopDataList;

    public SearchResultShopData()
    {
        shopDataList = new ArrayList<ShopData>();
    }

    public void setStaut(int staut)
    {
        this.staut = staut;
    }

    public void setShopDataList(ArrayList<ShopData> shopDataList)
    {
        this.shopDataList = shopDataList;
    }

    public ArrayList<ShopData>  getShopDataList()
    {
        return this.shopDataList;
    }

    public int getStaut()
    {
        return this.staut;
    }
}
