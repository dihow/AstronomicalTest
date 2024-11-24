package com.example.astronomicaltest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.astronomicaltest.endscreen.EndScreen
import com.example.astronomicaltest.startscreen.StartScreen
import com.example.astronomicaltest.testscreen.TestScreen
import com.example.astronomicaltest.ui.theme.AstronomicalTestTheme
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerScope

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AstronomicalTestTheme {
                Program()
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun Program() {
    val questions = remember {
        mutableListOf(
            Question("Самая большая планета в Солнечной системе?",
                listOf("Земля", "Меркурий", "Юпитер"), "Юпитер"),
            Question("Самая холодная планета в Солнечной системе?",
                listOf("Нептун", "Уран", "Сатурн"), "Уран"),
            Question("Как называется путь, по которому движется небесное тело?",
                listOf("Перигелий", "Орбита", "Радиус"), "Орбита"),
            Question("Сколько планет в Солнечной системе?",
                listOf("9", "7", "8"), "8"),
            Question("Каким по счёту от Солнца является Марс?",
                listOf("3", "4", "5"), "4"),
            Question("Как называется галактика, в которой находится Солнечная система?",
                listOf("Млечный Путь", "Туманность Андромеды", "Большое Магелланово Облако"), "Млечный Путь"),
            Question("Как называется сверхмассивная чёрная дыра, расположенная в центре Млечного Пути?",
                listOf("TON 618", "Скорпион V", "Стрелец A*"), "Стрелец A*"),
            Question("Какая планета вращается вокруг своей оси медленнее, чем вокруг солнца?",
                listOf("Юпитер", "Венера", "Меркурий"), "Венера"),
            Question("Кто изобрёл первый телескоп?",
                listOf("Галилео Галилей", "Николай Коперник", "Исаак Ньютон"), "Галилео Галилей"),
            Question("На какой планете находится самая высокая гора в Солнечной системе?",
                listOf("Земля", "Меркурий", "Марс"), "Марс"),
            Question("Приблизительная температура поверхности Солнца?",
                listOf("5780 K", "3890 K", "8420 K"), "5780 K"),
            Question("Приблизительный возраст Земли?",
                listOf("2,71 млрд лет", "6000 лет", "4,54 млрд лет"), "4,54 млрд лет"),
            Question("Самая горячая планета в Солнечной системе?",
                listOf("Венера", "Меркурий", "Марс"), "Венера"),
            Question("В каком году был запущен первый искусственный спутник?",
                listOf("1951", "1957", "1962"), "1957"),
            Question("В каком созвездии находится Полярная звезда?",
                listOf("Малая Медведица", "Большая Медведица", "Пегас"), "Малая Медведица"),
        )
    }
    val currentQuestionId = remember { mutableStateOf(0) }
    val correctAnswers = remember { mutableStateOf(0) }
    val currentFrame = remember { mutableStateOf(0) }
    //val answers: MutableList<Answer> = mutableListOf()
    val answers = remember { mutableStateListOf<Answer>() }



    if (currentFrame.value == 0) {
        StartScreen(onStartButtonTapped = {
            currentFrame.value = 1
        })
    }

    if (currentFrame.value == 1) {
        val option1Text = questions[currentQuestionId.value].answers[0]
        val option2Text = questions[currentQuestionId.value].answers[1]
        val option3Text = questions[currentQuestionId.value].answers[2]
        val option1Color = remember { mutableStateOf(questions[currentQuestionId.value].colors[0]) }
        val option2Color = remember { mutableStateOf(questions[currentQuestionId.value].colors[1]) }
        val option3Color = remember { mutableStateOf(questions[currentQuestionId.value].colors[2]) }

        TestScreen(titleTextContent = "${currentQuestionId.value + 1} вопрос из 15",
            questionTextContent = questions[currentQuestionId.value].text,
            option1ButtonTextTextContent = option1Text,
            option2ButtonTextTextContent = option2Text,
            option3ButtonTextTextContent = option3Text,
            option1ButtonTextColor = option1Color.value,
            option2ButtonTextColor = option2Color.value,
            option3ButtonTextColor = option3Color.value,
            nextButtonTextTextContent = if (currentQuestionId.value < questions.size - 1) "Далее" else "Завершить",
            onOption1ButtonTapped = {
                if (!questions[currentQuestionId.value].showAnswer) {
                    if (option1Text == questions[currentQuestionId.value].correctAnswer) {
                        option1Color.value = Color.Green
                        questions[currentQuestionId.value].colors[0] = Color.Green
                        correctAnswers.value++
                    } else {
                        option1Color.value = Color.Red
                        questions[currentQuestionId.value].colors[0] = Color.Red
                        if (option2Text == questions[currentQuestionId.value].correctAnswer) {
                            option2Color.value = Color.Green
                            questions[currentQuestionId.value].colors[1] = Color.Green
                        } else {
                            option3Color.value = Color.Green
                            questions[currentQuestionId.value].colors[2] = Color.Green
                        }
                    }
                    currentQuestionId.value++
                    currentQuestionId.value--
                    questions[currentQuestionId.value].showAnswer = true
                    answers.add(Answer(option1Text, option1Color.value))
                }
            },
            onOption2ButtonTapped = {
                if (!questions[currentQuestionId.value].showAnswer) {
                    if (option2Text == questions[currentQuestionId.value].correctAnswer) {
                        option2Color.value = Color.Green
                        questions[currentQuestionId.value].colors[1] = Color.Green
                        correctAnswers.value++
                    } else {
                        option2Color.value = Color.Red
                        questions[currentQuestionId.value].colors[1] = Color.Red
                        if (option1Text == questions[currentQuestionId.value].correctAnswer) {
                            option1Color.value = Color.Green
                            questions[currentQuestionId.value].colors[0] = Color.Green
                        } else {
                            option3Color.value = Color.Green
                            questions[currentQuestionId.value].colors[2] = Color.Green
                        }
                    }
                    currentQuestionId.value++
                    currentQuestionId.value--
                    questions[currentQuestionId.value].showAnswer = true
                    answers.add(Answer(option2Text, option2Color.value))
                }
            },
            onOption3ButtonTapped = {
                if (!questions[currentQuestionId.value].showAnswer) {
                    if (option3Text == questions[currentQuestionId.value].correctAnswer) {
                        option3Color.value = Color.Green
                        questions[currentQuestionId.value].colors[2] = Color.Green
                        correctAnswers.value++
                    } else {
                        option3Color.value = Color.Red
                        questions[currentQuestionId.value].colors[2] = Color.Red
                        if (option1Text == questions[currentQuestionId.value].correctAnswer) {
                            option1Color.value = Color.Green
                            questions[currentQuestionId.value].colors[0] = Color.Green
                        } else {
                            option2Color.value = Color.Green
                            questions[currentQuestionId.value].colors[1] = Color.Green
                        }
                    }
                    currentQuestionId.value++
                    currentQuestionId.value--
                    questions[currentQuestionId.value].showAnswer = true
                    answers.add(Answer(option3Text, option3Color.value))
                }
            },
            onPreviousButtonTapped = {
                if (currentQuestionId.value != 0) {
                    currentQuestionId.value--
                    option1Color.value = questions[currentQuestionId.value].colors[0]
                    option2Color.value = questions[currentQuestionId.value].colors[1]
                    option3Color.value = questions[currentQuestionId.value].colors[2]
                }
            },
            onNextButtonTapped = {
                if (currentQuestionId.value < questions.size - 1) {
                    currentQuestionId.value++
                    option1Color.value = questions[currentQuestionId.value].colors[0]
                    option2Color.value = questions[currentQuestionId.value].colors[1]
                    option3Color.value = questions[currentQuestionId.value].colors[2]
                }
                else if (answers.size == questions.size) {
                    currentFrame.value = 2
                }
            }
        )
    }

    if (currentFrame.value == 2) {
        EndScreen(
            titleTextContent = "Результат: ${correctAnswers.value} правильных ответов из 15",
            answer1TextContent = "1: " + answers[0].answer,
            answer2TextContent = "2: " + answers[1].answer,
            answer3TextContent = "3: " + answers[2].answer,
            answer4TextContent = "4: " + answers[3].answer,
            answer5TextContent = "5: " + answers[4].answer,
            answer6TextContent = "6: " + answers[5].answer,
            answer7TextContent = "7: " + answers[6].answer,
            answer8TextContent = "8: " + answers[7].answer,
            answer9TextContent = "9: " + answers[8].answer,
            answer10TextContent = "10: " + answers[9].answer,
            answer11TextContent = "11: " + answers[10].answer,
            answer12TextContent = "12: " + answers[11].answer,
            answer13TextContent = "13: " + answers[12].answer,
            answer14TextContent = "14: " + answers[13].answer,
            answer15TextContent = "15: " + answers[14].answer,
            answer1Color = answers[0].color,
            answer2Color = answers[1].color,
            answer3Color = answers[2].color,
            answer4Color = answers[3].color,
            answer5Color = answers[4].color,
            answer6Color = answers[5].color,
            answer7Color = answers[6].color,
            answer8Color = answers[7].color,
            answer9Color = answers[8].color,
            answer10Color = answers[9].color,
            answer11Color = answers[10].color,
            answer12Color = answers[11].color,
            answer13Color = answers[12].color,
            answer14Color = answers[13].color,
            answer15Color = answers[14].color,
            onEndButtonTapped = {
            Runtime.getRuntime().exit(0)
        })
    }
}

data class Question(val text: String, val answers: List<String>, val correctAnswer: String,
    var colors: MutableList<Color> = MutableList(answers.size) {Color.White},
    var showAnswer: Boolean = false)

data class Answer(val answer: String, val color: Color)