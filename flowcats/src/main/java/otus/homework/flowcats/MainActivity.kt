package otus.homework.flowcats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val diContainer = DiContainer()
    private val catsViewModel by viewModels<CatsViewModel> { CatsViewModelFactory(diContainer.repository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(R.layout.activity_main, null) as CatsView
        setContentView(view)

        lifecycleScope.launch {
            catsViewModel.catsStateFlow.flowWithLifecycle(lifecycle).collectLatest { update ->
                update?.let { result ->
                    when (result) {
                        is Result.Error -> { Toast.makeText(this@MainActivity, result.msg, Toast.LENGTH_SHORT).show() }
                        is Result.Success<*> -> {
                            val fact = result.result as? Fact
                            fact?.let { view.populate(it) }
                        }
                    }
                }
            }
        }
    }
}