package ru.egordubina.foodapp.ui.screens.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.egordubina.foodapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.profile)) })
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(it).fillMaxSize()
        ) {
            Button(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.login))
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.signup))
            }
        }
    }
}