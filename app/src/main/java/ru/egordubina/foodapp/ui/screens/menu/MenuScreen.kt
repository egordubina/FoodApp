package ru.egordubina.foodapp.ui.screens.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ru.egordubina.foodapp.R
import ru.egordubina.foodapp.ui.models.CategoryUi
import ru.egordubina.foodapp.ui.models.MealUi
import ru.egordubina.foodapp.ui.theme.FoodAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(uiState: MenuUiState) {
    val scrollAppBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    scrollBehavior = scrollAppBarBehavior,
                    title = { Text("Москва") },
                    actions = {
                        IconButton(onClick = { }) {
                            Image(
                                painter = painterResource(id = R.drawable.scan_qr),
                                contentDescription = stringResource(id = R.string.scan_qr)
                            )
                        }
                    }
                )
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp),
                    ) {
                        items(uiState.categoriesList) {
                            CategoryItem(category = it, isSelected = false) {}
                        }
                    }
                }
            }
        },
        modifier = Modifier.nestedScroll(scrollAppBarBehavior.nestedScrollConnection)
    ) { innerPadding ->
        // todo: change to dimensions
        LazyColumn(
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.consumeWindowInsets(innerPadding)
        ) {
            items(uiState.foodList, key = { it.id }) {
                FoodItem(it, modifier = Modifier.padding(horizontal = 16.dp))
            }
            item { Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars)) }
        }
    }
}

@Preview
@Composable
private fun MenuScreenPreview() {
    FoodAppTheme {
        MenuScreen(uiState = MenuUiState())
    }
}

@Composable
private fun CategoryItem(
    category: CategoryUi,
    isSelected: Boolean,
    onClick: (Int) -> Unit,
) {
    Surface(
        color = if (isSelected) Color(0x33FD3A69) else Color(0xFFFFFFFF),
        contentColor = if (isSelected) Color(0xFFFD3A69) else Color(0xFFC3C4C9),
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .shadow(
                elevation = if (isSelected) 0.dp else 6.dp,
                clip = false,
                ambientColor = Color(0x2BBEBEBE),
            )
            .clickable { onClick(category.id) }
    ) {
        // todo: change to dimensions
        Text(
            text = category.categoryName,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )
    }
}

@Composable
private fun FoodItem(foodItem: MealUi, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(22.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(foodItem.thumbnail)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(0.5f)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(text = foodItem.title, style = MaterialTheme.typography.titleLarge)
                Text(
                    text = foodItem.ingredients.joinToString(),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                OutlinedButton(
                    onClick = { },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.align(Alignment.End),
                    border = BorderStroke(1.dp, Color(0xFFFD3A69)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFD3A69))
                ) {
                    Text(text = foodItem.minPrice)
                }
            }
        }
    }
}