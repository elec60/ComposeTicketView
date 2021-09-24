package com.hashem.mousavi.composeticketview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hashem.mousavi.composeticketview.ui.theme.ComposeTicketViewTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTicketViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LazyColumn {
                        items(100) {
                            TicketView(
                                price = "EUR 1,000",
                                date = "Oct 20\n2021",
                                title = "Title",
                                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                                cornerRadius = 20.dp,
                                circleRadius = 10.dp,
                                percentOfCirclePosition = 0.4f
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TicketView(
    price: String,
    date: String,
    title: String,
    description: String,
    cornerRadius: Dp,
    circleRadius: Dp,
    percentOfCirclePosition: Float,
    modifier: Modifier = Modifier
) {

    Box(
        modifier
            .fillMaxSize()
            .wrapContentHeight()
            .background(Color.Black)
            .padding(start = 20.dp, top = 20.dp, end = 20.dp)
    ) {
        Canvas(modifier = modifier.matchParentSize()) {
            val clipPath = Path().apply {
                lineTo(size.width * percentOfCirclePosition - circleRadius.toPx(), 0f)
                arcTo(
                    rect = Rect(
                        offset = Offset(
                            size.width * percentOfCirclePosition - circleRadius.toPx(),
                            -circleRadius.toPx()
                        ),
                        size = Size(circleRadius.toPx() * 2, circleRadius.toPx() * 2)
                    ),
                    -180f,
                    -180f,
                    false
                )
                lineTo(size.width, 0f)
                lineTo(size.width, size.height)
                arcTo(
                    rect = Rect(
                        offset = Offset(
                            size.width * percentOfCirclePosition - circleRadius.toPx(),
                            size.height - circleRadius.toPx()
                        ),
                        size = Size(circleRadius.toPx() * 2, circleRadius.toPx() * 2)
                    ),
                    0f,
                    -180f,
                    false
                )
                lineTo(0f, size.height)
                close()
            }

            clipPath(clipPath) {
                drawRoundRect(
                    color = Color(
                        Random.nextInt(100, 256),
                        Random.nextInt(100, 256),
                        Random.nextInt(100, 256)
                    ),
                    cornerRadius = CornerRadius(cornerRadius.toPx(), cornerRadius.toPx())
                )
                drawLine(
                    color = Color.Black,
                    start = Offset(size.width * percentOfCirclePosition, 0f),
                    end = Offset(
                        size.width * percentOfCirclePosition,
                        size.height
                    ),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8.dp.toPx(), 4.dp.toPx()))
                )
            }
        }

        Row {
            Column(
                modifier = modifier
                    .padding(10.dp)
                    .weight(percentOfCirclePosition)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = price)
                Spacer(modifier = modifier.height(8.dp))
                Text(text = date)
            }

            Column(
                modifier = modifier
                    .padding(10.dp)
                    .weight(1f - percentOfCirclePosition),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = title, style = TextStyle(fontSize = 20.sp))
                Spacer(modifier = modifier.height(4.dp))
                Text(text = description)
            }
        }


    }

}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LazyColumn{
        items(100) {
            TicketView(
                price = "EUR 1,000",
                date = "Oct 20\n2021",
                title = "Title",
                description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                cornerRadius = 20.dp,
                circleRadius = 10.dp,
                percentOfCirclePosition = 0.4f
            )
        }
    }
}