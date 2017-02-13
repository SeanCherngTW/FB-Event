package DataType;

import DataType.DefineFreeText.DefineFreeTextType;
import Solr.ShopData;

public class FBShopActivityDescription extends ShopData {
    public FBShopActivityDescription(String id, int size, String title, int number,
                                     String tel, String cellphone, String mail, String category,
                                     String type, double score, String http, String address,
                                     double longitude, double latitude, String reference,
                                     String searchEngine, double distance, String description) {
        super(id, size, title, number, tel, cellphone, mail, category, type, score,
              http, address, longitude, latitude, reference, searchEngine, distance,
              description);
        // TODO Auto-generated constructor stub
    }

    private int num_likes;
    private int num_comments;
    //private String link;
    private DefineFreeTextType freeTextType;


    private String  contentMentionTimeJudgeStartDateVal;
    public String  contentMentionTimeJudgeStartDate;
    private String  contentMentionTimeJudgeEndDateVal;
    private String  contentMentionTimeJudgeEndDate;
    private String contentMentionActivityLOCAddressJudge;

    public String getStartDate()
    {
        String startDateStr = this.contentMentionTimeJudgeStartDate + "(" + this.contentMentionTimeJudgeStartDateVal+ ")";
        if(startDateStr.equals("()"))
            startDateStr = "";
        return startDateStr;
    }

    public String getEndDate()
    {
        String endDateStr = this.contentMentionTimeJudgeEndDate + "(" + this.contentMentionTimeJudgeEndDateVal+ ")";
        if(endDateStr.equals("()"))
            endDateStr = "";
        return endDateStr;
    }

    public String getLOC()
    {
        return this.contentMentionActivityLOCAddressJudge;
    }


    public void setContentMentionActivityLOCAddressJudge(String contentMentionActivityLOCAddressJudge)
    {
        this.contentMentionActivityLOCAddressJudge = contentMentionActivityLOCAddressJudge;
    }

    public String getContentMentionActivityLOCAddressJudge()
    {
        return contentMentionActivityLOCAddressJudge;
    }

    public void setContentMentionTimeJudgeStartDateVal(String contentMentionTimeJudgeStartDateVal)
    {
        this.contentMentionTimeJudgeStartDateVal = contentMentionTimeJudgeStartDateVal;
    }

    public String getContentMentionTimeJudgeStartDateVal()
    {
        return contentMentionTimeJudgeStartDateVal;
    }

    public void setContentMentionTimeJudgeStartDate(String contentMentionTimeJudgeStartDate)
    {
        this.contentMentionTimeJudgeStartDate = contentMentionTimeJudgeStartDate;
    }

    public String getContentMentionTimeJudgeStartDate()
    {
        return contentMentionTimeJudgeStartDate;
    }

    public void setContentMentionTimeJudgeEndDateVal(String contentMentionTimeJudgeEndDateVal)
    {
        this.contentMentionTimeJudgeEndDateVal = contentMentionTimeJudgeEndDateVal;
    }

    public String getContentMentionTimeJudgeEndDateVal()
    {
        return contentMentionTimeJudgeEndDateVal;
    }

    public void setContentMentionTimeJudgeEndDate(String contentMentionTimeJudgeEndDate)
    {
        this.contentMentionTimeJudgeEndDate = contentMentionTimeJudgeEndDate;
    }

    public String getContentMentionTimeJudgeEndDate()
    {
        return contentMentionTimeJudgeEndDate;
    }
}
