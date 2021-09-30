package com.tx.txcustomview.list_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import com.tx.txcustomview.databinding.ActivityListTestBinding

class ListTestActivity : AppCompatActivity() {
    lateinit var binding : ActivityListTestBinding
    lateinit var adapter : ListAdapter
    var count = 0
    private var list = MyObservableArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        initList()
    }
    private fun initList(){
        adapter = ListAdapter(list)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = this.adapter
        binding.addSingleBtn.setOnClickListener { addSingle() }
        binding.addMultiBtn.setOnClickListener { addMulti() }
        binding.removeSingleBtn.setOnClickListener { removeSingle() }
        binding.removeMultiBtn.setOnClickListener { removeMulti() }
    }
    private fun addSingle(){
        list.add("$count")
        count++
    }
    private fun addMulti(){
        for (index in 1..3){
            list.add("$count")
            count++
        }
    }
    private fun removeSingle(){
        if (list.isEmpty()){
            return
        }
        list.removeAt((list.size-1))
    }
    private fun removeMulti(){
        if (list.isEmpty()){
            return
        }
        var remove = ArrayList<String>()
        remove.addAll(list)
        for (index in 0 until remove.size){
            list.remove(remove[index])
        }
    }

}