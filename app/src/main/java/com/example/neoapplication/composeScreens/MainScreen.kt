package com.example.neoapplication.composeScreens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.neoapplication.R
import com.example.neoapplication.mainScreen.model.PilatesItems
import com.example.neoapplication.mainScreen.model.YogaItems
import com.example.neoapplication.mainScreen.viewmodel.MainScreenViewModel
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalFoundationApi::class,ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainScreenViewModel: MainScreenViewModel = hiltViewModel()) {
    // State to handle ViewPager visibility
    var isViewPagerVisible by remember { mutableStateOf(true) }

    val yogaData by mainScreenViewModel.uiStateYogaDataCompose.collectAsState()
    val pilatesData by mainScreenViewModel.uiStatePilatesDataCompose.collectAsState()
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


    val dataList = ArrayList<String>()
    dataList.add("https://moazamhussain.github.io/fitnessapp/yoga.jpg")
    dataList.add("https://moazamhussain.github.io/fitnessapp/pilates.jpg")

    val pagerState = rememberPagerState(pageCount = { dataList.size })

    LaunchedEffect(key1 = showBottomSheet) {
        if (showBottomSheet) {
            if(currentPage > 0)
            {
                bottomSheetItemsP.value = pilatesData.PILATES.take(3)
            }
            else
            {
                bottomSheetItemsY.value = yogaData.YOGA.take(3)
            }
        }
    }

    // Filtered list based on search query
    val filteredYogaData by remember(searchQuery.value) {
        derivedStateOf {
            if (searchQuery.value.isBlank()) {
                yogaData.YOGA
            } else {
                yogaData.YOGA.filter { it.YName!!.contains(searchQuery.value, ignoreCase = true) }
            }
        }
    }

    val filteredPilatesData by remember(searchQuery.value) {
        derivedStateOf {
            if (searchQuery.value.isBlank()) {
                pilatesData.PILATES
            } else {
                pilatesData.PILATES.filter { it.PName!!.contains(searchQuery.value, ignoreCase = true) }
            }
        }
    }

    LaunchedEffect(Unit) {
        mainScreenViewModel.getYogaDataCompose()
        mainScreenViewModel.getPilatesDataCompose()
    }

    // Layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
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
                    mainScreenViewModel
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


        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            when (currentPage) {
                0 -> {
                    yogaData.let {
                        items(filteredYogaData.size) { item ->
                            YogaItemView(filteredYogaData[item])
                        }
                    }

                }

                1 -> {
                    pilatesData.let {
                        items(filteredPilatesData.size) { item ->
                            PilatesItemView(filteredPilatesData[item])
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
                sheetState,bottomSheetItemsY.value,bottomSheetItemsP.value, currentPage,
                yogaData.YOGA.size,pilatesData.PILATES.size)
        }
    }

}

@Composable
fun SearchToolbar(onSearchTextChanged: (String) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    // Implement your search toolbar UI here
    // For example, an EditText wrapped in a Row
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
fun ImageSlider(dataList: List<String>,
                onPageChange: (Int) -> Unit,
                mainScreenViewModel: MainScreenViewModel = hiltViewModel()) {
    val pagerState = rememberPagerState(pageCount = {dataList.size})
    val context = LocalContext.current

    // LaunchedEffect to show a toast when the page changes
    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collectLatest { page ->
            if(page > 0)
            {
                mainScreenViewModel.getPilatesDataCompose()
            }
            else
            {
                mainScreenViewModel.getYogaDataCompose()
            }
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
            AsyncImage(
                model = dataList[page],
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .padding(5.dp)
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
    selectedColor: Color = colorResource(id = R.color.darkgreen),
    unselectedColor: Color = Color.White,
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
                        color = colorResource(id = R.color.darkgreen),
                        shape = CircleShape
                    )
            )
        }
    }
}


@Composable
fun YogaItemView(yogaItem: YogaItems) {
    // Display the card view with image and text
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lightgreen)),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image section
            AsyncImage(
                model = yogaItem.YImg,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Text section
            Column {
                Text(
                    text = yogaItem.YId.toString() ?: "No Name",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black
                )
                // Subtitle
                Text(
                    text = yogaItem.YName ?:"Subtitle here", // Replace with actual subtitle if available
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
fun PilatesItemView(pilatesItem: PilatesItems) {
    // Display the card view with image and text
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lightgreen)),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image section
            AsyncImage(
                model = pilatesItem.PImg,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Text section
            Column {
                Text(
                    text = pilatesItem.PId.toString() ?: "No Name",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black
                )
                // Subtitle
                Text(
                    text = pilatesItem.PName ?: "Subtitle here", // Replace with actual subtitle if available
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class,ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(onClose: () -> Unit,sheetState : SheetState,
                       dataY : List<YogaItems>,dataP : List<PilatesItems>,
                       selection : Int, ySize : Int, pSize : Int) {
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState,


    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Close button
            IconButton(onClick = onClose,
                modifier = Modifier.align(Alignment.End)) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Top 3 activities")
            if(selection > 0)
            {
                HorizontalItemListPilates(dataP)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "+ ${pSize} more activities", modifier = Modifier.align(Alignment.End))
            }
            else
            {
                HorizontalItemListYoga(dataY)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "+ ${ySize} more activities", modifier = Modifier.align(Alignment.End))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }

}


@Composable
fun ItemViewYoga(yogaItem: YogaItems) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lightgreen)),
        modifier = Modifier
            .padding(end = 8.dp)
            .wrapContentWidth().height(110.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = yogaItem.YImg),
                contentDescription = yogaItem.YName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = yogaItem.YId.toString() ?: "",
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = yogaItem.YName ?: "",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            }
        }
    }
}

@Composable
fun ItemViewPilates(pilatesItem: PilatesItems) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.lightgreen)),
        modifier = Modifier
            .padding(end = 8.dp)
            .wrapContentWidth().height(110.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = pilatesItem.PImg),
                contentDescription = pilatesItem.PName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = pilatesItem.PId.toString() ?: "",
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = pilatesItem.PName ?: "",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            }
        }
    }
}

@Composable
fun HorizontalItemListYoga(yogaItems: List<YogaItems>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        items(yogaItems.size) { item ->
            ItemViewYoga(yogaItem = yogaItems[item])
        }
    }
}

@Composable
fun HorizontalItemListPilates(pilatesItems: List<PilatesItems>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(pilatesItems.size) { item ->
            ItemViewPilates(pilatesItem = pilatesItems[item])
        }
    }
}

