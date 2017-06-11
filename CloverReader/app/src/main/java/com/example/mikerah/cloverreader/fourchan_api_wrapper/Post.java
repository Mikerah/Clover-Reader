package com.example.mikerah.cloverreader.fourchan_api_wrapper;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.nio.charset.IllegalCharsetNameException;

/**
 * Created by Mikerah on 5/1/2017.
 */

public class Post {
    private int mPostId;
    private String mPosterName;
    private String mPostSubject;
    private String mPostComment;
    private boolean mHasFile;
    private String mUrl;
    private Thread mThread;
    private JSONObject mData;
    private Url mUrlGenerator;
    private File mFile;

    private Post(Thread thread, JSONObject data){
        mThread = thread;
        mData = data;
        mUrlGenerator = Url.UrlGenerator(this.mThread.getBoard().getBoardName());
        try {
            mPostId = Integer.parseInt(mData.getString("no"));
            mPosterName = mData.getString("name");
            mPostSubject = mData.has("sub") ? mData.getString("sub") : null;
            mPostComment = mData.getString("com");
            mHasFile = mData.has("filename");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mUrl = String.format("%s#%d", mThread.getUrl(),mPostId);
        mFile = mHasFile ? File.NewFile(this, mData):null;

    }

    public static Post NewPost(Thread thread, JSONObject data){
        return new Post(thread, data);
    }

    public int getPostId() {
        return mPostId;
    }

    public String getPosterName() {
        return mPosterName;
    }


    public String getPostSubject() {
        return ChanHelper.htmlParser(mPostSubject);
    }

    public String getPostComment() {
        return ChanHelper.htmlParser(mPostComment);
    }

    public Boolean isOP() {
        return mThread.getTopic().equals(this);
    }

    public String getUrl() {
        return mUrl;
    }

    public Thread getThread() {
        return mThread;
    }

    public JSONObject getData() {
        return mData;
    }

    public Url getUrlGenerator() {
        return mUrlGenerator;
    }

    public boolean hasFile() {
        return mHasFile;
    }

    public File getFile() {
        return mFile;
    }
}
