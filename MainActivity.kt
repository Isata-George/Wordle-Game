package com.example.wordlegame

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var wordToGuess = FourLetterWordList.getRandomFourLetterWord()
    private var numberOfGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val guessInput = findViewById<EditText>(R.id.guessInput)
        val checkButton = findViewById<Button>(R.id.checkButton)
        val resetButton = findViewById<Button>(R.id.resetButton)
        val resultText = findViewById<TextView>(R.id.resultText)
        val guessesText = findViewById<TextView>(R.id.guessesText)

        checkButton.setOnClickListener {

            val guess = guessInput.text.toString().uppercase()

            if (guess.length != 4) {
                resultText.text = "Please enter a 4-letter word."
                return@setOnClickListener
            }

            val result = checkGuess(wordToGuess, guess)

            numberOfGuesses++

            resultText.text = "$guess: $result"

            guessesText.text =
                "Guesses remaining: ${3 - numberOfGuesses}"

            guessInput.text.clear()

            // After 3 guesses, end the game
            if (numberOfGuesses == 3) {

                resultText.text =
                    "$guess: $result\n\nThe word was: $wordToGuess"

                checkButton.isEnabled = false
                guessInput.isEnabled = false

                resetButton.visibility = Button.VISIBLE
            }
        }

        resetButton.setOnClickListener {

            // Get a new random word
            wordToGuess =
                FourLetterWordList.getRandomFourLetterWord()

            // Reset the number of guesses
            numberOfGuesses = 0

            // Clear the input and result
            guessInput.text.clear()
            resultText.text = ""

            // Reset the guesses counter
            guessesText.text = "Guesses remaining: 3"

            // Enable the game again
            guessInput.isEnabled = true
            checkButton.isEnabled = true

            // Hide Reset button
            resetButton.visibility = Button.GONE
        }
    }

    /**
     * O = correct letter in the correct position
     * + = correct letter in the wrong position
     * X = letter is not in the target word
     */
    private fun checkGuess(
        wordToGuess: String,
        guess: String
    ): String {

        var result = ""

        for (i in 0..3) {

            if (guess[i] == wordToGuess[i]) {
                result += "O"
            } else if (guess[i] in wordToGuess) {
                result += "+"
            } else {
                result += "X"
            }
        }

        return result
    }
}