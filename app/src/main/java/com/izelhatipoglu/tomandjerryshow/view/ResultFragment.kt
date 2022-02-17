package com.izelhatipoglu.tomandjerryshow.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import com.izelhatipoglu.tomandjerryshow.R
import com.izelhatipoglu.tomandjerryshow.utils.Constants
import kotlinx.android.synthetic.main.fragment_result.*
import kotlin.system.exitProcess


class ResultFragment : Fragment() {
    var finalScore = Constants.lastScore

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
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        handleClick()
            tv_score_game.text = "Skor: $finalScore"
            Constants.continueTime = 30
            Constants.countinueScore = 0

    }

    private fun handleClick(){
        bt_menu.setOnClickListener {
            val action = ResultFragmentDirections.actionResultFragmentToFirstFragment()
            NavHostFragment.findNavController(this).navigate(action)
        }

        bt_exit.setOnClickListener {
            activity?.moveTaskToBack(true);
            exitProcess(-1)
        }
    }


}