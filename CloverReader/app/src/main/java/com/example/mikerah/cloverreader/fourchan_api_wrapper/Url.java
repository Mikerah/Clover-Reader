package com.example.mikerah.cloverreader.fourchan_api_wrapper;

import java.util.Hashtable;
import java.util.Map;

/**
 * 4chan URL generator
 *
 * Created by Mikerah on 5/1/2017.
 */

public class Url {
    private String mBoardName;
    private static Map<String, Map<String, String>> mUrl;

    private Url(String boardName) {
        this.mBoardName = boardName;

        Map<String, String> domain;
        Hashtable<String, Map<String, String>> template;
        Map<String, String> listing;

        // 4chan API URL Subdomains
        domain = new Hashtable<String, String>();
        // 4chan API URL Templates
        template = new Hashtable<String, Map<String,String>>();
        // 4chan API Listings
        listing = new Hashtable<String ,String>();

        // Values for Domain hashtable
        String api = "https://a.4cdn.org"; // API Subdomain
        String boards = "https://boards.4chan.org"; // HTML subdomain
        String file = "https://i.4cdn.org"; // Image host
        String staticHost = "https://s.4cdn.org"; // Static host

        domain.put("api", api);
        domain.put("boards", boards);
        domain.put("file", file);
        domain.put("thumbs", file);
        domain.put("static", staticHost);

        // Values for Template Hashtable
        String board_api = domain.get("api") + "/%s/%s.json";
        String thread_api = domain.get("api") + "/%s/thread/%s.json";
        String board_http = domain.get("boards") + "/%s/%s.json";
        String thread_http = domain.get("boards") + "/%s/thread/%s";
        file = domain.get("file") + "/%s/%s%s";
        String thumbs = domain.get("thumbs") + "/%s/%ss.jpg";
        staticHost = domain.get("static") + "/image/%s";
        
        Map<String,String> apiHashtable = new Hashtable<>();
        apiHashtable.put("board", board_api);
        apiHashtable.put("thread", thread_api);

        Map<String, String> httpHashtable = new Hashtable<>();
        httpHashtable.put("board", board_http);
        httpHashtable.put("thread", thread_http);

        Map<String, String> dataHashtable = new Hashtable<>();
        dataHashtable.put("file", file);
        dataHashtable.put("thumbs", thumbs);
        dataHashtable.put("static", staticHost);

        template.put("api", apiHashtable);
        template.put("http", httpHashtable);
        template.put("data", dataHashtable);

        // Values for Listing Hashtable
        String boardList = domain.get("api") + "/board.json";
        String threadList = domain.get("api") + "/%s/threads.json";
        String archivedThreadList = domain.get("api") + "/%s/archive.json";
        String catalog = domain.get("api") + "/%s/catalog.json";

        listing.put("boardList", boardList);
        listing.put("threadList", threadList);
        listing.put("archivedThreadList", archivedThreadList);
        listing.put("catalog",catalog);

        mUrl = template;
        mUrl.put("domain", domain);
        mUrl.put("listing", listing);

    }

    public static Url UrlGenerator(String boardName) {
        return new Url(boardName);
    }

    public String getBoardUrl() {
        return mUrl.get("api") + mBoardName;
    }

    public static String getBoardList(){
        /*
        Create boards listing URL
         */
        return mUrl.get("listing").get("boardList");
    }

    public String getBoardPageUrl(String page){
        /*
        Create board page URL
         */
        return String.format(mUrl.get("api").get("board"),mBoardName,page);
    }

    public String getCatalogUrl(){
        /*
        Create catalog URL
         */
        return String.format(mUrl.get("listing").get("catalog"), mBoardName);
    }

    public String getThreadList(){
        /*
        Create threads listing URL
         */
        return String.format(mUrl.get("listing").get("threadList"), mBoardName);
    }

    public String getApiThreadUrl(String threadId){
        /*
        Create API thread URL
         */
        return String.format(mUrl.get("api").get("thread"), mBoardName,threadId);
    }

    public String getThreadUrl(String threadId) {
        /*
        Create HTTP thread URL
         */
        return String.format(mUrl.get("http").get("thread"), mBoardName, threadId);
    }

    public String getFileUrl(String tim, String ext){
        /*
        Create File URL
         */
        return String.format(mUrl.get("data").get("file"), mBoardName,tim,ext);
    }

    public String getThumbURL(String tim){
        /*
        Create thumb URL
         */
        return String.format(mUrl.get("data").get("thumbs"), mBoardName, tim);
    }

    public static Map<String, Map<String,String>> getUrl(){
        /*
        Retrieve Hashtable of URLs.
         */
        return mUrl;
    }


}
