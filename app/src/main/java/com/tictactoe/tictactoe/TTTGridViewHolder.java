package com.tictactoe.tictactoe;

import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by QiFeng on 4/21/17.
 */
public class TTTGridViewHolder extends RecyclerView.ViewHolder{

    private TextView vTextView;
    private OnGridClickedListener mOnGridClickedListener;
    private Point mPosition = new Point(0,0);

    public TTTGridViewHolder(View itemView, OnGridClickedListener listener) {
        super(itemView);
        mOnGridClickedListener = listener;
        vTextView = (TextView) itemView.findViewById(R.id.textview);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnGridClickedListener != null)
                    mOnGridClickedListener.onGridClicked(vTextView, mPosition);
            }
        });
    }

    public void onBind(String text, int x, int y){
        mPosition.set(x,y);
        vTextView.setText(text);
    }

    public interface OnGridClickedListener{
        public void onGridClicked(TextView textView, Point position);
    }

}
