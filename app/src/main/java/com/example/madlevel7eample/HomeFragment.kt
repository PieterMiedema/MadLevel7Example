package com.example.madlevel7eample

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private val viewModel: QuizViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()

        //always retrieve quiz  when screen is shown
        viewModel.getQuiz()

        btn_create_quiz.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_createQuizFragment)
        }

        viewModel.quiz.observe(viewLifecycleOwner, Observer {
            //make button visible and clickable
            if (!it.answer.isBlank() || !it.answer.isBlank()) {
                btn_start_quiz.alpha = 1.0f
                btn_start_quiz.isClickable = true

                btn_start_quiz.setOnClickListener {
                    navController.navigate(R.id.action_homeFragment_to_quizFragment)
                }
            }
        })
    }
}