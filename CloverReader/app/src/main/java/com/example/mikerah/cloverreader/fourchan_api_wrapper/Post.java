package com.example.mikerah.cloverreader.fourchan_api_wrapper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mikerah on 5/1/2017.
 */

public class Post {
    private int mPostId;
    private int mPosterId;
    private String mPosterName;
    private String mPosterEmail;
    private String mPostSubject;
    private String mPostComment;
    private Boolean mIsOP;
    private String mUrl;
    private Thread mThread;
    private JSONObject mData;
    private Url mUrlGenerator;

    private Post(Thread thread, JSONObject data){
        mThread = thread;
        mData = data;
        mUrlGenerator = Url(this.mThread.getBoardName());
        mIsOP = mThread.getTopic().equals(this);
        try {
            mPostId = Integer.parseInt(mData.getString("no"));
            mPosterId = Integer.parseInt(mData.getString("id"));
            mPosterName = mData.getString("name");
            mPosterEmail = mData.getString("email");
            mPostSubject = mData.getString("sub");
            mPostComment = mData.getString("com");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mUrlGenerator = String.format("%s#%i", mThread.getUrl(),mPostId);

    }

    public Post NewPost(Thread thread, JSONObject data){
        return new Post(thread, data);
    }

    public int getmPostId() {
        return mPostId;
    }

    public int getmPosterId() {
        return mPosterId;
    }

    public String getmPosterName() {
        return mPosterName;
    }

    public String getmPosterEmail() {
        return mPosterEmail;
    }

    public String getmPostSubject() {
        return mPostSubject;
    }

    public String getmPostComment() {
        return mPostComment;
    }

    public Boolean getmIsOP() {
        return mIsOP;
    }

    public String getmUrl() {
        return mUrl;
    }

    public Thread getmThread() {
        return mThread;
    }

    public JSONObject getmData() {
        return mData;
    }

    public Url getmUrlGenerator() {
        return mUrlGenerator;
    }
}
