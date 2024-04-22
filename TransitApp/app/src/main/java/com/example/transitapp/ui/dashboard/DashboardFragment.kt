package com.example.transitapp.ui.dashboard

import android.R
import android.app.Activity
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.transitapp.databinding.FragmentDashboardBinding
import com.example.transitapp.ui.BaseFragment
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader


class DashboardFragment : BaseFragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle? //? : nullable?
    ): View { //return value

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root //setting data type with colon.

        //autoCompleteTextView for dashboardFragment
        var autoCompleteTextView:AutoCompleteTextView = binding.autoCompleteTextView;
        val routes: Array<String> = resources.getStringArray(com.example.transitapp.R.array.routes);
        val adapter = ArrayAdapter<String>(requireActivity(),
            R.layout.simple_dropdown_item_1line,routes);
        autoCompleteTextView.setAdapter(adapter);


        //adding routes to phone memory
        var myBusRoutes:TextView = binding.textViewMyBusRoutes;
//        loadInternalStorage(myBusRoutes);
        var addRoutesButton: Button = binding.buttonAddRoutes;
        addRoutesButton.setOnClickListener{
            myBusRoutes.post({
                myBusRoutes.append(autoCompleteTextView.text);
                myBusRoutes.append("\n");
                saveRouteToInternalStorage(myBusRoutes.text.toString());
                ACTIVITY.numberRoutes++;
                hideSoftKeyboard(requireActivity());
            })
        }

        ACTIVITY.getSavedRoutes(myBusRoutes,ACTIVITY.numberRoutes);

        return root
    }

    private fun saveRouteToInternalStorage(route: String){
        //opening file outputstream
        var fos = activity!!.openFileOutput(ACTIVITY.filename, MODE_PRIVATE);

        try {
            //writing to file, writing entire textview to file.Overwriting previous
            fos!!.writer().use { it.write(route) }
        } catch (e: IOException) { //catch errors
            e.printStackTrace()
        } finally {
            if (fos != null) {
                try {
                    fos.close() // close stream
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun loadInternalStorage(textView: TextView) {
        var fis = activity!!.openFileInput(ACTIVITY.filename); //open input stream with file.
        try {
            val isr = InputStreamReader(fis)
            val br = BufferedReader(isr)
            val sb = StringBuilder() //string to store
            var text: String?
            while (br.readLine().also { text = it } != null) {
                sb.append(text).append("\n")
//                textView.append(sb.toString());
            }
            textView.setText(sb.toString())
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            if (fis != null) {
                try {
                    fis.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(
            Activity.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        if (inputMethodManager.isAcceptingText) {
            inputMethodManager.hideSoftInputFromWindow(
                activity.currentFocus!!.windowToken,
                0
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}