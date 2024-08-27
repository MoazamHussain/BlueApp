package com.example.blueapp.presentation

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.blueapp.R
import com.example.blueapp.data.remote.dto.PilatesItems
import com.example.blueapp.data.remote.dto.YogaItems
import com.example.blueapp.presentation.pilates_list.PilatesListViewModel
import com.example.blueapp.presentation.pilates_list.components.PilatesAnalyticsData
import com.example.blueapp.presentation.pilates_list.components.PilatesItemView
import com.example.blueapp.presentation.yoga_list.YogaListViewModel
import com.example.blueapp.presentation.yoga_list.components.YogaAnalyticsData
import com.example.blueapp.presentation.yoga_list.components.YogaItemView
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class,ExperimentalMaterial3Api::class)
@Composable
fun MainListScreen(yogaListViewModel: YogaListViewModel = hiltViewModel(),
                   pilatesListViewModel: PilatesListViewModel = hiltViewModel()) {

    var isViewPagerVisible by remember { mutableStateOf(true) }

    val stateYoga = yogaListViewModel.state.collectAsState()
    val statePilates = pilatesListViewModel.state.collectAsState()



    var currentPage by remember { mutableStateOf(0) }
    val searchQuery = remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    val sheetState = androidx.compose.material3.rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetItemsY = remember { mutableStateOf<List<YogaItems>>(emptyList()) }
    val bottomSheetItemsP = remember { mutableStateOf<List<PilatesItems>>(emptyList()) }


    val listState = rememberLazyListState()

    val showSlider by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
        }
    }



    val animatedSliderVisibility = animateFloatAsState(
        targetValue = if (showSlider) 1f else 0f,
        animationSpec = tween(durationMillis = 300)
    )


    val dataList = listOf(
        R.drawable.yoga,
        R.drawable.pilates
    )

    val pagerState = rememberPagerState(pageCount = { dataList.size })


    LaunchedEffect(key1 = showBottomSheet) {
        if (showBottomSheet) {
            if(currentPage > 0)
            {
                bottomSheetItemsP.value = statePilates.value.list.PILATES
            }
            else
            {
                bottomSheetItemsY.value = stateYoga.value.list.YOGA
            }
        }
    }


    val filteredYogaData by remember(searchQuery.value,stateYoga.value.list) {
        derivedStateOf {
            if (searchQuery.value.isBlank()) {
                stateYoga.value.list.YOGA
            } else {
                stateYoga.value.list.YOGA.filter { it.YName!!.contains(searchQuery.value, ignoreCase = true) }
            }
        }
    }

    val filteredPilatesData by remember(searchQuery.value,statePilates.value.list) {
        derivedStateOf {
            if (searchQuery.value.isBlank()) {
                statePilates.value.list.PILATES
            } else {
                statePilates.value.list.PILATES.filter { it.PName!!.contains(searchQuery.value, ignoreCase = true) }
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .background(Color.White)
    ) {
        if (showSlider) {
            AnimatedVisibility(
                visible = animatedSliderVisibility.value > 0f,
                enter = fadeIn() + scaleIn(),
                exit = fadeOut() + scaleOut()
            ) {
                ImageSlider(
                    dataList,
                    onPageChange = { page ->
                        currentPage = page
                    },
                    currentPage
                )
            }

        }

        SearchToolbar { searchText ->
            searchQuery.value = searchText
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .background(Color.White)
        ) {

            if(currentPage > 0)
            {
                when
                {
                    statePilates.value.error.isNotBlank()-> {
                        Text(
                            text = statePilates.value.error,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .align(Alignment.Center)
                        )
                    }
                    statePilates.value.isLoading -> {
                        androidx.compose.material3.CircularProgressIndicator(
                            modifier = androidx.compose.ui.Modifier.align(
                                androidx.compose.ui.Alignment.Center
                            )
                        )
                    }
                    else ->{
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            items(filteredPilatesData.size) { item ->
                                PilatesItemView(filteredPilatesData[item])
                            }
                        }

                    }
                }

            }
            else
            {
                when
                {
                    stateYoga.value.error.isNotBlank() -> {
                        Text(
                            text = stateYoga.value.error,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp)
                                .align(Alignment.Center)
                        )
                    }
                    stateYoga.value.isLoading -> {
                        androidx.compose.material3.CircularProgressIndicator(
                            modifier = androidx.compose.ui.Modifier.align(
                                androidx.compose.ui.Alignment.Center
                            )
                        )
                    }
                    else ->{
                        LazyColumn(
                            state = listState,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        ) {
                            items(filteredYogaData.size) { item ->
                                YogaItemView(filteredYogaData[item])
                            }
                        }

                    }
                }

            }




        FloatingActionButton(
            onClick = { showBottomSheet = !showBottomSheet },
            containerColor = colorResource(id = R.color.blue),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(5.dp)
        ) {
            Icon(
                painterResource(id = R.drawable.three_dots_menu_white),
                contentDescription = "Menu"
            )
        }

    }
        if(showBottomSheet)
        {
            BottomSheetContent(onClose = { showBottomSheet = false },
                sheetState,bottomSheetItemsY.value,bottomSheetItemsP.value, currentPage)
        }
    }

}

@Composable
fun SearchToolbar(onSearchTextChanged: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    Spacer(modifier = Modifier.padding(10.dp))
    TextField(
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = colorResource(id = R.color.lightgrey),
            focusedContainerColor = colorResource(id = R.color.lightgrey),
            unfocusedIndicatorColor = colorResource(id = R.color.white),
            focusedIndicatorColor = colorResource(id = R.color.white),
            cursorColor = colorResource(id = R.color.white),
        ),
        shape = RoundedCornerShape(15.dp),
        value = searchText,
        onValueChange = {
            searchText = it
            onSearchTextChanged(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        placeholder = { Text("Search") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ImageSlider(dataList: List<Int>,
                onPageChange: (Int) -> Unit,
                currentPage: Int,
                yogaListViewModel: YogaListViewModel = hiltViewModel(),
                pilatesListViewModel: PilatesListViewModel = hiltViewModel()) {
    val pagerState = rememberPagerState(pageCount = {dataList.size},initialPage = currentPage)

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collectLatest { page ->
            onPageChange(page)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) { page ->

            Image(
                painter = painterResource(id = dataList[page]),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .padding(5.dp)
                    .graphicsLayer {
                        alpha = 1f
                        translationX = 0f
                    }
            )

        }
        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            PagerIndicator(
                modifier = Modifier.align(Alignment.Center),
                pagesSize = 2, selectedPage = pagerState.currentPage
            )
        }

    }
}

@Composable
fun PagerIndicator(
    modifier: Modifier = Modifier,
    pagesSize: Int,
    selectedPage: Int,
    selectedColor: Color = colorResource(id = R.color.blue),
    unselectedColor: Color = Color.LightGray,
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(times = pagesSize) { page ->
            Box(
                modifier = Modifier
                    .size(8.dp)

                    .clip(CircleShape)
                    .background(color = if (page == selectedPage) selectedColor else unselectedColor)
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.white),
                        shape = CircleShape
                    )
            )
        }
    }
}



@OptIn(ExperimentalFoundationApi::class,ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(onClose: () -> Unit,sheetState : SheetState,
                       dataY : List<YogaItems>,dataP : List<PilatesItems>,
                       selection : Int) {
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 30.dp)
        ) {

            Spacer(modifier = Modifier.height(8.dp))

            if(selection > 0)
            {
                PilatesAnalyticsData(dataP)
            }
            else
            {
                YogaAnalyticsData(dataY)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}







