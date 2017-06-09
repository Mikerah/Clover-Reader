package com.example.mikerah.cloverreader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.Post;
import com.example.mikerah.cloverreader.fourchan_api_wrapper.Thread;

import org.w3c.dom.Text;

import java.util.List;


public class BoardFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private Board mBoard;

    public static BoardFragment newBoardFragment(String boardName) {
        Bundle args = new Bundle();
        args.putString("Board", boardName);

        BoardFragment fragment = new BoardFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mBoard = Board.newBoard(getArguments().getString("Board"));
        getActivity().setTitle(mBoard.getBoardTitle());

        new GetThreadsTask().execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_board,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id
                .fragment_threads_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
         LinearLayoutManager.VERTICAL, false));
        return view;
    }

    private class ThreadHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private Thread mThread;

        private ImageView mThreadThumbnail;
        private TextView mThreadSubject;
        private TextView mThreadComment;

        public ThreadHolder(View threadView) {
            super(threadView);
            threadView.setOnClickListener(this);

            mThreadThumbnail = (ImageView) threadView.findViewById(R.id
                    .post_img);
            mThreadSubject = (TextView) threadView.findViewById(R.id.post_subject);
            mThreadComment = (TextView) threadView.findViewById(R.id
                    .post_comment);
        }

        public void bindThreadItem(Thread thread) {
            mThread = thread;
            Post topic = mThread.getTopic();
            String threadSub = topic.getPostSubject();
            String threadComment = topic.getPostComment();
            mThreadSubject.setText(threadSub);
            mThreadComment.setText(threadComment);

            if(thread.getTopic().hasFile()) {
                String thumbnailUrl = thread.getTopic().getFile()
                        .getFileUrl();
                Glide.with(getActivity()).load(thumbnailUrl)
                        .into(mThreadThumbnail);
            }


        }

        @Override
        public void onClick(View v) {

        }
    }

    private class ThreadAdapter extends RecyclerView.Adapter<ThreadHolder> {

        private List<Thread> mThreads;

        public ThreadAdapter(List<Thread> threads) {
            mThreads = threads;
        }

        @Override
        public ThreadHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_thread, parent,
                    false);
            return new ThreadHolder(view);
        }

        @Override
        public void onBindViewHolder(ThreadHolder holder, int position) {
            Thread thread = mThreads.get(position);
            holder.bindThreadItem(thread);
        }

        @Override
        public int getItemCount() {
            return mThreads.size();
        }
    }

    private class GetThreadsTask extends AsyncTask<Void,Void,List<Thread>> {

        @Override
        protected List<Thread> doInBackground(Void... params) {
            List<Thread> threads = mBoard.getThreads(1);
            return threads;
        }

        @Override
        protected void onPostExecute(List<Thread> threads) {
            mRecyclerView.setAdapter(new ThreadAdapter(threads));

        }
    }



}
