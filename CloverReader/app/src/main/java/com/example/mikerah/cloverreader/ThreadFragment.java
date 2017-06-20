package com.example.mikerah.cloverreader;

import android.content.Context;
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

import java.util.List;



public class ThreadFragment extends Fragment {

    private RecyclerView mRecyclerView;
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id
                .fragment_posts_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));

        return view;
    }

    private class PostHolder extends RecyclerView.ViewHolder implements
            RecyclerView.OnClickListener {

        private Post mPost;

        private ImageView mPic;
        private TextView mText;


        public PostHolder(View postView) {
            super(postView);

            mPic = (ImageView) postView.findViewById(R.id
                    .img_for_post_with_pic);
            mText = (TextView) postView.findViewById(R.id
                    .text_view_for_post_with_pic);
        }

        public void bindPostItem(Post post){
            mPost = post;
            String text = mPost.getPostComment();
            mText.setText(text);
            if(post.hasFile()){
                Glide.with(getActivity()).load(post.getFile().getFileUrl())
                        .into(mPic);
            }
        }

        @Override
        public void onClick(View v) {

        }
    }

    private class PostAdapter extends RecyclerView.Adapter<PostHolder> {

        private List<Post> mPosts;

        public PostAdapter(List<Post> posts) {
            mPosts = posts;
        }

        @Override
        public PostHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_post_with_pic,
                    parent, false);
            return new PostHolder(view);

        }

        @Override
        public void onBindViewHolder(PostHolder holder, int position) {
            Post post = mPosts.get(position);
            holder.bindPostItem(post);
        }

        @Override
        public int getItemCount() {
            return mPosts.size();
        }
    }

    private class GetThreadTask extends AsyncTask<Void, Void, Thread> {

        int mThreadId;

        public GetThreadTask(int id) {
            mThreadId = id;
        }

        @Override
        protected Thread doInBackground(Void... params) {
            Thread thread = mBoard.getThread(mThreadId,true);
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

        @Override
        protected void onPostExecute(List<Post> posts) {
            mRecyclerView.setAdapter(new PostAdapter(posts));
        }


    }

}
