package com.example.sudoku.activities;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import com.example.sudoku.R;

import java.util.Objects;

public class OnclickListenerButton extends Activity implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        Drawable back = view.getBackground();
        Drawable bt1 = getResources().getDrawable(R.drawable.bt1);
        Drawable bt2 = getResources().getDrawable(R.drawable.bt2);
        Drawable bt3 = getResources().getDrawable(R.drawable.bt3);
        Drawable bt4 = getResources().getDrawable(R.drawable.bt4);
        Drawable bt5 = getResources().getDrawable(R.drawable.bt5);
        Drawable bt6 = getResources().getDrawable(R.drawable.bt6);
        Drawable bt7 = getResources().getDrawable(R.drawable.bt7);
        Drawable bt8 = getResources().getDrawable(R.drawable.bt8);
        Drawable bt9 = getResources().getDrawable(R.drawable.bt9);

            if(view.getBackground().equals(bt1)) {
                view.setBackgroundResource(R.drawable.btsel1);
            }
            else if(view.getBackground().equals(bt2)) {
                view.setBackgroundResource(R.drawable.btsel2);
            }
            else if(view.getBackground().equals(bt3)){
                view.setBackgroundResource(R.drawable.btsel3);
            }
            else if(view.getBackground().equals(bt4)) {
                view.setBackgroundResource(R.drawable.btsel4);
            }
            else if(view.getBackground().equals(bt5)) {
                view.setBackgroundResource(R.drawable.btsel5);
            }
            else if(view.getBackground().equals(bt6)){
                view.setBackgroundResource(R.drawable.btsel6);
            }
            else if(view.getBackground().equals(bt7)) {
                view.setBackgroundResource(R.drawable.btsel7);
            }
            else if(view.getBackground().equals(bt8)) {
                view.setBackgroundResource(R.drawable.btsel8);
            }
            else if(view.getBackground().equals(bt9)){
                view.setBackgroundResource(R.drawable.btsel9);
            }
            else{
                view.setBackgroundResource(R.drawable.vaziosel);
            }
    }
}