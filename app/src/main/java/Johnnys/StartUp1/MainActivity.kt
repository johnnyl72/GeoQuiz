package Johnnys.StartUp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.view.*
import org.w3c.dom.Text

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var prevButton: Button
    private lateinit var questionTextView: TextView


    private val questionBank = listOf (
        Question(R.string.question_oceans, true),
        Question(R.string.question_australia, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    private var currentIndex = 0
    private var totalCorrect = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.quest_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            trueButton.isEnabled = false;
            falseButton.isEnabled = false;
        }
        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            trueButton.isEnabled = false;
            falseButton.isEnabled = false;
        }
        nextButton.setOnClickListener {
            trueButton.isEnabled = true;
            falseButton.isEnabled = true;
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        prevButton.setOnClickListener {
            trueButton.isEnabled = true;
            falseButton.isEnabled = true;
            currentIndex = (currentIndex - 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }
    private fun updateQuestion() {
        if(currentIndex < 0) {
            currentIndex = 0;
        }
            val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer){
            totalCorrect = totalCorrect + 1
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        if(currentIndex == questionBank.size-1){
            var percent = (totalCorrect.toDouble() / questionBank.size.toDouble()) * 100
            Toast.makeText(this, "You got " + percent+ "% right!", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}