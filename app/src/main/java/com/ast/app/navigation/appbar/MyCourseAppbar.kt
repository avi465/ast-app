package com.ast.app.navigation.appbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.ast.app.presentation.bottomsheet.CategoryBottomSheet
import com.ast.app.presentation.bottomsheet.availableCourses
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCourseTopAppbar() {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    val selectedCourse by remember { mutableStateOf(availableCourses.first()) }
    var showSheet by remember { mutableStateOf(false) }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState,
            shape = RectangleShape,
            containerColor = Color.White,
        ) {
            CategoryBottomSheet(
                courses = availableCourses,
                selectedCourse = selectedCourse.toString(),
                onCourseSelected = {
                    coroutineScope.launch {
                        sheetState.hide()
                        showSheet = false
                    }
                }
            )
        }
    }

    TopAppBar(
        modifier = Modifier.shadow(elevation = 2.dp),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable { showSheet = true }
            ) {
                Text(selectedCourse.name, style = MaterialTheme.typography.titleMedium)
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Change Course",
                )
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
                    Icons.Outlined.MoreVert,
                    contentDescription = null,
                )
            }
        }
    )
}

