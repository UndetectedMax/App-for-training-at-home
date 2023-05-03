package com.example.coursework

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView

@SuppressLint("CommitTransaction")
fun AppCompatActivity.replaceFragment(
    container: Int,
    fragment: Fragment,
    tag:String = fragment.javaClass.simpleName
) = supportFragmentManager.beginTransaction().replace(container,fragment,tag).commit()