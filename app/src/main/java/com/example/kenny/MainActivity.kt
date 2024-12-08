package com.example.kenny

import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.kenny.R

import com.example.kenny.databinding.ActivityMainBinding
import com.google.android.material.color.utilities.Score
import kotlin.random.Random

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    private var score = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler= Handler()


        val timeText: TextView=binding.timeText
        val scoreText: TextView=binding.scoreText
        val kennyİmage: ImageView=binding.kennyImage


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        binding.scoreText.text = "Score:  $score"

        binding.kennyImage.setOnClickListener(){
            newScore() }
        startGame(timeText,scoreText, kennyİmage)

    }

    private fun startGame(timeText:TextView,scoreText: TextView,kennyImage: ImageView) {


        object : CountDownTimer(15000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timeText.setText("Kalan süre: " + millisUntilFinished / 1000)
            }

            override fun onFinish() {
                timeText.text = "Time s up"
                handler.removeCallbacks(runnable)
                showRestartDialog()
            }

        }.start()

        moveKenny(kennyImage)
    }

    private fun newScore() {
        score++
        binding.scoreText.text = "Score: $score"
    }
    private fun moveKenny(kennyImage: ImageView) {
        runnable = object : Runnable {
            override fun run() {
                val randomX = Random.nextInt(binding.root.width-kennyImage.width)
                val randomY = Random.nextInt(binding.root.height-kennyImage.height)
                kennyImage.x = randomX.toFloat()
                kennyImage.y = randomY.toFloat()

                handler.postDelayed(this, 800)
            }
        }
        handler.post(runnable)

    }

    private fun showRestartDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Game Over")
        alert.setMessage("Restart the game?")
        alert.setPositiveButton("Yes") { _, _ ->
            score=0
            binding.scoreText.text="Score=$score"
            startGame(binding.timeText, binding.scoreText, binding.kennyImage)}
        alert.setNegativeButton("No") { _, _ ->
            finish()
        }
        alert.show()
    }
}















