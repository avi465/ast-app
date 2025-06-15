package com.ast.app.presentation.application.home.feedback

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Feedback() {
    Box(
        modifier = Modifier
            .padding(vertical = 32.dp)
            .fillMaxSize()
            .height(224.dp)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Invite friends to get ${'\u20B9'}250 off",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Invite friend to Advanced Study Tutorials and get ${'\u20B9'}250 off on their first purchase",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight(400),
                fontSize = 14.sp
            )
            val inviteCodeText = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight(400),
                        fontSize = 14.sp
                    )
                ) {
                    append("Copy your invite code ")
                }

                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight(600),
                        fontSize = 14.sp
                    )
                ) {
                    append("Zb67ghi8")
                }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = inviteCodeText,
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    imageVector = Icons.Filled.CopyAll,
                    contentDescription = "copy",
                    Modifier.size(20.dp)
                )
            }


            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Invite")
            }
        }

    }
}