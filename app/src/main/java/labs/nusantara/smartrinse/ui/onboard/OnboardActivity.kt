package labs.nusantara.smartrinse.ui.onboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import labs.nusantara.smartrinse.databinding.ActivityOnboardBinding

class OnboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}