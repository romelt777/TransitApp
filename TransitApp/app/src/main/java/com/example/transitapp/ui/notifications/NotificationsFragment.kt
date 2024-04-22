package com.example.transitapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.transitapp.databinding.FragmentNotificationsBinding
import com.example.transitapp.ui.BaseFragment
import com.google.transit.realtime.GtfsRealtime
import java.net.URL


class NotificationsFragment : BaseFragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val url = URL("https://gtfs.halifax.ca/realtime/Alert/Alerts.pb")
        val feed = GtfsRealtime.FeedMessage.parseFrom(url.openStream())

//        val alertTextView = binding.textViewAlert
        var alertTextView :TextView


        val llay = binding.linearLayoutAlert;
        var test = "";
        for ((index,entity) in feed.entityList.withIndex()) {

            println("Message!!!: ${entity.alert.descriptionText.translationList[0].text}")
            alertTextView = TextView(activity)
            alertTextView.setLayoutParams(LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT))
            alertTextView.text= entity.alert.descriptionText.translationList[0].text;
            llay.addView(alertTextView)
        }
        binding.scrollView.removeAllViews()
        binding.scrollView.addView(llay)

        return root
    }
    fun addViewToScroll(textView: TextView){
        binding.linearLayoutAlert.apply {
            if(parent !=null){
                (parent as ViewGroup).removeView(this)
            }
        }
        binding.linearLayoutAlert.addView(textView);
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}