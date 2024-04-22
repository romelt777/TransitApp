package com.example.transitapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.transitapp.MainActivity
import com.example.transitapp.R


abstract class BaseFragment : Fragment() {
    lateinit var ACTIVITY: MainActivity
    override fun onAttach(context: Context){
        super.onAttach(context)
        ACTIVITY = context as MainActivity
    }
}