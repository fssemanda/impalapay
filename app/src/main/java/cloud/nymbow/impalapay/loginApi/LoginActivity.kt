



package cloud.nymbow.impalapay.loginApi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import cloud.nymbow.impalapay.R
import cloud.nymbow.impalapay.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navContainer1) as NavHostFragment
        navController = navHostFragment.navController

        Handler().postDelayed({
            navController.navigate(R.id.action_splashScreenFragment_to_signIn)
        },3000

        )



    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}