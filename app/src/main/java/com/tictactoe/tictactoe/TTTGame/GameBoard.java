package com.tictactoe.tictactoe.TTTGame;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QiFeng on 4/20/17.
 */

public class GameBoard implements Parcelable {

    public static final int PLAYER_1 = 1;
    public static final int PLAYER_2 = 2;
    public static final int NO_PLAYER = 0;

    public static final String PLAYER_1_VAL = "X";
    public static final String PLAYER_2_VAL = "O";
    public static final String NO_PLAYER_VAL = "";

    // 0 for unused
    // 1 for player 1
    // 2 for player 2
    private int mGameBoard[][] = new int[Constants.BOARD_SIZE][Constants.BOARD_SIZE];

    private int mPositionsRemaining = Constants.BOARD_SIZE * Constants.BOARD_SIZE;

    public GameBoard(){

    }

    /**
     * check if x,y is a valid move (position is empty)
     * @param x - x position
     * @param y - y position
     * @return true if valid move
     */
    public boolean validMove(int x, int y){
        return mGameBoard[x][y] == NO_PLAYER;
    }

    /**
     * places val at position x,y
     * @param x - x position
     * @param y - y position
     * @param val - val to place at position x,y (1 for player 1, 2 for player2)
     */
    public void setXY(int x, int y, int val){
        mGameBoard[x][y] = val;
        mPositionsRemaining--;
    }

    /**
     * @return number of valid positions left
     */
    public int getPositionsRemaining() {
        return mPositionsRemaining;
    }

    /**
     * get value at position x,y
     * @param x - position x
     * @param y - position y
     * @return X for player 1, O for player 2, "" for empty
     */
    public String getBoardValueAt(int x, int y){
        if (mGameBoard[x][y] == 0) return  NO_PLAYER_VAL;
        return mGameBoard[x][y] == PLAYER_1 ? PLAYER_1_VAL : PLAYER_2_VAL;
    }

    /**
     * check row x is there is a winner
     * @param x - row
     * @return 0 if no winner or the winner
     */
    public int checkRowWin(int x){
        int val = mGameBoard[x][0];

        for (int i = 1 ; i < mGameBoard[x].length; i++){
            if (mGameBoard[x][i] != val) return NO_PLAYER;
        }

        return val;
    }

    //check for column win
    public int checkColumnWin(int y){
        int val = mGameBoard[0][y];

        for (int i = 1; i < mGameBoard[0].length; i++){
            if (mGameBoard[i][y] != val) return NO_PLAYER;
        }

        return val;
    }

    //check for diagonal win
    public int checkDiagWinTopLeftToBottomRight(int x, int y){
        if (x != y) return NO_PLAYER;

        for (int i = 1; i < mGameBoard.length; i++){
            if (mGameBoard[i][i] != mGameBoard[i-1][i-1]) return NO_PLAYER;
        }

        return mGameBoard[x][y];
    }

    //check for diagonal win
    public int checkDiagWinBottomLeftToTopRight(int x, int y){
        if (x + y + 1 != mGameBoard.length) return NO_PLAYER;

        int maxIndex = mGameBoard.length - 1;
        for (int i = mGameBoard.length - 2; i >= 0; i--){
            int col = maxIndex - i;
            if (mGameBoard[i][col] != mGameBoard[i + 1][col - 1]) return NO_PLAYER;
        }

        return mGameBoard[x][y];
    }



    protected GameBoard(Parcel in) {
        int count = in.readInt();
        mGameBoard = new int[count][count];

        for (int i = 0; i < count; i++){
            in.readIntArray(mGameBoard[i]);
        }

        mPositionsRemaining = in.readInt();
    }

    public static final Creator<GameBoard> CREATOR = new Creator<GameBoard>() {
        @Override
        public GameBoard createFromParcel(Parcel in) {
            return new GameBoard(in);
        }

        @Override
        public GameBoard[] newArray(int size) {
            return new GameBoard[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mGameBoard.length);
        for (int[] a : mGameBoard){
            parcel.writeIntArray(a);
        }

        parcel.writeInt(mPositionsRemaining);
    }
}
