package com.example.mikerah.cloverreader;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.Post;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.Thread;

import java.util.List;



public class ThreadFragment extends Fragment {

    private RecyclerView mPosts;
    private Thread mThread;
    private Board mBoard;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param boardName Parameter 1.
     * @param threadId Parameter 2.
     * @return A new instance of fragment ThreadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThreadFragment newInstance(String boardName, String
            threadId) {
        ThreadFragment fragment = new ThreadFragment();
        Bundle args = new Bundle();
        args.putString("Board", boardName);
        args.putString("Thread", threadId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBoard = Board.newBoard(getArguments().getString("Board"));
            new GetThreadTask(Integer.parseInt(getArguments().getString
                    ("Thread"))).execute();
        }

        new GetPostsTask().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_thread, container,
                false);

        return view;
    }

    private class GetBoardTask extends AsyncTask<Void, Void, Board> {

        String mBoardName;

        public GetBoardTask(String boardName) {
            mBoardName = boardName;
        }

        @Override
        protected Board doInBackground(Void... params) {
            Board board = Board.newBoard(mBoardName);
            return board;
        }

        @Override
        protected void onPostExecute(Board board) {
            mBoard = board;
        }
    }

    private class GetThreadTask extends AsyncTask<Void, Void, Thread> {

        int mThreadId;

        public GetThreadTask(int id) {
            mThreadId = id;
        }

        @Override
        protected Thread doInBackground(Void... params) {
            Thread thread = Thread.newThread(mBoard, mThreadId);
            return thread;
        }

        @Override
        protected void onPostExecute(Thread thread) {
            mThread = thread;
            getActivity().setTitle(mBoard.getBoardTitle() + " - " + mThread.getId());

        }
    }

    private class GetPostsTask extends AsyncTask<Void, Void, List<Post>> {

        @Override
        protected List<Post> doInBackground(Void... params) {
            List<Post> posts = mThread.getListOfPosts();
            return posts;
        }


    }
}
