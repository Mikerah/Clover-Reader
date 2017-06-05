package com.example.mikerah.cloverreader.fourchan_api_wrapper;

import android.os.health.SystemHealthManager;
import android.util.Log;

import junit.framework.Assert;

import net.dongliu.requests.RawResponse;
import net.dongliu.requests.RequestBuilder;
import net.dongliu.requests.Requests;
import net.dongliu.requests.Session;
import net.dongliu.requests.exception.RequestsException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static android.R.attr.data;
import static android.R.attr.id;

/**
 * Created by Mikerah on 4/30/2017.
 */

public class Board {
    private static JSONObject mMetadata = ChanHelper.getJSONFromUrl(Url.getBoardList());

    public static JSONObject getMetadata() {
        return mMetadata;
    }

    public static JSONObject fetchBoardsMetadata(){
        if(mMetadata == null) {
            try {
                mMetadata = ChanHelper.getJSONFromUrl(Url.getBoardList());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return mMetadata;
    }

    private String mBoardName;
    private String mBoardTitle;
    private Url mUrlGenerator;
    private String mBoardUrl;
    private Map<Integer, Thread> mThreadCache;
    private JSONObject mBoardMetadata;

    private Board(String boardName) throws JSONException {
        mBoardName = boardName;
        mUrlGenerator = Url.UrlGenerator(mBoardName);
        mBoardUrl = mUrlGenerator.getBoardUrl();
        mThreadCache = new HashMap<>();

        JSONArray boards = mMetadata.getJSONArray("boards");
        for(int i=0; i<boards.length();i++){
            mBoardMetadata = (JSONObject) boards.get(i);
            if(mBoardMetadata.get("board").equals(mBoardName)) {
                mBoardTitle = mBoardMetadata.getString("title");
                break;
            }
        }
    }

    public static Board newBoard(String boardName) {
        try {
            return new Board(boardName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Board> getListOfBoardObjs(List<String> boardNames){
        List<Board> boards = new ArrayList<>();
        for(String boardName : boardNames) {
            boards.add(newBoard(boardName));
        }

        return boards;
    }

    public static List<Board> getListOfAllBoardObjs() {
        List<Board> allBoards = new ArrayList<>();
        JSONObject metadata = fetchBoardsMetadata();
        JSONArray boards = null;
        try {
            boards = (JSONArray) metadata.get("boards");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(int i=0; i < boards.length();i++){
            JSONObject board;
            try {
                board = boards.getJSONObject(i);
                allBoards.add(i, newBoard(board.getString("board")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return allBoards;
    }

    public List<Integer> getAllThreadIds(){
        List<Integer> threadIds = new ArrayList<>();
        JSONArray jsonObject;
        try {
            jsonObject = new JSONArray(Requests.get(mUrlGenerator.getThreadList()).send().readToText());
            for(int i=0; i<jsonObject.length(); i++){
                JSONObject page = (JSONObject) jsonObject.get(i);
                JSONArray pageArray = (JSONArray) page.get("threads");
                for (int j=0; j < pageArray.length(); j++){
                    JSONObject thread = (JSONObject) pageArray.get(j);
                    threadIds.add((int)(long) thread.get("no"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return threadIds;
    }

    public String getBoardName() {
        return mBoardName;
    }

    public String getBoardTitle() {
        return mBoardTitle;
    }

    public Thread getThread(int threadId, boolean updateCached) {
        // Check if thread is already cached
        Thread cachedThread = mThreadCache.get(threadId);
        if(cachedThread == null){
            Thread newThread = Thread.newThread(this,threadId);
            mThreadCache.put(threadId,newThread);
            mThreadCache.get(threadId);
        }
        if(!updateCached){
            return cachedThread;
        }

        Thread newThread = Thread.newThread(this,threadId);
        if (newThread.is404()){
            mThreadCache.remove(threadId);
            return null;
        }
        mThreadCache.put(threadId,newThread);
        return newThread;

    }

    public boolean threadExists(int threadId) {
        boolean exists = Requests.head(mUrlGenerator.getApiThreadUrl(Integer.toString(threadId))).send().getStatusCode() == 200;
        return exists;
    }

    public List<Thread> getThread(int boardPage) {
        List<Thread> listOfThreadsOnPage = new ArrayList<>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(Requests.get(mUrlGenerator
                    .getBoardPageUrl(Integer.toString(boardPage))).send().readToText());
            JSONArray threads = (JSONArray) jsonObject.get("threads");
            for(int i=0; i<threads.length(); i++) {
                JSONArray posts = (JSONArray) ((JSONObject) threads.get(i))
                        .get("post");
                listOfThreadsOnPage.add(Thread.newThread(this,(int)(long)(
                        (JSONObject)posts.get(0)).get("no")
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listOfThreadsOnPage;
    }

    public List<Thread> getAllThreads() {
        List<Thread> allThreads = new ArrayList<>();
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(Requests.get(mUrlGenerator
                    .getThreadList()).send().readToText());

            for(int i=0; i<jsonArray.length();i++){
                JSONObject page = (JSONObject) jsonArray.get(i);
                JSONArray pageArray = (JSONArray) page.get("threads");
                for(int j=0; j<jsonArray.length();j++) {
                    JSONObject thread = (JSONObject) pageArray.get(j);
                    int id = (int)(long)thread.get("no");
                    Thread newThread = Thread.newThread(this, id);
                    allThreads.add(newThread);
                    mThreadCache.put(id,newThread);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return allThreads;
    }

}
