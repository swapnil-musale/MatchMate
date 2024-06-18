package com.devx.matchmate.ui.profileMatch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.devx.matchmate.theme.MatchMateTheme

@Composable
fun ProfileMatchScreen(uiState: ProfileMatchScreenUiState) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        } else {
            Text(text = uiState.profileMatchList?.toString() ?: "")
        }
    }
}

@Preview
@Composable
private fun ProfileMatchScreenPreview() {
    MatchMateTheme {
        ProfileMatchScreen(uiState = ProfileMatchScreenUiState(isLoading = true))
    }
}
