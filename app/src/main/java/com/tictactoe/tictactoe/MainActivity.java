package com.tictactoe.tictactoe;

import android.content.DialogInterface;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tictactoe.tictactoe.TTTGame.Constants;
import com.tictactoe.tictactoe.TTTGame.GameBoard;
import com.tictactoe.tictactoe.TTTGame.GameState;
import com.tictactoe.tictactoe.TTTGame.TTTGame;
import com.tictactoe.tictactoe.Utils.GridSpaceDecoration;

public class MainActivity extends AppCompatActivity {

    private static final String ARGS_TTTGAME = "ttt_game_key";

    private TTTGame mTTTGame = new TTTGame();
    private TTTGridAdapter mTTTGridAdapter;

    private TextView vTitle;

    private String[] mTurnTitles = new String[2];

    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTurnTitles[0] = getString(R.string.player1_turn);
        mTurnTitles[1] = getString(R.string.player2_turn);

        if (savedInstanceState != null) mTTTGame = savedInstanceState.getParcelable(ARGS_TTTGAME);
        else mTTTGame = new TTTGame();

        mTTTGridAdapter = new TTTGridAdapter(mTTTGame, new TTTGridViewHolder.OnGridClickedListener() {
            @Override
            public void onGridClicked(TextView textView, Point position) {
                if (!mTTTGame.placeOnBoard(position.x, position.y)) return;

                //update text
                textView.setText(mTTTGame.getBoardValueAt(position.x, position.y));

                //check for winners
                mTTTGame.updateGameState(position.x, position.y);
                mTTTGame.nextPlayerTurn();

                updateTitle(mTTTGame.getPlayerTurn());
                updateScreen(mTTTGame.getGameState());
            }
        });

        vTitle = (TextView) findViewById(R.id.textview);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.addItemDecoration(new GridSpaceDecoration(Constants.BOARD_SIZE, 4, true));
        recyclerView.setLayoutManager(new GridLayoutManager(this, Constants.BOARD_SIZE));
        recyclerView.setAdapter(mTTTGridAdapter);

        updateTitle(mTTTGame.getPlayerTurn());
        updateScreen(mTTTGame.getGameState());
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //save game state
        outState.putParcelable(ARGS_TTTGAME, mTTTGame);
    }


    private void updateScreen(GameState gameState) {
        switch (gameState) {
            case Playing:
                break;
            case WinPlayer1:
                showWinner(GameBoard.PLAYER_1);
                break;
            case WinPlayer2:
                showWinner(GameBoard.PLAYER_2);
                break;
            case Start:
                showStartScreen();
                break;
            case NoWinner:
                showWinner(GameBoard.NO_PLAYER);
                break;
        }
    }

    /**
     * displays dialog with message displaying the winner
     * @param winner - who the winner is
     */
    private void showWinner(int winner) {
        String title;
        String body;
        if (winner == 0){
            title = getString(R.string.no_winner);
            body = getString(R.string.no_winner_body);
        }else {
            title = getString(R.string.player_winner, winner);
            body = getString(R.string.player_winner_body, winner);
        }

        mAlertDialog = new AlertDialog.Builder(this).setTitle(title)
                .setMessage(body)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.play_again), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restartGame();
                    }
                }).show();
    }

    /**
     * screen for when game starts
     */
    private void showStartScreen() {
        mAlertDialog = new AlertDialog.Builder(this).setTitle(R.string.tictactoe)
                .setMessage(R.string.start_to_play)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.start), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        restartGame();
                    }
                }).show();
    }


    /**
     * restart the game
     */
    private void restartGame(){
        mTTTGame.resetBoard();
        mTTTGridAdapter.notifyDataSetChanged();
        updateTitle(mTTTGame.getPlayerTurn());
        mTTTGame.startGame();
    }

    private void updateTitle(int playerTurn){
        vTitle.setText(mTurnTitles[playerTurn - 1]);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAlertDialog != null)
            mAlertDialog.dismiss();
    }
}
