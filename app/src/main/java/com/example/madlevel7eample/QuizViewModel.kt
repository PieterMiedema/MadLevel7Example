package com.example.madlevel7eample

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "FIRESTORE"
    private val quizRepository: QuizRepository = QuizRepository()

    val quiz: LiveData<Quiz> = quizRepository.quiz

    val createSuccess: LiveData<Boolean> = quizRepository.createSuccess

    private val _errorText: MutableLiveData<String> = MutableLiveData()
    val errorText: LiveData<String>
        get() = _errorText

    fun getQuiz() {
        viewModelScope.launch {
            try {
                quizRepository.getQuiz()
            } catch (ex: QuizRepository.QuizRetrievalError) {
                val errorMsg = "Something went wrong while retrieving quiz"
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.value = errorMsg
            }
        }
    }

    fun createQuiz(question: String, answer: String) {
        val quiz = Quiz(question, answer)
        viewModelScope.launch {
            try {
                quizRepository.createQuiz(quiz)
            } catch (ex: QuizRepository.QuizSaveError) {
                val errorMsg = "Something went wrong while saving quiz"
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.value = errorMsg
            }
        }
    }

    fun isAnswerCorrect(answer: String): Boolean {
        return quiz.value?.answer?.toLowerCase(Locale.ROOT) ==   answer.toLowerCase(Locale.ROOT)
    }

}