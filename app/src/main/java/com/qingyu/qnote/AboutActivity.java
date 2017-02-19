/*
 *  Copyright (C) 2015, Jhuster, All Rights Reserved
 *
 *  Author:  Jhuster(lujun.hust@gmail.com)
 *  
 *  https://github.com/Jhuster/JNote
 *  
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; version 2 of the License.
 */
package com.qingyu.qnote;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.jhuster.qnote.R;
import com.qingyu.qnote.markdown.MDReader;

public class AboutActivity extends BaseActivity {

    private TextView mTextView;
    private MDReader mMDReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initVariables() {
        mMDReader = new MDReader(getAboutAuthor());
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_display);
        mTextView = (TextView) findViewById(R.id.TitileTextView);
    }

    @Override
    protected void loadData() {
        mTextView.setTextKeepState(mMDReader.getFormattedContent(), BufferType.SPANNABLE);
    }

    protected String getVersionDescription() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName + " for Android";
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "Unknow";
    }

    protected String getAboutAuthor() {
        StringBuilder builder = new StringBuilder();
        builder.append("# **版本号:**" + getVersionDescription() + "\n\n");
        builder.append("# **关于作者:**qingyu\n\n");
        return builder.toString();
    }
}
