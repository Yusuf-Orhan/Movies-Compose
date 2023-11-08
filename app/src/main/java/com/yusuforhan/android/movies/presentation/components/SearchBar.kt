package com.yusuforhan.android.movies.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.yusuforhan.android.movies.presentation.theme.PurplyBlue
import com.yusuforhan.android.movies.presentation.theme.lightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    autoFocus: Boolean,
    onSearch: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
            .clip(CircleShape)
            .background(PurplyBlue)
            .fillMaxWidth()
            .height(54.dp)
    ) {
        var searchInput: String by remember { mutableStateOf("") }
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current


        TextField(
            value = searchInput,
            onValueChange = { newValue ->
                searchInput = if (newValue.trim().isNotEmpty()) newValue else ""
            },
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester = focusRequester),
            singleLine = true,
            placeholder = {
                Text(
                    text = "Search by title,genre,actor",
                    color = lightGray.copy(alpha = 0.8F)
                )
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                textColor = Color.White.copy(alpha = 0.78F),
                containerColor = Color.Transparent,
                disabledTextColor = Color.LightGray,
                cursorColor = Color.LightGray
            ), keyboardOptions = KeyboardOptions(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {

                }
            ),
            leadingIcon = {
                LaunchedEffect(Unit) {
                    if (autoFocus) {
                        focusRequester.requestFocus()
                    }
                }
                Row {
                    IconButton(onClick = {


                    }) {
                        Icon(
                            painter = rememberVectorPainter(image = Icons.Default.Search),
                            tint = lightGray,
                            contentDescription = null,
                            modifier = Modifier.size(30.dp, 30.dp)
                        )
                    }
                }
            },
            trailingIcon = {
                AnimatedVisibility(visible = searchInput.trim().isNotEmpty()) {
                    IconButton(onClick = {
                        focusManager.clearFocus()
                        searchInput = ""
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            tint = lightGray,
                            contentDescription = null
                        )
                    }
                }
            }
        )
    }
}
