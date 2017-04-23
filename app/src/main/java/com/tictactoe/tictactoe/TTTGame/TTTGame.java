package com.tictactoe.tictactoe.TTTGame;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by QiFeng on 4/20/17.
 */

public class TTTGame implements Parcelable{

    private GameBoard mGameBoard = new GameBoard();

    // players are 1 or 2
    private int mPlayerTurn = 1;

    private GameState mGameState = GameState.Start;

    public TTTGame(){

    }

    /**
     * set up the board for the next game
     */
    public void resetBoard(){
        mGameBoard = new GameBoard();
        mPlayerTurn = 1;
        mGameState = GameState.Start;
    }

    /**
     * start the game
     */
    public void startGame(){
        mGameState = GameState.Playing;
    }

    public GameState getGameState() {
        return mGameState;
    }

    /**
     * get value on the board
     * @param x position x
     * @param y position y
     * @return "X" for player 1, "O" for player 2, "" for empty
     */
    public String getBoardValueAt(int x, int y){
        return mGameBoard.getBoardValueAt(x,y);
    }

    public int getPlayerTurn() {
        return mPlayerTurn;
    }

    /**
     * update to next player's turn
     */
    public void nextPlayerTurn(){
        mPlayerTurn = mPlayerTurn == GameBoard.PLAYER_1 ? GameBoard.PLAYER_2 : GameBoard.PLAYER_1;
    }

    /***
     * places value on the TTT board
     * @param x - x position
     * @param y - y position
     * @return true if placed on board, otherwise false
     */
    public boolean placeOnBoard(int x, int y){
        if (!mGameBoard.validMove(x,y)) return  false;
        mGameBoard.setXY(x,y, mPlayerTurn);
        return true;
    }

    /***
     * check if there is a winner and update the game state
     * @param x - x position
     * @param y - y position
     */
    public void updateGameState(int x, int y){
        int winner;

        winner = mGameBoard.checkRowWin(x);
        if (winner != GameBoard.NO_PLAYER){
            mGameState = winner == GameBoard.PLAYER_1 ? GameState.WinPlayer1 : GameState.WinPlayer2;
            return;
        }

        winner = mGameBoard.checkColumnWin(y);
        if (winner != GameBoard.NO_PLAYER){
            mGameState = winner == GameBoard.PLAYER_1 ? GameState.WinPlayer1 : GameState.WinPlayer2;
            return;
        }

        winner = mGameBoard.checkDiagWinTopLeftToBottomRight(x,y);
        if (winner != GameBoard.NO_PLAYER){
            mGameState = winner == GameBoard.PLAYER_1 ? GameState.WinPlayer1 : GameState.WinPlayer2;
            return;
        }

        winner = mGameBoard.checkDiagWinBottomLeftToTopRight(x,y);
        if (winner != GameBoard.NO_PLAYER){
            mGameState = winner == GameBoard.PLAYER_1 ? GameState.WinPlayer1 : GameState.WinPlayer2;
            return;
        }

        //no more valid positions left: game over
        if (mGameBoard.getPositionsRemaining() <= 0){
            mGameState = GameState.NoWinner;
        }
    }


    protected TTTGame(Parcel in) {
        mGameBoard = in.readParcelable(GameBoard.class.getClassLoader());
        mPlayerTurn = in.readInt();
        mGameState = GameState.valueOf(in.readString());
    }

    public static final Creator<TTTGame> CREATOR = new Creator<TTTGame>() {
        @Override
        public TTTGame createFromParcel(Parcel in) {
            return new TTTGame(in);
        }

        @Override
        public TTTGame[] newArray(int size) {
            return new TTTGame[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mGameBoard, i);
        parcel.writeInt(mPlayerTurn);
        parcel.writeString(mGameState.name());
    }
}
