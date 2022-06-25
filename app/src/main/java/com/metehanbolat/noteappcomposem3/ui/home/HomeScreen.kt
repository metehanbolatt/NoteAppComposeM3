package com.metehanbolat.noteappcomposem3.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.metehanbolat.noteappcomposem3.R
import com.metehanbolat.noteappcomposem3.model.NoteEntity
import com.metehanbolat.noteappcomposem3.viewmodel.HomeViewModelAbstract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

private enum class PopupState {
    Open, Close, Edit
}

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModelAbstract
) {
    val noteListState = homeViewModel.noteListFlow.collectAsState(initial = listOf())
    val popupState = rememberSaveable { mutableStateOf(PopupState.Close) }

    Scaffold(
        content = { scaffoldPadding ->
            LazyColumn(modifier = Modifier.padding(scaffoldPadding)) {
                items(count = noteListState.value.size) { index ->
                    val note = noteListState.value[index]
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                popupState.value = PopupState.Edit
                            }
                            .height(54.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(start = 16.dp, end = 16.dp),
                            text = note.text,
                            maxLines = 1
                        )
                        Spacer(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                                .background(color = Color.Gray.copy(alpha = 0.54f))
                                .align(Alignment.BottomCenter)
                        )
                    }
                }
                item {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            modifier = Modifier.align(Alignment.Center),
                            onClick = {
                                popupState.value = PopupState.Open
                            }
                        ) {
                            Text(text = stringResource(id = R.string.screen_home_button_add_note))
                        }
                    }
                }
            }

            when (popupState.value) {
                PopupState.Open -> {
                    NotePopup(
                        onClickDismiss = {
                            popupState.value = PopupState.Close
                        },
                        onClickSave = {
                            homeViewModel.addNote(note = NoteEntity(text = it))
                            popupState.value = PopupState.Close
                        }
                    )
                }
                PopupState.Edit -> {
                    NotePopup(
                        text = "text to edit",
                        onClickDismiss = {
                            popupState.value = PopupState.Close
                        },
                        onClickSave = {
                            homeViewModel.addNote(note = NoteEntity(text = it))
                            popupState.value = PopupState.Close
                        }
                    )
                }
                PopupState.Close -> {

                }
            }
        }
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        homeViewModel = object : HomeViewModelAbstract {
            override val noteListFlow: Flow<List<NoteEntity>>
                get() = flowOf(
                    listOf(
                        NoteEntity(text = "note 1"),
                        NoteEntity(text = "note 2"),
                        NoteEntity(text = "note 3"),
                        NoteEntity(text = "note 4"),
                        NoteEntity(text = "note 5")
                    )
                )

            override fun addNote(note: NoteEntity) {}
            override fun updateNote(note: NoteEntity) {}
            override fun deleteNote(note: NoteEntity) {}
        }
    )
}