package com.fragilebytes.asos.services;

import android.view.View;

/**
 * Created by Windows on 10/04/2016.
 */
public interface ClickListener {
    public void onClick(View view, int position);

    public void onLongClick(View view, int position);
}