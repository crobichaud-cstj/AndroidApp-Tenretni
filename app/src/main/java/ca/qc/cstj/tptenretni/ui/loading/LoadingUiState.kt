package ca.qc.cstj.tptenretni.ui.loading

sealed class LoadingUiState {
    object Empty : LoadingUiState()
    class Working(val progression: Int): LoadingUiState()
    object Finished: LoadingUiState()
}