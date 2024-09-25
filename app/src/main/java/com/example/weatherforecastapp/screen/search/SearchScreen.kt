package com.example.weatherforecastapp.screen.search


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherforecastapp.navigation.WeatherScreens
import com.example.weatherforecastapp.widgets.WeatherAppBar

@Composable
fun SearchScreen(navController: NavController){
    Scaffold(topBar = {
        WeatherAppBar(title = "Search", navController =navController ,
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            isMainScreen = false,
            ){
            navController.popBackStack()
        }
    }) {
        Surface(modifier = Modifier.padding(it)) {

            Column (verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                SearchBar(modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                ) { mCity->
                    navController.navigate(WeatherScreens.MainScreen.name+"/$mCity")
                }
                }
            }
        }
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTextField(
    valueState: MutableState<String>,
    placeholder: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction  = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
    ) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = valueState.value,
        onValueChange = {
            valueState.value = it
        },
        label = { Text(text = placeholder)},
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType , imeAction = imeAction),
        keyboardActions = onAction,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = androidx.compose.ui.graphics.Color.Blue,
            unfocusedBorderColor = androidx.compose.ui.graphics.Color.Gray,
            cursorColor = androidx.compose.ui.graphics.Color.Black,
            focusedLabelColor = androidx.compose.ui.graphics.Color.Blue,
            unfocusedLabelColor = androidx.compose.ui.graphics.Color.Gray
        )

    )

}

@SuppressLint("SuspiciousIndentation")
@Composable
fun SearchBar(
    modifier : Modifier = Modifier,
    onSearch : (String ) -> Unit = {}) {
    val searchQueryState = rememberSaveable{
        mutableStateOf("")
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid  = remember(searchQueryState){
        searchQueryState.value.trim().isNotEmpty()
    }
    Column {
        CommonTextField(
            valueState = searchQueryState,
            placeholder = "Haldwani",
            imeAction = ImeAction.Done,
            onAction = KeyboardActions (
                onDone = {
                if(valid) return@KeyboardActions
                    onSearch(searchQueryState.value.trim())
                    searchQueryState.value = ""
                    keyboardController?.hide()
            }
            )
        )
    }
}


