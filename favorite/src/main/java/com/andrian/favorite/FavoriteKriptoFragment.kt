package com.andrian.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrian.core.ui.KriptoAdapter
import com.andrian.favorite.databinding.FragmentFavoriteKriptoBinding
import com.andrian.favorite.di.favoriteModule
import com.andrian.pantaukripto.detail.DetailKriptoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteKriptoFragment : Fragment() {

    private val favoriteKriptoViewModel: FavoriteKriptoViewModel by viewModel()
    private var _binding: FragmentFavoriteKriptoBinding? = null
    private val binding get() = _binding!!

    companion object {
        private var isModuleLoaded = false
        const val RESULT_CODE_UPDATE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isModuleLoaded) {
            loadKoinModules(favoriteModule)
            isModuleLoaded = true
        }
    }

    private val detailLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_CODE_UPDATE) {
                favoriteKriptoViewModel.refreshFavorites()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteKriptoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topAppBar.navigationIcon = null
        binding.topAppBar.title = "Favorite List"
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val kriptoAdapter = KriptoAdapter()
        kriptoAdapter.onItemClick = { selectedData ->
            val intent = Intent(requireContext(), DetailKriptoActivity::class.java)
            intent.putExtra(DetailKriptoActivity.EXTRA_DATA, selectedData)
            detailLauncher.launch(intent)
        }

        with(binding.rvFavorite) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = kriptoAdapter
        }

        favoriteKriptoViewModel.favoriteKripto.observe(viewLifecycleOwner) { favorites ->
            kriptoAdapter.submitList(favorites)
            binding.viewEmpty.root.visibility =
                if (favorites.isNotEmpty()) View.GONE else View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
