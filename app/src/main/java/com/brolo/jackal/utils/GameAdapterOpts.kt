package com.brolo.jackal.utils

import com.brolo.jackal.ui.main.GameAdapter

data class GameAdapterOpts(
    val mode: GameAdapter.ViewMode = GameAdapter.ViewMode.Default,
    val clickable: Boolean = true
)
