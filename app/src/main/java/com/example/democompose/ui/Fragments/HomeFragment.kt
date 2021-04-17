package com.example.democompose.ui.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import com.example.democompose.R
import com.example.democompose.Screen
import com.example.democompose.data.Item
import com.example.democompose.data.ItemList
import com.example.democompose.navigate
import com.example.democompose.ui.theme.DemoComposeTheme
import com.example.democompose.ui.theme.Red700


class HomeFragment : Fragment() {

    private val viewModel:HomeViewModel by viewModels { HomeViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.Home)
            }
        }


        return ComposeView(requireContext()).apply {
            id=R.id.homeFragment
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                DemoComposeTheme {
                    HomeScaffold()
                }
            }
        }
    }


}

@Preview
@Composable
fun HomeScaffold() {
    val selectedIndex = remember { mutableStateOf(0) }
    Scaffold(
        topBar = { CustomAppBar() },
        content = {
            HomeScreen()
        },
        bottomBar = { CustomBottomBar(selectedIndex = selectedIndex) }

    )
}


@Composable
fun HomeScreen() {
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Banner()
        Spacer(modifier = Modifier.size(20.dp))
        OptionView()
        Spacer(modifier = Modifier.size(20.dp))
        TopItemList()
        Spacer(modifier = Modifier.size(5.dp))
        TopItemList()

    }
}

@Composable
fun Banner() {
    Card(shape = RoundedCornerShape(5.dp)) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(20.dp)
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(5.dp)
                ),
            painter = painterResource(id = R.drawable.special_offer_banner),
            contentDescription = "banner",
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun CustomAppBar() {
    TopAppBar(
        elevation = 0.dp,
        modifier = Modifier.fillMaxWidth(),
        title = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "MyShop",
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White,
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 22.sp
                    )
                )
                IconButton(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    onClick = { }
                ) {

                }
            }
        },
    )
}


@Composable
fun TopItemList() {
    LazyRow(modifier = Modifier.fillMaxWidth()) {
        items(ItemList.list) { item ->
            ItemCard(item = item)
        }
    }
}

@Composable
fun ItemCard(item: Item) {
    Card(
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .padding(10.dp)
            .width(180.dp)
            .shadow(elevation = 5.dp, shape = RoundedCornerShape(14.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = item.image),
                modifier = Modifier.size(140.dp),
                contentScale = ContentScale.Crop,
                contentDescription = "Item"
            )
            Row(modifier = Modifier.padding(top = 20.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = item.name, style = TextStyle(color = Color.Gray), fontSize = 16.sp)
                    Text(text = item.price, style = TextStyle(color = Red700), fontSize = 16.sp)

                }
            }
        }
    }
}

@Composable
fun OptionView() {
    Row(modifier = Modifier.padding(20.dp), horizontalArrangement = Arrangement.Center) {
        RoundedIconButton(icon = R.drawable.google_pay, description = "Pay")
        Spacer(modifier = Modifier.weight(1f))
        RoundedIconButton(icon = R.drawable.bill, description = "Bills")
        Spacer(modifier = Modifier.weight(1f))
        RoundedIconButton(icon = R.drawable.scan, description = "Scan")
    }
}


@Composable
fun RoundedIconButton(icon: Int, description: String) {
    Column(
        modifier = Modifier.size(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(color = MaterialTheme.colors.primary, shape = RoundedCornerShape(10.dp))
                .weight(1f)
        ) {
            IconButton(
                onClick = { }, modifier = Modifier
                    .align(Alignment.Center)
                    .padding(5.dp)
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "bill",
                    contentScale = ContentScale.Fit
                )
            }
        }
        Text(text = description)
    }

}

@Composable
fun CustomBottomBar(selectedIndex: MutableState<Int>) {
    val listItems = listOf("Home", "Location", "Cart", "Profile")
    Card(
        elevation = 0.dp, shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(64.dp)

    ) {
        BottomNavigation(backgroundColor = Color.DarkGray) {
            listItems.forEachIndexed { index: Int, label: String ->
                val isSelected = selectedIndex.value == index
                BottomNavigationItem(
                    selected = isSelected,
                    icon = {
                        when (index) {
                            0 -> {
                                TabIcons(icon = R.drawable.ic_home, isTintColor = isSelected)
                            }
                            1 -> {
                                TabIcons(icon = R.drawable.ic_location, isTintColor = isSelected)
                            }
                            2 -> {
                                TabIcons(
                                    icon = R.drawable.ic_baseline_shopping_cart_24,
                                    isTintColor = isSelected
                                )
                            }
                            3 -> {
                                TabIcons(
                                    icon = R.drawable.ic_baseline_person_24,
                                    isTintColor = isSelected
                                )
                            }
                        }
                    },
                    onClick = { selectedIndex.value = index },
                    alwaysShowLabel = false
                )


            }
        }
    }
}

@Composable
fun TabIcons(icon: Int, isTintColor: Boolean) {
    if (isTintColor) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(icon),
            contentDescription = "bottomIcon",
            colorFilter = ColorFilter.tint(MaterialTheme.colors.primary),
            contentScale = ContentScale.Fit,

            )
    } else {
        Image(
            modifier = Modifier.wrapContentSize(),
            painter = painterResource(icon),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(Color.White),
            contentDescription = "bottomIcon"
        )
    }
}


@Composable
fun CustomLargeButton() {
    Button(
        onClick = {},
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background,
            contentColor = MaterialTheme.colors.primary,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                color = MaterialTheme.colors.background, shape = RoundedCornerShape(15.dp),
            )
    ) {
        Text(text = "Proceed To Buy")
    }
}