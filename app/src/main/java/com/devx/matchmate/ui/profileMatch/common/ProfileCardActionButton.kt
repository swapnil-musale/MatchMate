package com.devx.matchmate.ui.profileMatch.common

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devx.matchmate.R
import com.devx.matchmate.theme.MatchMateTheme

@Composable
fun ProfileCardActionButton(
    resId: Int,
    onClick: () -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(size = 60.dp)
                .clip(shape = CircleShape)
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
private fun ProfileCardActionButtonPreview() {
    MatchMateTheme {
        ProfileCardActionButton(resId = R.drawable.ic_accept, onClick = {})
    }
}
