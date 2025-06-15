package com.ast.app.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun QuizScreen(quizViewModel: QuizViewModel = viewModel()) {
    val questions by quizViewModel.questions.collectAsState()
    val currentIndex by quizViewModel.currentQuestionIndex.collectAsState()

    if (questions.isNotEmpty()) {
        val currentQuestion = questions[currentIndex]

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Question ${currentIndex + 1}/${questions.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = currentQuestion.question, style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(16.dp))

            currentQuestion.options.forEachIndexed { index, option ->
                val isSelected = currentQuestion.selectedAnswerIndex == index
                Button(
                    onClick = { quizViewModel.selectAnswer(index) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(option)
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (currentIndex > 0) {
                    OutlinedButton(onClick = { quizViewModel.goToPreviousQuestion() }) {
                        Text("Previous")
                    }
                }

                if (currentIndex < questions.size - 1) {
                    Button(onClick = { quizViewModel.goToNextQuestion() }) {
                        Text("Next")
                    }
                } else {
                    Button(onClick = {
                        val score = quizViewModel.getScore()
//                        Toast.makeText(LocalContext.current, "Score: $score", Toast.LENGTH_LONG).show()
                    }) {
                        Text("Submit")
                    }
                }
            }
        }
    }
}
