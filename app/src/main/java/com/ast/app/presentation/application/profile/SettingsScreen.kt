package com.ast.app.presentation.application.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Brightness5
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Update
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.ast.app.MainActivity
import com.ast.app.datastore.PreferencesDataStore
import com.ast.app.graphs.Graph
import com.ast.app.network.utils.CookieManager
import com.razorpay.Checkout
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    rootNavController: NavHostController,
    navController: NavHostController
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SettingsContent(
            rootNavController = rootNavController,
            navController = navController
        )
    }
}


@Composable
fun SettingsContent(
    rootNavController: NavHostController,
    navController: NavHostController
) {
    val scrollState = rememberScrollState()
    // Initialize DataStore
    val context = LocalContext.current
    val preferencesDataStore = PreferencesDataStore(context)
    val isDarkTheme by preferencesDataStore.darkThemeFlow.collectAsState(initial = isSystemInDarkTheme())
    val useDynamicColor by preferencesDataStore.dynamicColorFlow.collectAsState(initial = false)
    val coroutineScope = rememberCoroutineScope()
    var isLoggedOut by remember { mutableStateOf(false) }

    // Observe logout state and navigate if needed
//    LaunchedEffect(isLoggedOut) {
//        if (isLoggedOut) {
//            rootNavController.popBackStack(
//                rootNavController.graph.startDestinationId,
//                false
//            )
//        }
//    }

    Column(
        modifier = Modifier.verticalScroll(scrollState)
    ) {
        // Profile Settings
        SettingsItem(
            title = "Account & Profile",
            subtitle = "View and edit your profile",
            icon = Icons.Outlined.AccountCircle,
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
            icon = Icons.Outlined.WbSunny,
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
            icon = Icons.AutoMirrored.Outlined.HelpOutline,
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
            tint = MaterialTheme.colorScheme.error,
            textColor = MaterialTheme.colorScheme.error,
            onClick = {
                // Launch the logout task in a coroutine
                coroutineScope.launch {
                    /* Handle Logout */
                    try {
                        val sharedPreferences =
                            context.getSharedPreferences(
                                MainActivity.SHARED_PREFS,
                                Context.MODE_PRIVATE
                            )
                        with(sharedPreferences.edit()) {
                            putString("username", null)
                            putString("password", null)
                            putBoolean("remember_me", false)
                            apply()
                        }

                        // Optionally, you can make a server request to log out here
                        // Example: api.logout()

                        // Update state to trigger recomposition after the task
                        // isLoggedOut = true

                        //todo: do server logout request
                        CookieManager.clear()


                        // Erase data of customer from razorpay sdk on logout
                        Checkout.clearUserData(context)

                        rootNavController.navigate(Graph.ROOT) {
                            popUpTo(Graph.MAIN_SCREEN_PAGE) {
                                inclusive = true
                            }
                        }

//                        rootNavController.popBackStack(
//                            rootNavController.graph.startDestinationId,
//                            false
//                        )

                    } catch (e: Exception) {
                        Log.d("TAG", "SettingsContent: $e")
                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }
}

@Composable
fun SettingsItem(
    title: String,
    subtitle: String? = null,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color? = null,
    textColor: Color? = null,
    isSwitch: Boolean = false,
    isChecked: Boolean = false,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    ListItem(
        modifier = Modifier.clickable(
            enabled = onClick != null,
            onClick = { onClick?.invoke() }),
        headlineContent = { Text(title, color = textColor ?: LocalContentColor.current) },
        supportingContent = {
            if (subtitle != null) {
                Text(subtitle, color = textColor ?: LocalContentColor.current)
            }
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint ?: LocalContentColor.current
            )

        },
        trailingContent = {
            if (isSwitch) {
                Switch(checked = isChecked, onCheckedChange = { onCheckedChange?.invoke(it) })
            }
        }
    )
}
