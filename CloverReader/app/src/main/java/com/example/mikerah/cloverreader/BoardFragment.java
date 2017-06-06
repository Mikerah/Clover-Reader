package com.example.mikerah.cloverreader;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;


public class BoardFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Board mBoard;

    public static BoardFragment newBoardFragment(Board board) {
        Bundle args = new Bundle();
        args.putParcelable("Board", board);

        BoardFragment fragment = new BoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mBoard = getArguments().getParcelable("Board");
    }



}
