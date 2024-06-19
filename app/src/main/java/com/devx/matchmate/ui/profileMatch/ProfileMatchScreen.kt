package com.devx.matchmate.ui.profileMatch

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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.devx.domain.model.ProfileMatch
import com.devx.matchmate.R
import com.devx.matchmate.theme.MatchMateTheme
import com.devx.matchmate.ui.common.VerticalDivider

@Composable
fun ProfileMatchScreen(uiState: ProfileMatchScreenUiState) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
    ) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.profileMatchList?.isNotEmpty() == true) {
            ProfileMatchContent(profileMatchList = uiState.profileMatchList)
        } else {
            Text(text = "No Data!")
        }
    }
}

@Composable
private fun ProfileMatchContent(profileMatchList: List<ProfileMatch>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = profileMatchList,
            key = { it.userId },
        ) {
            ProfileMatchItem(profileMatch = it)
        }
    }
}

@Composable
private fun ProfileMatchItem(profileMatch: ProfileMatch) {
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
                                .clip(shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                                .background(color = MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = if (profileMatch.status == 0) "Declined" else "Accepted",
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
                                // Update Decline Status in Database
                            },
                        )

                        ProfileCardActionButton(
                            resId = R.drawable.ic_accept,
                            onClick = {
                                // Update Accept Status in Database
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
