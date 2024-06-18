package com.devx.matchmate.ui.profileMatch

import com.devx.domain.model.ProfileMatch

data class ProfileMatchScreenUiState(
    val isLoading: Boolean = false,
    val profileMatchList: List<ProfileMatch>? = null,
    val errorMessage: String? = null,
)
