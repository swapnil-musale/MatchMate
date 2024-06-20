package com.devx.matchmate.ui.profileMatch

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.devx.data.utils.network.NetworkConnectivityManager
import com.devx.domain.model.ProfileMatch
import com.devx.matchmate.R
import com.devx.matchmate.theme.MatchMateTheme
import com.devx.matchmate.ui.common.MatchMateAppBar
import com.devx.matchmate.ui.profileMatch.common.ProfileMatchItem
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Composable
fun ProfileMatchScreen() {
    val profileMatchViewModel: ProfileMatchViewModel = hiltViewModel()
    val uiState by profileMatchViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        profileMatchViewModel.getProfileMatches()
    }

    ProfileMatchScreenContent(
        uiState = uiState,
        onProfileStatusUpdated = { userId, updatedStatus ->
            profileMatchViewModel.onProfileMatchStatusUpdated(
                userId = userId,
                updatedStatus = updatedStatus,
            )
        },
        refreshProfileMatches = {
            profileMatchViewModel.getProfileMatches(fromNetwork = true)
        },
    )
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
private fun ProfileMatchScreenContent(
    uiState: ProfileMatchScreenUiState,
    onProfileStatusUpdated: (userId: String, updatedStatus: Int) -> Unit,
    refreshProfileMatches: () -> Unit,
) {
    val localContext = LocalContext.current
    val connectivityManager = remember { NetworkConnectivityManager(context = localContext) }
    val isConnected =
        connectivityManager.isConnected.collectAsStateWithLifecycle(initialValue = false)

    val displayToast = { displayMessage: String ->
        Toast.makeText(localContext, displayMessage, Toast.LENGTH_SHORT).show()
    }

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(value = false) }

    fun refresh() =
        refreshScope.launch {
            refreshing = true
            refreshProfileMatches()
            refreshing = false
        }

    val pullRefreshState = rememberPullRefreshState(refreshing = refreshing, onRefresh = ::refresh)

    Scaffold(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        topBar = {
            MatchMateAppBar(title = stringResource(id = R.string.app_bar_title))
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .pullRefresh(state = pullRefreshState)
                    .padding(top = paddingValues.calculateTopPadding()),
        ) {
            when (uiState) {
                is ProfileMatchScreenUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                is ProfileMatchScreenUiState.Success -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        stickyHeader {
                            AnimatedVisibility(
                                visible = isConnected.value.not(),
                                enter = slideInVertically(),
                                exit = slideOutVertically(),
                            ) {
                                Text(
                                    modifier =
                                        Modifier
                                            .fillMaxWidth()
                                            .background(color = MaterialTheme.colorScheme.error),
                                    text = stringResource(id = R.string.no_internet_connection_message),
                                    color = MaterialTheme.colorScheme.onError,
                                    textAlign = TextAlign.Center,
                                )
                            }
                        }
                        items(
                            items = uiState.profileMatchList,
                            key = { it.userId },
                        ) { profileMatchItem ->
                            ProfileMatchItem(
                                profileMatch = profileMatchItem,
                                onProfileAccepted = {
                                    onProfileStatusUpdated(it.userId, 1)
                                    displayToast(localContext.getString(R.string.user_action_accepted))
                                },
                                onProfileDeclined = {
                                    onProfileStatusUpdated(it.userId, 0)
                                    displayToast(localContext.getString(R.string.user_action_declined))
                                },
                            )
                        }
                    }

                    PullRefreshIndicator(
                        refreshing = refreshing,
                        state = pullRefreshState,
                        modifier = Modifier.align(alignment = Alignment.TopCenter),
                    )
                }

                is ProfileMatchScreenUiState.Error -> {
                    if (uiState.message.isNotEmpty()) {
                        displayToast(uiState.message)
                    }
                }

                is ProfileMatchScreenUiState.Ideal -> {
                    Log.d("ProfileMatchScreen", "Ideal")
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileMatchScreenPreview() {
    MatchMateTheme {
        ProfileMatchScreenContent(
            uiState =
                ProfileMatchScreenUiState.Success(
                    profileMatchList =
                        listOf(
                            ProfileMatch(
                                userId = "abc",
                                name = "Swapnil Musale",
                                profilePicUrl = "",
                                address = "Mumbai",
                            ),
                        ).toImmutableList(),
                ),
            onProfileStatusUpdated = { _, _ -> },
            refreshProfileMatches = {},
        )
    }
}
