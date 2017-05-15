package com.example.mikerah.cloverreader.fourchan_api_wrapper;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikerah on 4/30/2017.
 */

public class Thread {

    private boolean mIsClosed;
    private boolean mIsSticky;
    private boolean mIsArchived;
    private boolean mIsAtBumpLimit;
    private boolean mIsAtImgLimit;
    private boolean mIs404;
    private int mNumberOfCustomSpoilers;
    private Post mTopic;
    private List<Post> mListOfPosts;
    private String mUrl;
    private Board mBoard;
    private int mId;
    private Url mUrlGenerator;

    private Thread(Board board, int id){
        mBoard = board;
        mId = id;
        mUrlGenerator = Url.UrlGenerator(board.getBoardName());
        mUrl = mUrlGenerator.getApiThreadUrl(Integer.toString(mId));
        mTopic = null;
        mIs404 = false;
        mListOfPosts = new ArrayList<>();
        mListOfPosts.add(mTopic);
        try {
            mIsClosed = mTopic.getData().get("closed") == 1;
            mIsSticky = mTopic.getData().get("sticky") == 1;
            mIsArchived = mTopic.getData().get("archived") == 1;
            mIsAtImgLimit = mTopic.getData().get("imagelimit") == 1;
            mIsAtBumpLimit = mTopic.getData().get("bumplimit") == 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static Thread newThread(Board board, int id){
        return new Thread(board, id);
    }

    public int getNumberOfReplies(){
        return mListOfPosts.size();
    }


    public String getUrl() {
        return mUrl;
    }

    public Board getBoard() {
        return mBoard;
    }

    public Post getTopic() {
        return mTopic;
    }

    public List<String> files() {
        List<String> urls = new ArrayList<>();
        if (mTopic.hasFile()){
            urls.add(mTopic.getFile().getFileUrl());
        }
        for(Post reply : mListOfPosts){
            if (reply.hasFile()) {
                urls.add(reply.getFile().getFileUrl());
            }
        }
        return urls;
    }

    public List<String> thumbs() {
        List<String> urls = new ArrayList<>();
        if (mTopic.hasFile()){
            urls.add(mTopic.getFile().getFileThumbnailUrl());
        }
        for (Post reply : mListOfPosts){
            if (reply.hasFile()) {
                urls.add(reply.getFile().getFileThumbnailUrl());
            }
        }

        return urls;
    }

    public List<String> filenames() {
        List<String> names = new ArrayList<>();
        if (mTopic.hasFile()){
            names.add(mTopic.getFile().getFilename());
        }
        for (Post reply: mListOfPosts) {
            if(reply.hasFile()){
                names.add(reply.getFile().getFilename());
            }
        }

        return names;
    }

    public List<String> thumbnames() {
        List<String> names = new ArrayList<>();
        if(mTopic.hasFile()){
            names.add(mTopic.getFile().getFileThumbnailName());
        }
        for(Post reply: mListOfPosts) {
            if(reply.hasFile()) {
                names.add(reply.getFile().getFileThumbnailName());
            }
        }

        return names;
    }

    public List<File> fileObjects() {
        List<File> files = new ArrayList<>();
        if(mTopic.hasFile()) {
            files.add(mTopic.getFile());
        }
        for(Post reply: mListOfPosts) {
            if(reply.hasFile()) {
                files.add(reply.getFile());
            }
        }

        return files;
    }

    public List<Post> getListOfPosts() {
        return mListOfPosts;
    }

    public int update() {
        if (mIs404) {
            return 0;
        }

    }

    public boolean is404() { return mIs404;}



}
