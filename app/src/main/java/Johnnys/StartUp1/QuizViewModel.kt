package Johnnys.StartUp1

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel() {
    var currentIndex = 0
    var isCheater = false
    val questionBank = listOf(
        Question(R.string.question_prince, false),
        Question(R.string.question_meryl, false),
        Question(R.string.question_mm, false),
        Question(R.string.question_gin, true),
        Question(R.string.question_unicorn, true),
        Question(R.string.question_wall, true)
    )
    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer
    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId
    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
    fun moveToPrev() {
        currentIndex = (currentIndex - 1) % questionBank.size
    }
}
