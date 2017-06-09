package com.example.mikerah.cloverreader;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;

import java.io.InputStream;

public class BoardActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context context, Board board) {
        Intent intent = new Intent(context, BoardActivity.class);
        intent.putExtra("Board", board.getBoardName());
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String boardName = getIntent().getStringExtra("Board");
        return BoardFragment.newBoardFragment(boardName);
    }

}
