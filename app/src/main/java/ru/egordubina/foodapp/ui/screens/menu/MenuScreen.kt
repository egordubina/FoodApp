package ru.egordubina.foodapp.ui.screens.menu

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.egordubina.foodapp.R
import ru.egordubina.foodapp.ui.models.CategoryUi
import ru.egordubina.foodapp.ui.models.MealUi

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MenuScreen(
    uiState: MenuUiState,
    onFoodItemClick: (Int) -> Unit,
    onCategoryClick: (String) -> Unit,
) {
    val scrollAppBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var showSelectCityMenu by remember { mutableStateOf(false) }
    var selectedCity by rememberSaveable { mutableStateOf("Москва") }
    var selectedCategory by rememberSaveable { mutableIntStateOf(-1) }
    val scope = rememberCoroutineScope()
    val categoriesScrollState = rememberLazyListState()
    val foodScrollState = rememberLazyListState()
    val foodScrollPosition by remember { derivedStateOf { foodScrollState.firstVisibleItemIndex } }
    val promoPagerState = rememberPagerState { uiState.promoList.size }
    LaunchedEffect(key1 = selectedCategory) {
        scope.launch {
            onCategoryClick(
                uiState.categoriesList.find { it.id == selectedCategory }?.categoryName ?: ""
            )
        }
    }
    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    scrollBehavior = scrollAppBarBehavior,
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .clickable { showSelectCityMenu = !showSelectCityMenu }
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(text = selectedCity)
                            AnimatedContent(targetState = showSelectCityMenu, label = "") {
                                if (it)
                                    Icon(
                                        imageVector = Icons.Rounded.KeyboardArrowUp,
                                        contentDescription = null
                                    )
                                else
                                    Icon(
                                        painter = painterResource(id = R.drawable.keyboard_arrow_down),
                                        contentDescription = null
                                    )
                            }
                        }
                        DropdownMenu(
                            expanded = showSelectCityMenu,
                            onDismissRequest = { showSelectCityMenu = false }
                        ) {
                            uiState.allCities.forEach {
                                DropdownMenuItem(
                                    text = { Text(text = it) },
                                    onClick = {
                                        showSelectCityMenu = false
                                        selectedCity = it
                                    }
                                )
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = { }) {
                            Image(
                                painter = painterResource(id = R.drawable.scan_qr),
                                contentDescription = stringResource(id = R.string.scan_qr)
                            )
                        }
                    }
                )
                AnimatedVisibility(visible = foodScrollPosition == 0) {
                    Surface(
                        color = MaterialTheme.colorScheme.background
                    ) {
                        HorizontalPager(
                            state = promoPagerState,
                            modifier = Modifier.height(112.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(uiState.promoList[it])
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .fillMaxWidth()
                            )
                        }
                    }
                }
                Surface(
                    color = MaterialTheme.colorScheme.background,
                ) {
                    LazyRow(
                        state = categoriesScrollState,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(16.dp),
                    ) {
                        items(uiState.categoriesList, key = { it.categoryName }) {
                            CategoryItem(
                                category = it,
                                isSelected = it.id == selectedCategory,
                                onClick = { id ->
                                    selectedCategory = id
                                    scope.launch {
                                        delay(1000)
                                        categoriesScrollState.animateScrollToItem(0)
                                    }
                                },
                                onClearCategoryClick = {
                                    selectedCategory = -1
                                    scope.launch {
                                        delay(1000)
                                        categoriesScrollState.animateScrollToItem(0)
                                        foodScrollState.animateScrollToItem(0)
                                    }
                                },
                                modifier = Modifier.animateItemPlacement(animationSpec = tween(600))
                            )
                        }
                    }
                }
            }
        },
        modifier = Modifier.nestedScroll(scrollAppBarBehavior.nestedScrollConnection)
    ) { innerPadding ->
        // todo: change to dimensions
        LazyColumn(
            state = foodScrollState,
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier.consumeWindowInsets(innerPadding)
        ) {
            items(uiState.foodList, key = { it.id }) {
                FoodItem(
                    foodItem = it,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onFoodItemClick(it.id) }
                        .padding(horizontal = 16.dp)
                        .animateItemPlacement(
                            animationSpec = tween(600)
                        )
                )
            }
            item { Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars)) }
        }
    }
}

@Composable
private fun CategoryItem(
    category: CategoryUi,
    isSelected: Boolean,
    onClick: (Int) -> Unit,
    onClearCategoryClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilterChip(
        selected = isSelected,
        onClick = { onClick(category.id) },
        label = { Text(text = category.categoryName) },
        trailingIcon = {
            if (isSelected)
                Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = null,
                    modifier = Modifier.clickable { onClearCategoryClick() }
                )
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = Color(0xFFFFFFFF),
            labelColor = Color(0xFFC3C4C9),
            iconColor = Color(0xFFFD3A69),
            selectedContainerColor = Color(0x33FD3A69),
            selectedLabelColor = Color(0xFFFD3A69),
            selectedTrailingIconColor = Color(0xFFFD3A69),
        ),
        border = null,
        modifier = modifier.shadow(
            elevation = if (isSelected) 0.dp else 10.dp,
            ambientColor = Color(0x2BBEBEBE),
        )
    )
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
                    .weight(0.4f)
                    .clip(RoundedCornerShape(16.dp)),
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.6f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = foodItem.title,
                    fontSize = 16.sp,
                    lineHeight = 19.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = foodItem.ingredients.joinToString(),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    lineHeight = 17.sp
                )
                OutlinedButton(
                    onClick = { },
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.align(Alignment.End),
                    border = BorderStroke(1.dp, Color(0xFFFD3A69)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFD3A69))
                ) {
                    Text(text = foodItem.minPrice, fontSize = 13.sp, lineHeight = 16.sp)
                }
            }
        }
    }
}