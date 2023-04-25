package ca.qc.cstj.tptenretni.ui.loading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import ca.qc.cstj.tptenretni.R
import ca.qc.cstj.tptenretni.databinding.ActivityLoadingBinding
import ca.qc.cstj.tptenretni.ui.MainActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LoadingActivity : AppCompatActivity() {

    private val viewModel: LoadingViewModel by viewModels()
    private lateinit var binding: ActivityLoadingBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.loadingUiState.onEach {
            when(it){
                LoadingUiState.Empty -> Unit
                is LoadingUiState.Working -> {
                    binding.txvLoading.text = getString(R.string.Loading,it.progression)
                    binding.pgbLoading.setProgress(it.progression, true)
                }
                LoadingUiState.Finished -> {
                    binding.txvLoading.text = getString(R.string.completed)
                    startActivity(MainActivity.newIntent(this))
                }
            }
        }.launchIn(lifecycleScope)
    }
}