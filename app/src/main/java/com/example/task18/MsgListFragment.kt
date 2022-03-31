package com.example.task18

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task18.databinding.FragmentMsgListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MsgListFragment : Fragment() {
    private lateinit var binding: FragmentMsgListBinding
    private val adapter = MsgAdapter()
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMsgListBinding.inflate(inflater, container, false)
        getData()
        return binding.root
    }

    private fun getData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(apiInterface::class.java)

        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object : Callback<List<MsgItem>?> {
            override fun onResponse(
                call: Call<List<MsgItem>?>,
                response: Response<List<MsgItem>?>
            ) {
                val responseBody = response.body()!!

                binding.rv.layoutManager = LinearLayoutManager(this@MsgListFragment.context)
                binding.rv.adapter = adapter

                for (msg in responseBody) {
                    adapter.addMsg(msg)
                }
            }

            override fun onFailure(call: Call<List<MsgItem>?>, t: Throwable) {
                Log.d("MsgListFragment", "onFailure: " + t.message)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = MsgListFragment()
    }
}