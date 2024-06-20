package com.devx.matchmate.ui.profileMatch.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import com.devx.domain.model.ProfileMatch
import com.devx.matchmate.R
import com.devx.matchmate.theme.MatchMateTheme
import com.devx.matchmate.ui.common.VerticalDivider
import androidx.compose.ui.unit.dp as dp1

@Composable
fun ProfileMatchItem(
    profileMatch: ProfileMatch,
    onProfileAccepted: (ProfileMatch) -> Unit,
    onProfileDeclined: (ProfileMatch) -> Unit,
) {
    val localContext = LocalContext.current

    Card(modifier = Modifier.padding(all = 16.dp1)) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp1),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AsyncImage(
                modifier = Modifier.size(150.dp1),
                model = profileMatch.profilePicUrl,
                contentDescription = profileMatch.name,
                imageLoader =
                    ImageLoader
                        .Builder(context = localContext)
                        .crossfade(enable = true)
                        .build(),
            )

            VerticalDivider(height = 12.dp1)

            Text(
                text = profileMatch.name,
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                color = MaterialTheme.colorScheme.primary,
            )

            VerticalDivider(height = 4.dp1)

            Text(
                text = profileMatch.address,
                style = TextStyle(fontSize = 16.sp),
            )

            VerticalDivider(height = 16.dp1)

            Box(modifier = Modifier.fillMaxWidth()) {
                if (profileMatch.status == 0 || profileMatch.status == 1) {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .height(height = 60.dp1)
                                .clip(
                                    shape =
                                        RoundedCornerShape(
                                            topStart = 12.dp1,
                                            topEnd = 12.dp1,
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
                                .padding(bottom = 12.dp1),
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

@Preview
@Composable
private fun ProfileMatchItemPreview() {
    MatchMateTheme {
        ProfileMatchItem(
            profileMatch = ProfileMatch(userId = "", name = "", profilePicUrl = "", address = ""),
            onProfileAccepted = {},
            onProfileDeclined = {},
        )
    }
}
