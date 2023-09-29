package com.example.eval8.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eval8.adapter.DataItem
import com.example.eval8.adapter.DataItemAdapter
import com.example.eval8.databinding.FragmentHomeBinding
import com.example.eval8.viewModel.ApiStatus
import com.example.eval8.viewModel.DataViewModel
import com.example.eval8.viewModel.DataViewModelFactory

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: DataViewModel by lazy {
        val activity = requireNotNull(this.activity) {
        }
        ViewModelProvider(this, DataViewModelFactory(activity.application))
            .get(DataViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DataItemAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.allData.observe(viewLifecycleOwner) {
            var dataset = mutableListOf<DataItem>()
            it.map {
                dataset.add(DataItem.DataHeader(it.id))
                dataset.add(DataItem.DataBody(it))
            }

            adapter.submitList(dataset)
        }

        viewModel.status.observe(viewLifecycleOwner) {
            when (it) {
                ApiStatus.LOADING -> binding.statusText.text = "Loading.."
                ApiStatus.FAILURE -> binding.statusText.text = "Couldn't fetch data"
                ApiStatus.SUCCESS -> binding.statusText.visibility = View.GONE
            }
        }
    }


}