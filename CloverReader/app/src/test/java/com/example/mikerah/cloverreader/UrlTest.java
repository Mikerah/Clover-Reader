package com.example.mikerah.cloverreader;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Url;

import org.junit.Test;
import org.junit.Assert;

import java.util.Map;

/**
 * Created by Mikerah on 5/1/2017.
 */

public class UrlTest {

    @Test
    public void UrlTest_UrlGenerator(){
        Url urlTestObj = Url.UrlGenerator("b");
        Assert.assertNotNull(urlTestObj);
    }

    @Test
    public void UrlTest_GetBoardList() {
        String boardList = Url.getBoardList();
        Assert.assertNotNull(boardList);

    }

    @Test
    public void UrlTest_GetBoardPageUrl() {
        Url urlTestObj = Url.UrlGenerator("b");
        String boardPageUrl = urlTestObj.getBoardPageUrl("1");
        Assert.assertNotNull(boardPageUrl);
    }

    @Test
    public void UrlTest_GetCatalogUrl() {
        Url urlTestObj = Url.UrlGenerator("b");
        String catalogUrl = urlTestObj.getCatalogUrl();
        Assert.assertNotNull(catalogUrl);
    }

    @Test
    public void UrlTest_GetThreadList() {
        Url urlTestObj = Url.UrlGenerator("b");
        String threadList = urlTestObj.getThreadList();
        Assert.assertNotNull(threadList);
    }

    @Test
    public void UrlTest_GetApiThreadUrl() {
        Url urlTestObj = Url.UrlGenerator("b");
        String apiThreadUrl = urlTestObj.getApiThreadUrl("731184187");
        Assert.assertNotNull(apiThreadUrl);
    }

    @Test
    public void UrlTest_GetThreadUrl() {
        Url urlTestObj = Url.UrlGenerator("b");
        String httpThreadUrl = urlTestObj.getThreadUrl("731184187");
        Assert.assertNotNull(httpThreadUrl);
    }

    @Test
    public void UrlTest_GetFileUrl() {
        Url urlTestObj = Url.UrlGenerator("b");
        String fileUrl = urlTestObj.getFileUrl("731184187","jpg");
        Assert.assertNotNull(fileUrl);
    }

    @Test
    public void UrlTest_GetThumbUrl(){
        Url urlTestObj = Url.UrlGenerator("b");
        String thumbUrl = urlTestObj.getThumbURL("731184187");
        Assert.assertNotNull(thumbUrl);
    }

    @Test
    public void UrlTest_GetUrlMap() {
        Url urlTestObj = Url.UrlGenerator("b");
        Map<String, Map<String,String>> url = Url.getUrl();
        Assert.assertNotNull(url);
    }
}
