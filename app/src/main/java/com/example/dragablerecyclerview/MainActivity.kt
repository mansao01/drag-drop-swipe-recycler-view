package com.example.dragablerecyclerview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dragablerecyclerview.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Sports>()
    private lateinit var listSportsAdapter: ListSportsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = resources.getString(R.string.material_me)

        binding.rvSports.setHasFixedSize(true)

        list.addAll(listSports)

        binding.swipeRefresh.setOnRefreshListener {
            refreshRecyclerView()
            binding.swipeRefresh.isRefreshing = false
        }

        showRecyclerView()
    }

    private val listSports: ArrayList<Sports>
        get() {
            val dataTitle = resources.getStringArray(R.array.sports_titles)
            val dataInfo = resources.getStringArray(R.array.sports_info)
            val dataImage = resources.obtainTypedArray(R.array.sports_image)
            val dataDescription = resources.getStringArray(R.array.sports_description)

            val listSport = ArrayList<Sports>()
            for (i in dataTitle.indices) {
                val sport = Sports(dataTitle[i], dataInfo[i], dataImage.getResourceId(i, -1), dataDescription[i])
                listSport.add(sport)
            }
            return listSport
        }


    private var simpleCallback =
        object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP.or(ItemTouchHelper.DOWN),
            ItemTouchHelper.LEFT.or(ItemTouchHelper.RIGHT)
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val startPosition = viewHolder.adapterPosition
                val endPosition = target.adapterPosition

                Collections.swap(list, startPosition, endPosition)
                recyclerView.adapter?.notifyItemMoved(startPosition, endPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                list.removeAt(viewHolder.adapterPosition)
                listSportsAdapter.notifyItemRemoved(viewHolder.adapterPosition)
            }

        }

    private fun refreshRecyclerView() {
        list.clear()
        binding.rvSports.layoutManager = LinearLayoutManager(this)
        listSportsAdapter = ListSportsAdapter(list)
        binding.rvSports.adapter = listSportsAdapter
        list.addAll(listSports)

        listSportsAdapter.setOnItemClickCallBack(object : ListSportsAdapter.OnItemCLickCallBack {
            override fun onItemClicked(data: Sports) {
                showSelectedUser(data)
            }
        })
    }


    private fun showRecyclerView() {
        binding.rvSports.layoutManager = LinearLayoutManager(this)
        listSportsAdapter = ListSportsAdapter(list)
        binding.rvSports.adapter = listSportsAdapter

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.rvSports)

        listSportsAdapter.setOnItemClickCallBack(object : ListSportsAdapter.OnItemCLickCallBack {
            override fun onItemClicked(data: Sports) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: Sports) {
        val moveToDetailIntent = Intent(this, DetailActivity::class.java)
        moveToDetailIntent.putExtra(DetailActivity.EXTRA_DATA, data)

        startActivity(moveToDetailIntent)
    }
}
