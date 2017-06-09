package com.example.mikerah.cloverreader.fourchan_api_wrapper;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mikerah on 5/1/2017.
 */

public class File {
    private Post mPost;
    private JSONObject mData;
    private Url mUrl;
    private String mMd5Hash;
    private String mMd5HashHex;
    private String mFilename;
    private String mFileUrl;
    private String mFileExtension;
    private int mFileSize;
    private int mFileWidth;
    private int mFileHeight;
    private boolean mIsFileDeleted;
    private int mFileThumbnailWidth;
    private int mFileThumbnailHeight;
    private String mFileThumbnailName;
    private String mFileThumbnailUrl;


    private File(Post post, JSONObject data) {
        mPost = post;
        mData = data;
        mUrl = Url.UrlGenerator(mPost.getThread().getBoard().getBoardName());
        try {
            mMd5Hash = Base64.decode(mData.getString("md5"),Base64.DEFAULT).toString();
            mMd5HashHex = String.format("%H", mMd5Hash);
            mFilename = String.format("%s%s", mData.get("tim"),mData.get("ext"));
            mFileUrl = mUrl.getFileUrl(mData.getString("tim"), mData.getString("ext"));
            mFileExtension = mData.getString("ext");
            mFileSize = mData.getInt("fsize");
            mFileWidth = mData.getInt("w");
            mFileHeight = mData.getInt("h");
            mIsFileDeleted = (mData.getInt("filedeleted") == 1);
            mFileThumbnailHeight = mData.getInt("tn_h");
            mFileThumbnailWidth = mData.getInt("tn_w");
            mFileThumbnailName = String.format("%ss.jpg",mData.getString("tim"));
            mFileThumbnailUrl = mUrl.getThumbURL(mData.getString("tim"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static File NewFile(Post post, JSONObject data){
        return new File(post, data);
    }

    public Post getPost() {
        return mPost;
    }

    public JSONObject getData() {
        return mData;
    }

    public Url getUrl() {
        return mUrl;
    }

    public String getMd5Hash() {
        return mMd5Hash;
    }

    public String getMd5HashHex() {
        return mMd5HashHex;
    }

    public String getFilename() {
        return mFilename;
    }

    public String getFileUrl() {
        return mFileUrl;
    }

    public String getFileExtension() {
        return mFileExtension;
    }

    public int getFileSize() {
        return mFileSize;
    }

    public int getFileWidth() {
        return mFileWidth;
    }

    public int getFileHeight() {
        return mFileHeight;
    }

    public boolean isIsFileDeleted() {
        return mIsFileDeleted;
    }

    public int getFileThumbnailWidth() {
        return mFileThumbnailWidth;
    }

    public int getFileThumbnailHeight() {
        return mFileThumbnailHeight;
    }

    public String getFileThumbnailName() {
        return mFileThumbnailName;
    }

    public String getFileThumbnailUrl() {
        return mFileThumbnailUrl;
    }
}
