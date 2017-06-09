package com.example.mikerah.cloverreader;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mikerah on 5/17/2017.
 */

public class MainFragment extends Fragment {

    private RecyclerView mRecyclerView;

    public static MainFragment newMainFragment() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetBoardsTask().execute();
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_main,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_boards_recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        return view;
    }

    private class BoardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Button mButton;
        private Board mBoard;

        public BoardHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_board, container, false));

            mButton = (Button) itemView.findViewById(R.id
                    .list_item_board_button);
            mButton.setOnClickListener(this);
        }

        public void bindBoard(Board board) {
            mBoard = board;
            mButton.setText(board.getBoardTitle() + "\n\\" + board
                    .getBoardName());
        }

        @Override
        public void onClick(View v) {
            Intent i = BoardActivity.newIntent(getActivity(), mBoard);
            startActivity(i);
        }
    }

    private class BoardAdapter extends RecyclerView.Adapter<BoardHolder> {
        private List<Board> mBoards;

        public BoardAdapter(List<Board> boards) {
            mBoards = boards;
        }

        @Override
        public BoardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new BoardHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(BoardHolder boardHolder, int position) {
            Board board = mBoards.get(position);
            boardHolder.bindBoard(board);
        }

        @Override
        public int getItemCount() {
            return mBoards.size();
        }
    }

    private class GetBoardsTask extends AsyncTask<Void,Void,List<Board>> {

        @Override
        protected List<Board> doInBackground(Void... params) {
            List<Board> boards = Board.getListOfAllBoardObjs();
            return boards;
        }

        @Override
        protected void onPostExecute(List<Board> boards) {
            mRecyclerView.setAdapter(new BoardAdapter(boards));

        }
    }


}
