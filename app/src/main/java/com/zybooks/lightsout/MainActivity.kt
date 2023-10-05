package com.zybooks.lightsout

import android.os.Bundle
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children


class MainActivity : AppCompatActivity() {

    private lateinit var game: LightsOutGame
    private lateinit var lightGridLayout: GridLayout
    private var lightOnColor = 1
    private var lightOffColor = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lightGridLayout = findViewById(R.id.light_grid)

        // Add the same click handler to all grid buttons
        for (gridButton in lightGridLayout.children) {
            gridButton.setOnClickListener(this::onLightButtonClick)
        }

        // Set the long click listener for the top-left button
        val topLeftButton = lightGridLayout.getChildAt(0) // Assuming the top-left button is at index 0
        topLeftButton.setOnLongClickListener {
            // Cheat by turning all lights off
            game.turnAllLightsOff()

            // Update button colors after cheating
            setButtonColors()
            if (game.isGameOver) {
                Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show()
            }
            // Return true to indicate that the long click event is consumed
            true
        }

        lightOnColor = ContextCompat.getColor(this, R.color.yellow)
        lightOffColor = ContextCompat.getColor(this, R.color.black)

        game = LightsOutGame()
        startGame()
    }

    private fun startGame() {
        game.newGame()
        setButtonColors()
    }

    private fun onLightButtonClick(view: View) {

        // Find the button's row and col
        val buttonIndex = lightGridLayout.indexOfChild(view)
        val row = buttonIndex / GRID_SIZE
        val col = buttonIndex % GRID_SIZE

        game.selectLight(row, col)
        setButtonColors()

        // Congratulate the user if the game is over
        if (game.isGameOver) {
            Toast.makeText(this, R.string.congrats, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setButtonColors() {

        // Set all buttons' background color
        for (buttonIndex in 0 until lightGridLayout.childCount) {
            val gridButton = lightGridLayout.getChildAt(buttonIndex)

            // Find the button's row and col
            val row = buttonIndex / GRID_SIZE
            val col = buttonIndex % GRID_SIZE

            if (game.isLightOn(row, col)) {
                gridButton.setBackgroundColor(lightOnColor)
                gridButton.contentDescription = getString(R.string.light_on)
            } else {
                gridButton.setBackgroundColor(lightOffColor)
                gridButton.contentDescription = getString(R.string.light_off)
            }
        }
    }

    fun onNewGameClick(view: View) {
        startGame()
    }
}