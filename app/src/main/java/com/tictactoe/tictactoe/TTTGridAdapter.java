package com.tictactoe.tictactoe;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.tictactoe.tictactoe.TTTGame.Constants;
import com.tictactoe.tictactoe.TTTGame.TTTGame;

/**
 * Created by QiFeng on 4/21/17.
 */

public class TTTGridAdapter extends RecyclerView.Adapter<TTTGridViewHolder> {

    private TTTGame mTTTGame;
    private TTTGridViewHolder.OnGridClickedListener mOnGridClickedListener;

    public TTTGridAdapter(TTTGame game, TTTGridViewHolder.OnGridClickedListener listener){
        mTTTGame = game;
        mOnGridClickedListener = listener;
    }

    @Override
    public TTTGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TTTGridViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_ttt_grid, parent, false), mOnGridClickedListener);
    }

    @Override
    public void onBindViewHolder(TTTGridViewHolder holder, int position) {
        int x = position / Constants.BOARD_SIZE;
        int y = position % Constants.BOARD_SIZE;

        holder.onBind(mTTTGame.getBoardValueAt(x,y), x, y);
    }

    @Override
    public int getItemCount() {
        return Constants.BOARD_SIZE * Constants.BOARD_SIZE;
    }
}
