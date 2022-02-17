package com.izelhatipoglu.tomandjerryshow.view


import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.izelhatipoglu.tomandjerryshow.R
import com.izelhatipoglu.tomandjerryshow.utils.Constants
import kotlinx.android.synthetic.main.fragment_tom_and_jerry_game.*
import kotlinx.android.synthetic.main.stop_game_menu.*


class AnimationFragment : Fragment() {

    var handler = Handler()
    var runnable = Runnable { }
    private lateinit var timer : CountDownTimer
    var highScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                stopGame()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_animation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        backToGame()

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getInteger(R.integer.saved_high_score_default_key)
        highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue)

    }

    private fun backToGame(){
       timer = object : CountDownTimer((Constants.animationTime*1000).toLong(),1000,){
           override fun onTick(p0: Long) {
            Constants.continueTime -= 1
           }
           override fun onFinish() {
               Constants.animationTime = 2
               if(Constants.continueTime <= 0){
                   fragmentManager?.commit {
                       topScore()
                       setReorderingAllowed(true)
                       // Replace whatever is in the fragment_container view with this fragment
                       replace<ResultFragment>(R.id.fragmentContainerView)
                   }
               }else{
                   fragmentManager?.commit {
                       setReorderingAllowed(true)
                       // Replace whatever is in the fragment_container view with this fragment
                       replace<TomAndJerryGame>(R.id.fragmentContainerView)
                   }
               }
           }
       }.start()
    }

    private fun topScore() {
        findNavController().popBackStack(R.id.animationFragment, true)

        if (Constants.countinueScore <= highScore) {
            Constants.lastScore = Constants.countinueScore
            findNavController().navigate(R.id.resultFragment)

        } else {

            val sharedPref2 = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref2.edit()) {
                putInt(getString(R.string.saved_high_score_key), Constants.countinueScore)
                apply()
            }

            findNavController().navigate(R.id.victoryFragment)
        }

        Constants.continueTime = 30
        Constants.countinueScore = 0
    }

    private fun stopGame(){
        timer.cancel()
        val mDialogView = LayoutInflater.from(view?.context).inflate(R.layout.stop_game_menu, null)
        val mBuilder = AlertDialog.Builder(view?.context).setView(mDialogView).show()
        mBuilder.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mBuilder.setCancelable(false)
        mBuilder.iv_home.setOnClickListener {
            mBuilder.dismiss()
            findNavController().popBackStack(R.id.animationFragment, true)
            findNavController().navigate(R.id.firstFragment)
            Constants.animationTime = 2
            Constants.continueTime = 30
            Constants.countinueScore = 0
        }

        mBuilder.iv_countinue.setOnClickListener {
            mBuilder.dismiss()
            timer = object : CountDownTimer((Constants.animationTime*1000).toLong(),1000){
                override fun onTick(p0: Long) {
                    Constants.continueTime -= 1
                }

                override fun onFinish() {
                    handler.removeCallbacks(runnable)
                    backToGame()
                    Constants.animationTime = 2

                }
            }
            timer.start()

        }

    }

}