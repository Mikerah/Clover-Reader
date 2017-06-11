package com.example.mikerah.cloverreader;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.Thread;

import static java.security.AccessController.getContext;

public class ThreadActivity extends SingleFragmentActivity{

    public static Intent newIntent(Context context, Board board, Thread thread) {
        Intent intent = new Intent(context, ThreadActivity.class);
        intent.putExtra("Board", board.getBoardName());
        intent.putExtra("Thread", Integer.toString(thread.getId()));
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String boardName = getIntent().getStringExtra("Board");
        String threadId = getIntent().getStringExtra("Thread");
        return ThreadFragment.newInstance(boardName,threadId);
    }

}
