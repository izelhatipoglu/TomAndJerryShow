package com.izelhatipoglu.tomandjerryshow.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.izelhatipoglu.tomandjerryshow.R
import kotlinx.android.synthetic.main.fragment_victory.*
import java.util.*
import kotlin.system.exitProcess


class VictoryFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_victory, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getInteger(R.integer.saved_high_score_default_key)
        val highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue)

        tv_new_top_score.text=highScore.toString()

        handleClick()
    }

    private fun handleClick(){
        bt_menu_victory.setOnClickListener {
            val action = VictoryFragmentDirections.actionVictoryFragmentToFirstFragment()
            NavHostFragment.findNavController(this@VictoryFragment).navigate(action)
        }

        bt_exit_victory.setOnClickListener {
            activity?.moveTaskToBack(true);
            exitProcess(-1)
        }
    }
}