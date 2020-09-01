package Johnnys.StartUp1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.view.*
import org.w3c.dom.Text

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    private var totalCorrect = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        prevButton = findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.quest_text_view)
        cheatButton = findViewById(R.id.cheat_button)

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
            quizViewModel.moveToNext()
            updateQuestion()
        }
        prevButton.setOnClickListener {
            trueButton.isEnabled = true;
            falseButton.isEnabled = true;
            quizViewModel.moveToPrev()
            updateQuestion()
        }
        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity,
                answerIsTrue)

            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
        updateQuestion()
    }
    private fun updateQuestion() {
        if(quizViewModel.currentIndex < 0) {
            quizViewModel.currentIndex = 0
        }
            val questionTextResId = quizViewModel.questionBank.get(quizViewModel.currentIndex).textResId
        questionTextView.setText(questionTextResId)
    }
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
//        val messageResId = if (userAnswer == correctAnswer){
//            totalCorrect = totalCorrect + 1
//            R.string.correct_toast
//        } else {
//            R.string.incorrect_toast
//        }
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> {
                R.string.correct_toast
            } else -> {
                R.string.incorrect_toast
            }
        }
        if(userAnswer == correctAnswer){
            totalCorrect = totalCorrect +1
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        if(quizViewModel.currentIndex == quizViewModel.questionBank.size-1){
            var percent = (totalCorrect.toDouble() / quizViewModel.questionBank.size.toDouble()) * 100
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
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }
    override fun onActivityResult(requestCode: Int,
                                  resultCode: Int,
                                  data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater =
                data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?:
                        false
        }
    }
}