package com.izelhatipoglu.tomandjerryshow.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import com.izelhatipoglu.tomandjerryshow.R
import com.izelhatipoglu.tomandjerryshow.splash.SplashScreenActivity
import kotlinx.android.synthetic.main.fragment_first.*
import kotlin.system.exitProcess

class FirstFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.moveTaskToBack(true);
                exitProcess(-1)
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getInteger(R.integer.saved_high_score_default_key)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue)

        tv_top_score_one.text = highScore.toString()

        handleClick()
    }

    private fun handleClick() {
        bt_mode_one.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToTomAndJerryGame()
            Navigation.findNavController(it).navigate(action)
        }

    }
}