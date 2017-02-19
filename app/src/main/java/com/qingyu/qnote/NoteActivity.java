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

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.jhuster.qnote.R;
import com.qingyu.qnote.db.NoteDB;
import com.qingyu.qnote.db.NoteDB.Note;

import java.util.Calendar;

public class NoteActivity extends BaseActivity {

    private Note mNote = new Note();
  //  private MDWriter mMDWriter;
    private EditText contentEditText;
    private  EditText tittleEditText;
    private  EditText signEditText;
    private String sign = "QingYu";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        onSaveNote();
        //loadData();
        super.onPause();
    }

/*   @Override
    protected void onResume() {
        loadData();
        super.onResume();
    }*/
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_note);
        contentEditText = (EditText) findViewById(R.id.ContentEditText);
        tittleEditText = (EditText) findViewById(R.id.TittleEditText);
        signEditText =(EditText)findViewById(R.id.signEditText);
    }

    @Override
    protected void loadData() {
       // mMDWriter = new MDWriter(contentEditText);
        mNote.key = getIntent().getLongExtra("NoteId", -1);
        if (mNote.key != -1) {
            Note note = NoteDB.getInstance().get(mNote.key);
            if (note != null) {
                //mMDWriter.setContent(note.content);
                mNote = note;
                tittleEditText.setText(mNote.title);
                contentEditText.setText(mNote.content);
                signEditText.setText(mNote.signature);
            } else {
                mNote.key = -1;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_display) {
            onSaveNote();
            Intent intent = new Intent(this, DisplayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("note",mNote);
            intent.putExtras(bundle);
            startActivity(intent);
            return true;
        } else if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

/*    public void onClickHeader(View v) {
        mMDWriter.setAsHeader();
    }

    public void onClickCenter(View v) {
        mMDWriter.setAsCenter();
    }

    public void onClickList(View v) {
        mMDWriter.setAsList();
    }

    public void onClickBold(View v) {
        mMDWriter.setAsBold();
    }

    public void onClickQuote(View v) {
        mMDWriter.setAsQuote();
    }*/

    public void onSaveNote() {
       /* mNote.title = mMDWriter.getTitle();;
        mNote.content = mMDWriter.getContent();*/
        mNote.title = tittleEditText.getText().toString();
        mNote.content = contentEditText.getText().toString();
        mNote.signature = sign;

        if (mNote.key == -1) {
            if (!"".equals(mNote.content)) {
                mNote.date = Calendar.getInstance().getTimeInMillis();
                NoteDB.getInstance().insert(mNote);
            }
        } else {
            NoteDB.getInstance().update(mNote);
        }
    }
}
