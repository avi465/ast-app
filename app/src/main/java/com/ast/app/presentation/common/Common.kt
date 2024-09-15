package com.ast.app.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sensors
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ast.app.R

@Composable
fun PrivacyPolicy() {
    val privacyAndPolicyText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        ) {
            append("By signing up you agree to our ")
        }

        withStyle(
            style = SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary,
            )
        ) {
            append("Privacy Policy")
        }
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        ) {
            append(" and ")
        }
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
        HorizontalDivider()
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
        HorizontalDivider()
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

@Composable
fun LiveLabel(modifier: Modifier){
    Surface(
        color = Color.Red,
        modifier = modifier
            .padding(16.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Sensors,
                contentDescription = "live",
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                text = "LIVE",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
            )
        }
    }
}