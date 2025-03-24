package com.andrian.pantaukripto.about

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import com.andrian.pantaukripto.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.topAppBar.navigationIcon = null
        binding.topAppBar.title = "My Profile"

        startAnimations()
    }

    private val handler = Handler(Looper.getMainLooper())

    private val animationRunnable = Runnable {
        _binding?.let {
            it.profileName.animate().alpha(1f).setDuration(500).start()
            it.profileEmail.animate().alpha(1f).setDuration(500).start()
        }
    }

    private fun startAnimations() {
        binding.profileImage.translationY = 100f
        binding.profileImage.alpha = 0f
        binding.profileImage.animate().translationY(0f).alpha(1f).setDuration(800)
            .setInterpolator(BounceInterpolator()).start()

        binding.profileName.alpha = 0f
        binding.profileEmail.alpha = 0f

        handler.postDelayed(animationRunnable, 800)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacks(animationRunnable)
        _binding = null
    }
}
