package com.example.mikerah.cloverreader;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.Url;

import net.dongliu.requests.Requests;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.Assert;

import java.util.List;

/**
 * Created by Mikerah on 5/19/2017.
 */

public class BoardTest {

    @Test
    public void BoardTest_Test() throws JSONException {
        JSONObject jsonObject = new JSONObject(Requests.get(Url.getBoardList
                ()).send().readToText());

        //System.out.print(jsonObject);


    }
}
