package com.izelhatipoglu.tomandjerryshow.view


import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.izelhatipoglu.tomandjerryshow.R
import com.izelhatipoglu.tomandjerryshow.utils.Constants
import kotlinx.android.synthetic.main.fragment_tom_and_jerry_game.*
import kotlinx.android.synthetic.main.stop_game_menu.*

class TomAndJerryGame : Fragment() {
    private var imageList: ArrayList<ImageView> = ArrayList()
    private lateinit var timer: CountDownTimer
    var score = Constants.countinueScore
    var handler = Handler()
    var runnable = Runnable { }

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tom_and_jerry_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val defaultValue = resources.getInteger(R.integer.saved_high_score_default_key)
        highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), defaultValue)

        iv_pause_mode_one.setOnClickListener { stopGame() }
        tv_top_score_mode_one.text = highScore.toString()

        tv_game_time.visibility = View.VISIBLE
        addImageList()
        hideImages()
        tv_score_mode_one.text = Constants.countinueScore.toString()

        timer = object : CountDownTimer((Constants.continueTime * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                Constants.continueTime = (millisUntilFinished / 1000).toInt()
                println("time ${Constants.continueTime}")
                val time2 = (millisUntilFinished / 1000).toString()
                tv_game_time.text = "Time: $time2"
            }

            override fun onFinish() {
                handler.removeCallbacks(runnable)
                topScore()
            }
        }.start()

    }


    private fun addImageList() {
        imageList.add(jerryImageView1)
        imageList.add(jerryImageView2)
        imageList.add(jerryImageView3)
        imageList.add(jerryImageView4)
        imageList.add(jerryImageView5)
        imageList.add(jerryImageView6)
        imageList.add(jerryImageView7)
        imageList.add(jerryImageView8)
        imageList.add(jerryImageView9)
        imageList.add(jerryImageView10)
        imageList.add(jerryImageView11)
        imageList.add(jerryImageView12)
        imageList.add(jerryImageView13)
        imageList.add(jerryImageView14)
        imageList.add(jerryImageView15)
        imageList.add(jerryImageView16)
    }

    private fun jerryImage() {
        for (i in imageList) {
            i.setImageResource(R.drawable.jerry)
            i.setOnClickListener {
                i.visibility = View.INVISIBLE
                score += 1
                Constants.countinueScore = score
                tv_score_mode_one.text = Constants.countinueScore.toString()
            }
        }
    }

    private fun littleMouseImage() {
        for (i in imageList) {
            i.setImageResource(R.drawable.littlemouse)
            i.setOnClickListener {
                i.visibility = View.INVISIBLE
                score -= 1
                Constants.countinueScore = score
                tv_score_mode_one.text = Constants.countinueScore.toString()
            }
        }
    }

    private fun littleDog() {

        for (i in imageList) {
            i.setImageResource(R.drawable.littledog)
            i.setOnClickListener {
                timer.cancel()
                tv_game_time.visibility = View.INVISIBLE
                findNavController().popBackStack(R.id.tomAndJerryGame, true)
                findNavController().navigate(R.id.animationFragment)
            }
        }
    }

    private fun controlScore() {
        val rnd = (1..10).random()
        if (rnd == 3 || rnd == 5) {
            littleMouseImage()
        } else if (rnd == 7) {
            littleDog()
        } else
            jerryImage()
    }

    private fun hideImages() {

        runnable = object : Runnable {
            override fun run() {
                controlScore()
                for (i in imageList) {
                    i.visibility = View.INVISIBLE
                }
                val randomImage = (0..15).random()
                imageList[randomImage].visibility = View.VISIBLE

                handler.postDelayed(runnable, 500)
            }
        }
        handler.post(runnable)
    }

    private fun topScore() {
        findNavController().popBackStack(R.id.tomAndJerryGame, true)

        if (score <= highScore) {
            Constants.lastScore = score
            findNavController().navigate(R.id.resultFragment)

        } else {

            val sharedPref2 = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
            with(sharedPref2.edit()) {
                putInt(getString(R.string.saved_high_score_key), score)
                apply()
            }

            findNavController().navigate(R.id.victoryFragment)
        }

        score = 0
        tv_score_mode_one.text = "0"
        Constants.continueTime = 30
        Constants.countinueScore = 0
    }


    private fun stopGame(){
        gridLayout.visibility = View.INVISIBLE
        timer.cancel()
        val mDialogView = LayoutInflater.from(view?.context).inflate(R.layout.stop_game_menu, null)
        val mBuilder = AlertDialog.Builder(view?.context).setView(mDialogView).show()
        mBuilder.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mBuilder.setCancelable(false)
        mBuilder.iv_home.setOnClickListener {
            mBuilder.dismiss()
            findNavController().popBackStack(R.id.tomAndJerryGame, true)
            findNavController().navigate(R.id.firstFragment)
            score = 0
            tv_score_mode_one.text = "0"
            Constants.continueTime = 30
            Constants.countinueScore = 0
        }

        mBuilder.iv_countinue.setOnClickListener {
            gridLayout.visibility = View.VISIBLE
            mBuilder.dismiss()
            timer = object : CountDownTimer((Constants.continueTime*1000).toLong(),1000){
                override fun onTick(p0: Long) {
                    tv_game_time.text = (p0/1000).toString()
                    Constants.continueTime = (p0/1000).toInt()
                    System.out.println("Sabit: " + Constants.continueTime.toString())
                }

                override fun onFinish() {
                    handler.removeCallbacks(runnable)
                    topScore()

                }
            }
            timer.start()

        }

    }


}