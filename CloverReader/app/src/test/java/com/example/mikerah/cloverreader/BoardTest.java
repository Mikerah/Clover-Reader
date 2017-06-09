package com.example.mikerah.cloverreader;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.File;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.Post;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.Thread;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.Url;

import net.dongliu.requests.Requests;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

/**
 * Created by Mikerah on 5/19/2017.
 */

public class BoardTest {

    @Test
    public void BoardTest_Test() throws JSONException {
        Map<String, Map<String, String>> url = Url.getUrl();
        System.out.println(url.get("data").get("thumbs"));
        System.out.print(String.format(url.get("data").get("thumbs"),"b",
                "132"));

    }
}
