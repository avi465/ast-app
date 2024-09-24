package com.ast.app.presentation.application.profile

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Brightness5
import androidx.compose.material.icons.outlined.Contrast
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material.icons.twotone.WbSunny
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ast.app.R
import com.ast.app.datastore.PreferencesDataStore
import com.ast.app.presentation.common.EmptyScreen
import com.ast.app.presentation.common.ErrorScreen
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SettingsContent()
    }
}


@Composable
fun SettingsContent() {
    val scrollState = rememberScrollState()
    // Initialize DataStore
    val context = LocalContext.current
    val preferencesDataStore = PreferencesDataStore(context)
    val isDarkTheme by preferencesDataStore.darkThemeFlow.collectAsState(initial = isSystemInDarkTheme())
    val useDynamicColor by preferencesDataStore.dynamicColorFlow.collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        // Profile Settings
        SettingsItem(
            title = "Account & Profile",
            subtitle = "View and edit your profile",
            icon = Icons.Filled.AccountCircle,
            onClick = { /* Navigate to Profile Settings */ }
        )

        // Transactions (Orders)
        SettingsItem(
            title = "Orders & Transactions",
            subtitle = "View your order history and transactions",
            icon = Icons.Outlined.ShoppingCart,
            onClick = { /* Navigate to Orders/Transactions */ }
        )

        HorizontalDivider()

        // Dynamic Theme Toggle
        SettingsItem(
            title = "Dynamic Theme",
            subtitle = "Adapt the app's appearance based on system theme and preferences in Android 12+",
            icon = Icons.Outlined.Brightness5,
            isSwitch = true,
            isChecked = useDynamicColor,
            onCheckedChange = {
                coroutineScope.launch {
                    preferencesDataStore.saveDynamicColor(it)
                }
            }
        )

        // Dark Theme
        SettingsItem(
            title = "Dark Mode",
            subtitle = "Reduce eye strain",
            icon = Icons.TwoTone.WbSunny,
            isSwitch = true,
            isChecked = isDarkTheme,
            onCheckedChange = {
                coroutineScope.launch {
                    preferencesDataStore.saveDarkTheme(it)
                }
            }
        )

        HorizontalDivider()

        // Terms and Conditions
        SettingsItem(
            title = "Terms and Conditions",
            subtitle = "Read terms and conditions",
            icon = Icons.AutoMirrored.Outlined.Article,
            onClick = { /* Open Terms and Conditions */ }
        )

        // Privacy Policy
        SettingsItem(
            title = "Privacy Policy",
            subtitle = "View the privacy policy",
            icon = Icons.Outlined.PrivacyTip,
            onClick = { /* Open Privacy Policy */ }
        )


        // Help and Support
        SettingsItem(
            title = "Help & Support",
            subtitle = "Get help and find FAQs",
            icon = Icons.AutoMirrored.Outlined.Help,
            onClick = { /* Open Help/Support */ }
        )

        // About the App
        SettingsItem(
            title = "About",
            subtitle = "Learn more about the app",
            icon = Icons.Outlined.Info,
            onClick = { /* Open About section */ }
        )

        HorizontalDivider()

        // Check for update
        SettingsItem(
            title = "Check For Update",
            subtitle = "Check for the latest features, improvements, and bug fixes",
            icon = Icons.Outlined.Update,
            onClick = { /* Open About section */ }
        )

        // Logout
        SettingsItem(
            title = "Logout",
            subtitle = "Logout from your account on this device",
            icon = Icons.AutoMirrored.Outlined.Logout,
            onClick = { /* Handle Logout */ }
        )
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String? = null,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSwitch: Boolean = false,
    isChecked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    ListItem(
        modifier = Modifier.clickable(
            enabled = onClick != null,
            onClick = { onClick?.invoke() }),
        headlineContent = { Text(title) },
        supportingContent = {
            if (subtitle != null) {
                Text(subtitle)
            }
        },
        leadingContent = {
            Icon(imageVector = icon, contentDescription = null)
        },
        trailingContent = {
            if (isSwitch) {
                Switch(checked = isChecked, onCheckedChange = { onCheckedChange?.invoke(it) })
            }
        }
    )
}
