package com.sakuya.anime1.ui.view.cardpager;

import android.view.View;
public interface CardAdapter {

    View getCardViewAt(int position);

    int getCount();
}
