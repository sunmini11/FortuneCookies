package com.egco428.a13285;

/**
 * Created by dell pc on 31/10/2559.
 */
public class FortuneResults {
    private String message;
    private String timestamp;
    private String imgname;

    private long id;
    public long getId(){return id;}
    public void setId(long id){this.id = id;}

    public FortuneResults(long id, String imgname,String message,String timestamp){
        this.id = id;
        this.message = message;
        this.timestamp = timestamp;
        this.imgname = imgname;
    }

    public String getMessage(){return message;}
    public String getTimestamp(){return timestamp;}
    public String getImgname(){return imgname;}
}
