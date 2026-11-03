package com.example.analyticsgrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// сколько колонок и сколько всего чисел
private const val COLUMNS = 3
private const val COUNT = 12

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

// генерируем список случайных чисел
fun makeNumbers(): List<Int> {
    return List(COUNT) { (1..999).random() }
}

@Composable
fun MainScreen() {
    // состояние экрана: список чисел
    var numbers by remember { mutableStateOf(makeNumbers()) }

    val sum = numbers.sum()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F4F7))
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.title),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Сумма: $sum",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
        )

        NumberGrid(numbers = numbers)

        Button(
            onClick = { numbers = makeNumbers() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(stringResource(R.string.refresh))
        }
    }
}

@Composable
fun NumberGrid(numbers: List<Int>) {
    Column {
        // разбиваем список на строки по COLUMNS чисел
        numbers.chunked(COLUMNS).forEach { rowNumbers ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowNumbers.forEach { number ->
                    NumberCell(
                        number = number,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun NumberCell(number: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(4.dp)
            .height(80.dp)
            .background(Color.White, RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = number.toString(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MaterialTheme {
        MainScreen()
    }
}