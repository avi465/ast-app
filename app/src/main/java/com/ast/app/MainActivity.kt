package com.ast.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ast.app.graphs.RootNavigationGraph
import com.ast.app.ui.theme.AdvancedStudyTutorialsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
//      this will enable edge to edge
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
//        this will install splash screen
//        installSplashScreen()
        setContent {
            AdvancedStudyTutorialsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RootNavigationGraph()
                }
            }
        }
    }

    companion object {
        const val SHARED_PREFS = "com.ast.app.SHARED_PREFS"
    }
}