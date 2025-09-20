package com.ast.app.presentation.application.course

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.PersonPin
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.ast.app.graphs.LiveClassScreen
import com.ast.app.model.Course
import com.ast.app.model.Lesson
import com.ast.app.model.Quiz
import com.ast.app.model.SlideModel
import com.ast.app.network.RESOURCE_ENDPOINT
import com.ast.app.presentation.application.shop.course.details.CourseDetailUiState
import com.ast.app.presentation.application.shop.course.details.CourseDetailViewModel
import com.ast.app.presentation.application.shop.course.details.CourseDetailsViewModelProviderFactory
import com.ast.app.presentation.common.CircularLoader
import com.ast.app.utils.UrlUtil
import com.ast.app.utils.downloadAndOpenPdf
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyCourseDetailsScreen(
    courseId: String,
    navController: NavController,
    courseDetailViewModel: CourseDetailViewModel = viewModel(
        factory = CourseDetailsViewModelProviderFactory(courseId = courseId)
    ),
    myCourseDetailViewModel: MyCourseDetailViewModel = viewModel(
        factory = MyCourseDetailViewModelProviderFactory(courseId = courseId)
    )
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val scrollState = rememberLazyListState()

    val courseDetailUiState by courseDetailViewModel.courseDetailUiState.collectAsState()
    val lessonState by myCourseDetailViewModel.lessonsState.collectAsState()
    val quizzesState by myCourseDetailViewModel.quizzesState.collectAsState()
    val slideState by myCourseDetailViewModel.slidesState.collectAsState()

    var lessonCached by remember { mutableStateOf(false) }
    var slideCached by remember { mutableStateOf(false) }
    var quizCached by remember { mutableStateOf(false) }

    when (courseDetailUiState) {
        is CourseDetailUiState.Success -> {
            val course = (courseDetailUiState as CourseDetailUiState.Success).course
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = scrollState
            ) {
                item {
                    if (course != null) {
                        HeaderComponent(course = course)
                    }
                }

                stickyHeader {
                    StickyHeaderComponent(
                        selectedTabIndex = selectedTabIndex,
                        onTabSelected = { index ->
                            selectedTabIndex = index
                            if (index == 1 && !slideCached) { //slide
                                myCourseDetailViewModel.fetchSlides(courseId)
                                slideCached = true
                            } else if (index == 2 && !quizCached) { //quiz
                                myCourseDetailViewModel.fetchQuizzes(courseId)
                                quizCached = true
                            }
                        }
                    )
                }

                when (selectedTabIndex) {
                    0 -> { // Chapters/Lessons
                        when (lessonState) {
                            is UiState.Loading -> {
                                item { LoadingComponent() }
                            }

                            is UiState.Error -> {
                                item { ErrorComponent((lessonState as UiState.Error).error) }
                            }

                            is UiState.Success -> {
                                val lessons = (lessonState as UiState.Success<List<Lesson>>).data
                                items(lessons) { lesson ->
                                    LectureCard(
                                        title = lesson.title,
                                        type = lesson.type,
                                        thumbnailUrl = lesson.images.let {
                                            if (it.isNotEmpty()) {
                                                RESOURCE_ENDPOINT + it[0] + "_landscapeSM.webp"
                                            } else {
                                                ""
                                            }
                                        } ?: "",
                                        onClick = {
                                            val lessonId = lesson.id
                                            navController.navigate(LiveClassScreen.LiveClassPlayer.route + "/${lessonId}") {
                                                launchSingleTop = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }

                    1 -> { // Slides
                        when (slideState) {
                            is UiState.Loading -> {
                                item { LoadingComponent() }
                            }

                            is UiState.Error -> {
                                item { ErrorComponent((slideState as UiState.Error).error) }
                            }

                            is UiState.Success -> {
                                val slides = (slideState as UiState.Success<List<SlideModel>>).data
                                items(slides) { slide ->
                                    SlideCard(slide = slide)
                                }
                            }
                        }

                    }

                    2 -> { // Quizzes
                        when (quizzesState) {
                            is UiState.Loading -> {
                                item { LoadingComponent() }
                            }

                            is UiState.Error -> {
                                item { ErrorComponent((quizzesState as UiState.Error).error) }
                            }

                            is UiState.Success -> {
                                val quizzes = (quizzesState as UiState.Success<List<Quiz>>).data
                                items(quizzes) { quiz ->
                                    QuizCard(quiz = quiz)
                                }
                            }
                        }
                    }
                }
            }
        }

        CourseDetailUiState.Loading -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularLoader()
            }
        }

        is CourseDetailUiState.Error -> {
            ErrorComponent((courseDetailUiState as CourseDetailUiState.Error).error)
        }
    }
}


@Composable
fun ErrorComponent(errorMessage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = errorMessage)
    }
}

@Composable
fun LoadingComponent(cardCount: Int = 1) {
    Column {
        repeat(cardCount) { index ->
            LectureCardLoading(
                shimmerOffset = index * 100f // Stagger shimmer for each card
            )
        }
    }
}

@Composable
fun LectureCardLoading(shimmerOffset: Float = 0f) {
    val isDark = MaterialTheme.colorScheme.surface.luminance() < 0.5f
    val shimmerColors = listOf(
        if (isDark) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.outlineVariant,
        MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
        if (isDark) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.outlineVariant
    )
    val transition = rememberInfiniteTransition(label = "cardShimmerAnimation")
    val translateAnim by transition.animateFloat(
        initialValue = shimmerOffset,
        targetValue = shimmerOffset + 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "cardShimmerAnimation"
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim, 0f),
        end = Offset(translateAnim + 300f, 0f)
    )

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(96.dp)
                    .height(64.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(brush)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                        .background(brush)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                        .background(brush)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(50))
                    .background(brush)
            )
        }
    }
}

