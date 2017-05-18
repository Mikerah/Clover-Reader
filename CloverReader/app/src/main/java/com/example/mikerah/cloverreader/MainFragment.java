package com.example.mikerah.cloverreader;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;

import java.util.List;

/**
 * Created by Mikerah on 5/17/2017.
 */

public class MainFragment extends Fragment {

    private GridView mBoardGridView;

    public static MainFragment newMainFragment() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_board,container,false);
        mBoardGridView = (GridView) view.findViewById(R.id.boards_grid_view);

        new GetBoardTitlesTask().execute();

        return view;
    }

    private class BoardAdapter extends BaseAdapter {

        private Context mContext;
        private List<String> mBoardTitles;

        public BoardAdapter(Context c, List<String> boardTitles) {
            mContext = c;
            mBoardTitles = boardTitles;
        }


        @Override
        public int getCount() {
            return mBoardTitles.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView;
            if(convertView == null) {
                textView = new TextView(mContext);
            } else {
                textView = (TextView) convertView;
            }

            textView.setText(mBoardTitles.get(position));
            return textView;
        }
    }

    private class GetBoardTitlesTask extends AsyncTask<Void,Void,List<String>> {

        @Override
        protected List<String> doInBackground(Void... params) {
            List<String> boardTitles = CloverHelper.getBoardTitles();
            return boardTitles;
        }

        @Override
        protected void onPostExecute(List<String> boardTitles) {
            mBoardGridView.setAdapter(new BoardAdapter(getActivity(), boardTitles));

        }
    }


}
