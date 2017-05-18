package com.example.mikerah.cloverreader;

import com.example.mikerah.cloverreader.fourchan_api_wrapper.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikerah on 4/30/2017.
 */

public class CloverHelper {

    public static List<String> getBoardTitles() {
        List<String> boardTitles = new ArrayList<>();
        List<Board> allBoards = Board.getListOfAllBoardObjs();

        for(Board b:allBoards) {
            boardTitles.add(b.getBoardTitle());
        }

        return boardTitles;
    }
}