@Composable
fun HeaderComponent(
    course: Course
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(36.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (course.images.isNotEmpty()){
                AsyncImage(
                    model = RESOURCE_ENDPOINT + course.images[0].url + "_landscapeSM.webp",
                    contentDescription = "Course Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
            }else{
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.outlineVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Image",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = course.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Normal
                )
                Text(
                    text = course.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoItem(
                "STATUS",
                course.status.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                Icons.Outlined.CheckCircle
            )
            InfoItem("CHAPTERS", "12 Quizess", Icons.AutoMirrored.Outlined.MenuBook)
            InfoItem("INSTRUCTOR", "Jaison Joshy", Icons.Outlined.PersonPin)
            InfoItem(
                "LANGUAGE",
                course.language.uppercase(Locale.ROOT).take(2),
                Icons.Outlined.Language
            )
        }
    }
}

@Composable
fun StickyHeaderComponent(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabTitles = listOf("Classes", "Slides", "Quizzes", "Notes", "Downloads")

    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 16.dp,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(tabPositions[selectedTabIndex])
                    .height(3.dp),
                color = MaterialTheme.colorScheme.primary
            )
        },
        divider = {
            HorizontalDivider(
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Text(
                    text = title,
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun InfoItem(label: String, value: String, icon: ImageVector) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, style = MaterialTheme.typography.labelMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun LectureCard(
    title: String,
    type: String,
    thumbnailUrl: String,
    onClick: () -> Unit = {}
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
//        shape = RectangleShape,
        elevation = CardDefaults.elevatedCardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail Image
            if (thumbnailUrl.isNotEmpty()){
                Log.d("LectureCard", "LectureCard: $thumbnailUrl")
                AsyncImage(
                    model = thumbnailUrl,
                    contentDescription = "Lecture Thumbnail",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(96.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
            }else{
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.outlineVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No Image",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Title and Progress Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = type.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Action icon
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.PlayCircle,
                    contentDescription = "Play",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun QuizCard(quiz: Quiz) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
//                text = quiz.title,
                text = "Title of Quiz",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(onClick = { /* Navigate to quiz */ }) {
                Icon(
                    imageVector = Icons.Default.PlayCircle,
                    contentDescription = "Take Quiz",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun SlideCard(slide: SlideModel) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val fileUri = UrlUtil.constructFileUrl(slide.file.path)

    ListItem(
        modifier = Modifier
            .clickable {
                scope.launch {
                    downloadAndOpenPdf(
                        context = context,
                        fileUrl = fileUri,
                        fileName = slide.title,
                        mimeType = slide.file.mimetype
                    )
                }
            }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        headlineContent = {
            Text(
                text = slide.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onSurface
            )
        }, leadingContent = {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.MenuBook,
                contentDescription = "Slide Icon",
                tint = MaterialTheme.colorScheme.primary
            )
        }, trailingContent = {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = "View Slide",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}

