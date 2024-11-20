package com.example.notele.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.notele.R
import com.example.notele.db.model.NoteleModel
import com.example.notele.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHome(
    vm : HomeViewModel = hiltViewModel()
){
    val stateVm = vm.state.value

    Scaffold(
        topBar = {
            SearchBar(
                query = "Buscar",
                onQueryChange = {},
                onSearch = {},
                active = false,
                onActiveChange = {},
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shadowElevation = 2.dp,
            ) {

            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            ListScreen(
                noteleList = stateVm.noteList
            )
        }
    }

}
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    noteleList: List<NoteleModel>
){

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            12.dp
        ),
        content = {
            items(noteleList) { index ->
                    ItemScreen(
                        notele = index
                    )
            }
        }
    )
}

@Composable
fun ItemScreen(
    notele: NoteleModel
){
    Card(
        modifier = Modifier.fillMaxWidth(1f).padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ){
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 5.dp, end = 5.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Icon(
                painter = painterResource(R.drawable.circle_property),
                contentDescription = "",
            )
            Icon(
                painter = painterResource(R.drawable.delete_note),
                contentDescription = ""
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 12.dp, end = 8.dp, bottom = 12.dp)

        ) {
            Text(text = notele.title, fontSize = 24.sp, modifier = Modifier.padding(top = 5.dp))
            Text(text = notele.description,
                fontSize = 12.sp, modifier = Modifier.padding(top = 5.dp),
                maxLines = 3
                )
            Row(
                modifier = Modifier.padding(2.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Right
            ){
                Text(text = "13/09/2024", fontSize = 14.sp)
            }
        }
    }
}



