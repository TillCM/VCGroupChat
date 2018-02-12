package com.example.ctill.vcgroupchat;

import java.util.Date;

/**
 * Created by ctill on 2018/02/12.
 */

public class Chat
{
    private String mName;
    private String mMessage;
    private long mTime;

    //Empty Constructor:

    private Chat(){}

    // overloaded constructor

    public  Chat(String mName, String mMessage, long mTime)
    {
       this. mName = mName;
       this.mMessage = mMessage;
       this.mTime = mTime;

        mTime = new Date().getTime();
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public long getmTime() {
        return mTime;
    }

    public void setmTime(long mTime) {
        this.mTime = mTime;
    }
}




