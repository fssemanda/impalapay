package cloud.nymbow.impalapay.loginApi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cloud.nymbow.impalapay.R

import cloud.nymbow.impalapay.databinding.FragmentSplashScreenBinding


class SplashScreenFragment : Fragment() {

    private var _binding: FragmentSplashScreenBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            val context = requireContext()
            Toast.makeText(context,"Hello",Toast.LENGTH_SHORT).show()

        }

}