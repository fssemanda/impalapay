package cloud.nymbow.impalapay.loginApi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cloud.nymbow.impalapay.BuildConfig
import cloud.nymbow.impalapay.MainActivity
import cloud.nymbow.impalapay.R

import cloud.nymbow.impalapay.databinding.FragmentSignInBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.AuthUI.IdpConfig
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

class SignIn : Fragment() {

    private var _binding: FragmentSignInBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()

        binding.loginBtn.setOnClickListener {

            var authProviders = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()

            )
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(authProviders).setIsSmartLockEnabled(!BuildConfig.DEBUG, true).setTheme(
                R.style.Theme_ImpalaPay).build(),
                RC_SIGN_IN)
        }
        val googleSignBtn = binding.gSignIn
        googleSignBtn.setOnClickListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.GoogleBuilder().build()
            )
            startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(!BuildConfig.DEBUG, true)
                .build(), RC_SIGN_IN)
        }

      loginFunc()



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN){
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK){
                val user = FirebaseAuth.getInstance().currentUser
                val intent= Intent(this.context,MainActivity::class.java)
                intent.putExtra(USER_ID,user!!.uid)
                intent.putExtra("uid",user!!.uid)
                startActivity(intent)
            }
        }
    }


    private fun loginFunc(){

        val auth_instance = FirebaseAuth.getInstance()
        if (auth_instance.currentUser!=null){
            val intent = Intent(this.context, MainActivity::class.java)
            intent.putExtra(USER_ID,auth_instance.currentUser!!.uid)
            startActivity(intent)
        }
    }


    companion object{
        const val USER_ID = "user_id"
        const val RC_SIGN_IN = 15
    }



}
