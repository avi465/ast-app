package com.ast.app.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import com.ast.app.R

@Composable
fun PrivacyPolicy() {
    val privacyAndPolicyText = buildAnnotatedString {
        append("By signing up you agree to our ")
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append("Privacy Policy")
        }
        append(" and ")
        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append("Terms of Use")
        }
    }
    Column {
        Divider()
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_s)))
        Text(
            text = privacyAndPolicyText,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(
                start = dimensionResource(
                    id = R.dimen.padding_xs
                ), end = dimensionResource(id = R.dimen.padding_xs)
            )
        )
    }
}

@Composable
fun OrWithDivider() {
    Box(
        modifier = Modifier, contentAlignment = Alignment.Center
    ) {
        Divider()
        Surface {
            Text(
                text = "or",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surface
                    )
                    .padding(
                        start = dimensionResource(id = R.dimen.padding_s),
                        end = dimensionResource(id = R.dimen.padding_s)
                    )
            )
        }
    }
}