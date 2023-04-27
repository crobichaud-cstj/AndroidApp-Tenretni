package ca.qc.cstj.tptenretni.ui.loading

import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ca.qc.cstj.tptenretni.core.Constants
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoadingViewModel : ViewModel() {

    private val _loadingUiState = MutableStateFlow<LoadingUiState>(LoadingUiState.Empty)
    val loadingUiState = _loadingUiState.asStateFlow()

    private var _timerCounter = 0

    private val timer = object : CountDownTimer(Constants.Refresh_Delay.LOADING_TIME, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _timerCounter++
            _loadingUiState.update {
                LoadingUiState.Working(_timerCounter)
            }
        }

        override fun onFinish() {
            cancel()
            _loadingUiState.update {
                LoadingUiState.Finished
            }
        }

    }
    init {
        timer.start()
    }

    fun startTimer() {
        _timerCounter = 0
        timer.start()
    }

}
