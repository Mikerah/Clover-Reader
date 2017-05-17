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
    private boolean mIsOP;
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
        mIsOP = mThread.getTopic().equals(this);
        try {
            mPostId = Integer.parseInt(mData.getString("no"));
            mPosterId = Integer.parseInt(mData.getString("id"));
            mPosterName = mData.getString("name");
            mPosterEmail = mData.getString("email");
            mPostSubject = mData.getString("sub");
            mPostComment = mData.getString("com");
            mHasFile = mData.getBoolean("filename");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        mUrl = String.format("%s#%i", mThread.getUrl(),mPostId);
        mFile = mHasFile ? File.NewFile(this, mData):null;

    }

    public static Post NewPost(Thread thread, JSONObject data){
        return new Post(thread, data);
    }

    public int getPostId() {
        return mPostId;
    }

    public int getPosterId() {
        return mPosterId;
    }

    public String getPosterName() {
        return mPosterName;
    }

    public String getPosterEmail() {
        return mPosterEmail;
    }

    public String getPostSubject() {
        return mPostSubject;
    }

    public String getPostComment() {
        return mPostComment;
    }

    public Boolean getIsOP() {
        return mIsOP;
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
