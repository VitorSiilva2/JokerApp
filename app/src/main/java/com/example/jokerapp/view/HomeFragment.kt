package com.example.jokerapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jokerapp.R
import com.example.jokerapp.data.CategoryRemoteDataSource
import com.example.jokerapp.model.Category
import com.example.jokerapp.presentation.HomePresenter
import com.xwray.groupie.GroupieAdapter


class HomeFragment : Fragment () {

    private lateinit var progressbar : ProgressBar

    private lateinit var presenter : HomePresenter

    private val adapter = GroupieAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dataSource = CategoryRemoteDataSource()
        presenter = HomePresenter(this, dataSource)

    }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.fragment_home, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            progressbar =view.findViewById(R.id.progress_bar)

            val recyclerView = view.findViewById<RecyclerView>(R.id.rv_main)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            if (adapter.itemCount == 0) {
                presenter.findAllCategories()
            }

            recyclerView.adapter = adapter

            adapter.setOnItemClickListener{ item, view ->
                val bundle = Bundle()
                val categoryName = (item as CategoryItem).category.name
                    bundle.putString("category", categoryName )
                findNavController().navigate(R.id.action_nav_home_to_nav_joke, bundle)

            }

        }

    fun showCategories(response: List<Category>) {
        val categories = response.map {CategoryItem(it)}
        adapter.addAll(categories)
        adapter.notifyDataSetChanged()
    }

    fun showProgress(){
        progressbar.visibility = View.VISIBLE

    }

    fun hideProgress() {
        progressbar.visibility = View.GONE
    }

    fun showFailure(message : String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    }