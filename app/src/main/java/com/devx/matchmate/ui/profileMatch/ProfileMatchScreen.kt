package com.devx.matchmate.ui.profileMatch

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.devx.domain.model.ProfileMatch
import com.devx.matchmate.R
import com.devx.matchmate.theme.MatchMateTheme
import com.devx.matchmate.ui.common.VerticalDivider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileMatchScreen(
    uiState: ProfileMatchScreenUiState,
    profileMatchViewModel: ProfileMatchViewModel = hiltViewModel(),
) {
    val localContext = LocalContext.current

    LaunchedEffect(key1 = uiState.errorMessage) {
        if (uiState.errorMessage?.isNotEmpty() == true) {
            Toast.makeText(localContext, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            profileMatchViewModel.resetErrorMessage()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Profile Matches") })
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(paddingValues = paddingValues),
        ) {
            if (uiState.profileMatchList?.isNotEmpty() == true) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(
                        items = uiState.profileMatchList,
                        key = { it.userId },
                    ) { profileMatchItem ->
                        ProfileMatchItem(
                            profileMatch = profileMatchItem,
                            onProfileAccepted = {
                                profileMatchViewModel.onProfileMatchStatusUpdated(
                                    userId = it.userId,
                                    updatedStatus = 1,
                                )
                            },
                            onProfileDeclined = {
                                profileMatchViewModel.onProfileMatchStatusUpdated(
                                    userId = it.userId,
                                    updatedStatus = 0,
                                )
                            },
                        )
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(text = if (uiState.isLoading) "Loading" else "No Matches")
                }
            }

            Button(
                onClick = {
                    profileMatchViewModel.getProfileMatchesFromNetwork()
                },
                modifier =
                    Modifier
                        .padding(bottom = 12.dp, end = 16.dp)
                        .align(alignment = Alignment.BottomEnd),
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(color = Color.Black)
                } else {
                    Text(text = "Refresh")
                }
            }
        }
    }
}

@Composable
private fun ProfileMatchItem(
    profileMatch: ProfileMatch,
    onProfileAccepted: (ProfileMatch) -> Unit,
    onProfileDeclined: (ProfileMatch) -> Unit,
) {
    val localContext = LocalContext.current

    Card(modifier = Modifier.padding(all = 16.dp)) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                modifier = Modifier.size(150.dp),
                model = profileMatch.profilePicUrl,
                contentDescription = profileMatch.name,
                imageLoader =
                    ImageLoader
                        .Builder(context = localContext)
                        .crossfade(enable = true)
                        .build(),
            )

            VerticalDivider(height = 12.dp)

            Text(
                text = profileMatch.name,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                color = MaterialTheme.colorScheme.primary,
            )

            VerticalDivider(height = 4.dp)

            Text(
                text = profileMatch.address,
                style = TextStyle(fontSize = 16.sp),
            )

            VerticalDivider(height = 16.dp)

            Box(modifier = Modifier.fillMaxWidth()) {
                if (profileMatch.status == 0 || profileMatch.status == 1) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(height = 60.dp)
                                .clip(
                                    shape =
                                        RoundedCornerShape(
                                            topStart = 12.dp,
                                            topEnd = 12.dp,
                                        ),
                                ).background(color = MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = if (profileMatch.status == 1) "Accepted" else "Declined",
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = TextStyle(fontSize = 18.sp),
                        )
                    }
                } else {
                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        ProfileCardActionButton(
                            resId = R.drawable.ic_decline,
                            onClick = {
                                onProfileDeclined(profileMatch)
                            },
                        )

                        ProfileCardActionButton(
                            resId = R.drawable.ic_accept,
                            onClick = {
                                onProfileAccepted(profileMatch)
                            },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileCardActionButton(
    resId: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(size = 60.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape,
                ),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(onClick = {
            onClick()
        }) {
            Icon(
                modifier = Modifier.size(size = 30.dp),
                painter = painterResource(id = resId),
                contentDescription = null,
            )
        }
    }
}

@Preview
@Composable
private fun ProfileMatchScreenPreview() {
    MatchMateTheme {
        ProfileMatchScreen(
            uiState =
                ProfileMatchScreenUiState(
                    profileMatchList =
                        listOf(
                            ProfileMatch(
                                userId = "abc",
                                name = "Swapnil Musale",
                                profilePicUrl = "",
                                address = "Mumbai",
                            ),
                        ),
                ),
        )
    }
}
