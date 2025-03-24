package com.andrian.pantaukripto.home

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.andrian.core.data.Resource
import com.andrian.core.ui.KriptoAdapter
import com.andrian.pantaukripto.databinding.FragmentHomeBinding
import com.andrian.pantaukripto.detail.DetailKriptoActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var query: String = ""
    private lateinit var kriptoAdapter: KriptoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        kriptoAdapter = KriptoAdapter().apply {
            onItemClick = { kripto ->
                val intent = Intent(requireContext(), DetailKriptoActivity::class.java)
                intent.putExtra(DetailKriptoActivity.EXTRA_DATA, kripto)
                startActivity(intent)
            }
        }

        with(binding.rvKripto) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = kriptoAdapter
        }

        homeViewModel.kriptoList.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val data = resource.data
                    if (data != null) {
                        kriptoAdapter.submitList(data)
                    }
                }

                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(context, resource.message ?: "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                query = s.toString()
                filterAndSortList(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filterAndSortList(query: String) {
        val currentList = homeViewModel.kriptoList.value?.data ?: return
        val filteredList = currentList.filter {
            it.symbol.contains(query, ignoreCase = true)
        }
        kriptoAdapter.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
