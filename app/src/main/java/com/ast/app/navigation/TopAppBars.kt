package com.ast.app.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.ast.app.R

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun OnBoardTopAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
//    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    MediumTopAppBar(
        title = {
            Text(
                text = stringResource(currentScreenTitle),
//                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Medium),
            )
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back_button
                        )
                    )
                }
            }
        },
//        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstAppTopAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(text = "Home")
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back_button
                        )
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Outlined.Search,
                    contentDescription = null,
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Outlined.Bookmarks,
                    contentDescription = null,
                )
            }
            IconButton(onClick = { /* do something */ }) {
                Icon(
                    Icons.Outlined.MoreVert,
                    contentDescription = null,
                )
            }
        }
    )
}