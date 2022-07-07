package com.example.composeanimatedbottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.BottomSheetValue.Collapsed
import androidx.compose.material.BottomSheetValue.Expanded
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeanimatedbottomsheet.ui.theme.ComposeAnimatedBottomSheetTheme
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeAnimatedBottomSheetTheme {
                    Screen()
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentFraction:Float
get() {
    val fraction = bottomSheetState.progress.fraction
    val targetValue = bottomSheetState.targetValue
    val currentValue = bottomSheetState.currentValue

    return when{
        currentValue == Collapsed && targetValue == Collapsed -> 0f
        currentValue == Expanded && targetValue == Expanded -> 1f
        currentValue == Collapsed && targetValue == Expanded -> fraction
        else -> 1f - fraction
    }
}
/**--------------------Composable-----------------------**/
@ExperimentalMaterialApi
@Composable
fun Screen(){
    var coroutineState = rememberCoroutineScope()
    val bottomSheetState =  rememberBottomSheetScaffoldState(
            bottomSheetState =BottomSheetState(Collapsed)
    )

    val sheetToggle :()->Unit={
        coroutineState.launch {
            if(bottomSheetState.bottomSheetState.isCollapsed){
                bottomSheetState.bottomSheetState.expand()
            }else{
                bottomSheetState.bottomSheetState.collapse()
            }
        }
    }
    
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = bottomSheetState,
        topBar = {
                 TopAppBar(
                     title = { Text(text = "Custom bottom sheet")},
                     navigationIcon = {
                         IconButton(onClick = { /*TODO*/ }) {
                             Icon(imageVector = Icons.Filled.Home, contentDescription = null)
                         }
                     }
                 )
        },
        sheetContent = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.8f)) {
                BottomSheetExpanded()
                BottomSheetCollapsed(bottomSheetState.currentFraction)
            }
        },
        sheetPeekHeight = 56.dp
    ) {
        ScreenMainContent()
    }

}


@Composable
fun BottomSheetCollapsed(currentFraction: Float){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(Color.Transparent)
            .graphicsLayer(alpha = 1f - currentFraction),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Animated bottom sheet sample",
            color = Color.White
        )
    }
}

@Composable
fun BottomSheetExpanded(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Color(R.color.violet_102)),
        contentAlignment = Alignment.TopCenter
    )
    {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(30.dp))
            Text(text = "Bottom sheet expanded content")
            Button(
                onClick = { /*TODO*/ }
            ) {
                Text(text = "Click Me")
            }
        }
    }
}

@Composable
fun ScreenMainContent(){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Application",
            style = MaterialTheme.typography.h3
        )
    }
}

/**--------------------End Composable-----------------------**/

/**--------------------Preview-----------------------**/
@Preview(showBackground = true)
@Composable
fun BottomSheetCollapsedPreview(){
    ComposeAnimatedBottomSheetTheme {
        //BottomSheetCollapsed()
    }
}

@Preview(showBackground = true)
@Composable
fun BottomSheetExpandedPreview(){
    ComposeAnimatedBottomSheetTheme {
        BottomSheetExpanded()
    }
}

@Preview
@Composable
fun ScreenMainContentPreview(){
    ComposeAnimatedBottomSheetTheme {
        ScreenMainContent()
    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun ScreenPreview(){
    ComposeAnimatedBottomSheetTheme {
        Screen()
    }
}
/**--------------------End Preview-----------------------**/