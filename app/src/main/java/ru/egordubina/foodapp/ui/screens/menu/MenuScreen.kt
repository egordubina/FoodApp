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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
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
import kotlinx.coroutines.CoroutineScope
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
    var selectedCategory by rememberSaveable { mutableIntStateOf(-1) }
    val scope = rememberCoroutineScope()
    val foodScrollState = rememberLazyListState()
    LaunchedEffect(selectedCategory) {
        scope.launch {
            onCategoryClick(
                uiState.categoriesList.find { it.id == selectedCategory }?.categoryName ?: ""
            )
        }
    }
    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopBar(
                isLoading = uiState.isLoading,
                selectedCategory = selectedCategory,
                promoList = uiState.promoList,
                allCities = uiState.allCities,
                foodList = uiState.foodList,
                categoriesList = uiState.categoriesList,
                scope = scope,
                foodScrollState = foodScrollState,
                scrollAppBarBehavior = scrollAppBarBehavior,
                onSelectCategory = { selectedCategory = it }
            )
        },
        modifier = Modifier.nestedScroll(scrollAppBarBehavior.nestedScrollConnection)
    ) {
        LazyColumn(
            state = foodScrollState,
            contentPadding = it,
        ) {
            items(uiState.foodList, key = { it.id }) {
                FoodItem(
                    foodItem = it,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { onFoodItemClick(it.id) }
                        .padding(16.dp)
                        .animateItemPlacement(animationSpec = tween(600))
                )
                HorizontalDivider(color = Color(0xFFF3F5F9), thickness = 1.dp)
            }
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
        color = Color.White,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(22.dp),
            verticalAlignment = Alignment.CenterVertically,
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
                    border = BorderStroke(1.dp, Color(0xFFFD3A69)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFD3A69)),
                    modifier = Modifier.align(Alignment.End),
                ) {
                    Text(text = foodItem.minPrice, fontSize = 13.sp, lineHeight = 16.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun TopBar(
    isLoading: Boolean,
    selectedCategory: Int,
    promoList: List<String>,
    allCities: List<String>,
    foodList: List<MealUi>,
    categoriesList: List<CategoryUi>,
    scope: CoroutineScope,
    foodScrollState: LazyListState,
    scrollAppBarBehavior: TopAppBarScrollBehavior,
    onSelectCategory: (Int) -> Unit,
) {
    var showSelectCityMenu by remember { mutableStateOf(false) }
    var selectedCity by rememberSaveable { mutableStateOf("Москва") }
    val categoriesScrollState = rememberLazyListState()
    val foodScrollPosition by remember { derivedStateOf { foodScrollState.firstVisibleItemIndex } }
    val promoPagerState = rememberPagerState { promoList.size }
    Column {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                scrolledContainerColor = Color.White
            ),
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
                    allCities.forEach {
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
        if (isLoading)
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        AnimatedVisibility(visible = foodScrollPosition == 0 && promoList.isNotEmpty()) {
            Surface(color = Color.White) {
                HorizontalPager(
                    state = promoPagerState,
                    modifier = Modifier.height(112.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(promoList[it])
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
        Surface(color = Color.White) {
            LazyRow(
                state = categoriesScrollState,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp),
            ) {
                items(categoriesList, key = { it.categoryName }) {
                    CategoryItem(
                        category = it,
                        isSelected = it.id == selectedCategory,
                        onClick = { id ->
                            onSelectCategory(id)
                            if (foodList.isNotEmpty())
                                scope.launch {
                                    delay(1000)
                                    categoriesScrollState.animateScrollToItem(0)
                                }
                        },
                        onClearCategoryClick = {
                            onSelectCategory(-1)
                            if (foodList.isNotEmpty())
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
}