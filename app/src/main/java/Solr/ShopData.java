package Solr;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/*[note]
 *
 * QuerySolr.java
 * String reference = "";


ShopData shop = new ShopData(id, size, title, number, tel,
						cellphone, mail, category, type, score, http, address,
						longitude, latitude, reference, "S",
						Double.parseDouble(df.format(distance)),description);


QueryGooglePlaceAPI.java

ShopData shop = new ShopData(-1, size, title, number, tel,
						cellphone, mail, category, type, score, http, address,
						longitude, latitude, reference, "G",
						Double.parseDouble(df.format(distance)), description);// -1就是id

SQLserver.java
						ShopData shop = new ShopData(-1, -1, title, -1, tel,
							cellphone, mail, category, type, -1, url, address,
							dlng, dlat, "", "O", Double.parseDouble(df
									.format(distance)), description);

private final static String SEARCH_ENGINE_FB_SOLR = "FBS";
private final static String SEARCH_ENGINE_TING_SOLR = "S";
private final static String SEARCH_ENGINE_GOOGLE = "G";
 */
public class ShopData implements Serializable, Parcelable {
    static final long serialVersionUID = -4037732610028027214L;
    public final static String SEARCH_ENGINE_ATTRIBUTE_FB_ACTIVITY_SOLR = "FBAS";
    public final static String SEARCH_ENGINE_ATTRIBUTE_FB_SOLR = "FBS";
    public final static String SEARCH_ENGINE_ATTRIBUTE_TING_SOLR = "S";
    public final static String SEARCH_ENGINE_ATTRIBUTE_GOOGLE = "G";
    public final static String SEARCH_ENGINE_ATTRIBUTE_GOOGLE_ONLINE = "O";
    //QuerySolr
    // solr的欄位
    private int size = 0;
    //private int id = 0;
    private String id = "";
    private String title = "";
    private int number = 0;
    private String tel = "";
    private String cellphone = "";
    private String mail = "";
    private String category = "";
    private String type = "";
    private double score = -1;
    private String http = "";
    private String address = "";
    private double longitude = 0;// 經度
    private double latitude = 0;// 緯度
    private String reference = "";
    private String searchEngine = "";// 在solr找到或在google找到
    private double distance = 0;
    private String description = "";

    private int validStaut = 1; //<!-- 0失效 1 有效 !--> 之前討論要求新增的
    private Date updateRecordDate;//之前討論要求新增的
    private List<String> imgs;
    private List<String> imgs_information;
    private String business_hour = "";

    //非solr 欄位
    private String img = ""; //疾疾店家 顯示的店家大頭貼 一張版

    protected ShopData(Parcel in) {
        size = in.readInt();
        id = in.readString();
        title = in.readString();
        number = in.readInt();
        tel = in.readString();
        cellphone = in.readString();
        mail = in.readString();
        category = in.readString();
        type = in.readString();
        score = in.readDouble();
        http = in.readString();
        address = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        reference = in.readString();
        searchEngine = in.readString();
        distance = in.readDouble();
        description = in.readString();
        validStaut = in.readInt();
        imgs = in.createStringArrayList();
        imgs_information = in.createStringArrayList();
        business_hour = in.readString();
        img = in.readString();
    }

    public int getSize() {
        return size;
    }

    //	public ShopData(int id, int size, String title, int number, String tel,
//			String cellphone, String mail, String category, String type,
//			double score, String http, String address, double longitude,
//			double latitude, String reference, String searchEngine,
//			double distance, String description)
    public ShopData(String id, int size, String title, int number, String tel,
                    String cellphone, String mail, String category, String type,
                    double score, String http, String address, double longitude,
                    double latitude, String reference, String searchEngine,
                    double distance, String description) {
        super();
        this.id = id;
        this.size = size;
        this.title = title;
        this.number = number;
        this.tel = tel;
        this.cellphone = cellphone;
        this.mail = mail;
        this.category = category;
        this.type = type;
        this.score = score;
        this.http = http;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.searchEngine = searchEngine;
        this.reference = reference;
        this.distance = distance;
        this.description = description;
    }

    //	public void setId(int id)
//	{
//		this.id = id;
//	}
//
//	public int getId()
//	{
//		return id;
//	}
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCateory() {
        return category;
    }

    public void setCateory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getHttp() {
        return http;
    }

    public void setHttp(String http) {
        this.http = http;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getSearchEngine() {
        return searchEngine;
    }

    public void setSearchEngine(String type) {
        this.searchEngine = type;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String _img) {
        this.img = _img;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public List<String> getImgs_information() {
        return imgs_information;
    }

    public void setImgs_information(List<String> imgs_information) {
        this.imgs = imgs_information;
    }

    public String getBusiness_hour() {
        return business_hour;
    }

    public void setBusiness_hour(String business_hour) {
        this.business_hour = business_hour;
    }

    public void setValidStaut(int validStaut) {
        this.validStaut = validStaut;
    }

    public int getValidStaut() {
        return validStaut;
    }

    public void setUpdateRecordDate(Date updateRecordDate) {
        this.updateRecordDate = updateRecordDate;
    }

    public Date getUpdateRecordDate() {
        return updateRecordDate;
    }


    public static final Creator<ShopData> CREATOR = new Creator<ShopData>() {
        @Override
        public ShopData createFromParcel(Parcel in) {
            return new ShopData(in);
        }

        @Override
        public ShopData[] newArray(int size) {
            return new ShopData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(size);
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeInt(number);
        parcel.writeString(tel);
        parcel.writeString(cellphone);
        parcel.writeString(mail);
        parcel.writeString(category);
        parcel.writeString(type);
        parcel.writeDouble(score);
        parcel.writeString(http);
        parcel.writeString(address);
        parcel.writeDouble(longitude);
        parcel.writeDouble(latitude);
        parcel.writeString(reference);
        parcel.writeString(searchEngine);
        parcel.writeDouble(distance);
        parcel.writeString(description);
        parcel.writeInt(validStaut);
        parcel.writeStringList(imgs);
        parcel.writeStringList(imgs_information);
        parcel.writeString(business_hour);
        parcel.writeString(img);
    }
}
