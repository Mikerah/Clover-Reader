package com.example.mikerah.cloverreader.fourchan_api_wrapper;

import net.dongliu.requests.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
            mIsClosed = Integer.parseInt(mTopic.getData().get("closed").toString()) == 1;
            mIsSticky = Integer.parseInt(mTopic.getData().get("sticky").toString())
                    == 1;
            mIsArchived = Integer.parseInt(mTopic.getData().get("archived").toString())
                    == 1;
            mIsAtImgLimit = Integer.parseInt(mTopic.getData().get
                    ("imagelimit").toString()) == 1;
            mIsAtBumpLimit = Integer.parseInt(mTopic.getData().get
                    ("bumplimit").toString()) == 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        fromJSON();

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

    public String getThreadUrl() {
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
        int numberOfNewPosts = 0;
        if (mIs404) {
            return 0;
        }

        JSONObject metadata;
        try {
            metadata = (JSONObject) new JSONObject(Requests.get(mUrl).send().readToText());
            JSONArray posts = (JSONArray) metadata.get("posts");
            if(mListOfPosts.size() == posts.length()) {
                return 0;
            }
            for(int i=0; mListOfPosts.size() < posts.length(); i++) {
                mListOfPosts.add(Post.NewPost(this, (JSONObject)posts.get(i)));
                numberOfNewPosts++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return numberOfNewPosts;


    }

    public boolean is404() { return mIs404;}

    private void fromJSON() {
        JSONObject metadata;
        try {
            metadata = (JSONObject) ChanHelper.getJSONFromUrl(mUrl, false);

            JSONArray posts = (JSONArray) metadata.get("posts");
            mTopic = Post.NewPost(this,(JSONObject)posts.get(0));
            for(int i=1; i < posts.length();i++){
                mListOfPosts.add(i, Post.NewPost(this, (JSONObject)posts.get
                        (i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
