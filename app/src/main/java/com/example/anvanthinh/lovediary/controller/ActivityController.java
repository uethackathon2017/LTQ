package com.example.anvanthinh.lovediary.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * class dieu khien xem khi nao la dien thoai, khi nao la may tinh bang de
 *  dua ra cac lop phu hop
 */

public abstract class ActivityController extends AppCompatActivity {
    protected  AppCompatActivity mActivity;
    public ActivityController(AppCompatActivity _a){
        this.mActivity = _a;
    }
    public abstract void onCreate(Bundle save);

}
