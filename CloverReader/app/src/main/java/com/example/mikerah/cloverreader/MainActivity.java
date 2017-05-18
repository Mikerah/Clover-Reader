package com.example.mikerah.cloverreader;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return MainFragment.newMainFragment();
    }



}
