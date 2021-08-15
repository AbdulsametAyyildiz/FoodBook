package com.kalemlisipahi.foodbook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.kalemlisipahi.foodbook.R
import com.kalemlisipahi.foodbook.adapter.FoodAdapter
import com.kalemlisipahi.foodbook.model.FoodModel
import com.kalemlisipahi.foodbook.viewmodel.ListFragmentViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import java.util.ArrayList

class ListFragment : Fragment() {

    private lateinit var viewModel : ListFragmentViewModel
    private val adapter =  FoodAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(ListFragmentViewModel::class.java)
        viewModel.refreshData()
        recyclerView.layoutManager = LinearLayoutManager(context)

        recyclerView.adapter = adapter

        listSwipeRefreshLayout.setOnRefreshListener {
            listProgressBar.visibility = View.VISIBLE
            listErrorText.visibility = View.GONE
            recyclerView.visibility = View.GONE
            viewModel.refreshLayout()

        }

        observeLiveData()
    }

    private fun observeLiveData()
    {
        viewModel.foodListGlobal.observe(viewLifecycleOwner,
            {
            it.let {

                recyclerView.visibility = View.VISIBLE
                adapter.updateData(it)
                }
            })

        viewModel.foodErrorMessage.observe(viewLifecycleOwner,
            {
                it?.let {
                    if(it)
                    {
                        recyclerView.visibility = View.GONE
                        listErrorText.visibility = View.VISIBLE
                        listProgressBar.visibility = View.GONE
                    }else
                    {
                        listErrorText.visibility = View.GONE
                    }

                }
            })

        viewModel.foodLoading.observe(viewLifecycleOwner,
            {
                it?.let {
                    if (it)
                    {
                        recyclerView.visibility = View.GONE
                        listProgressBar.visibility = View.VISIBLE
                    }else
                    {
                        listProgressBar.visibility = View.GONE
                        if(listSwipeRefreshLayout.isRefreshing) listSwipeRefreshLayout.isRefreshing = false
                    }

                }
            })
    }


}