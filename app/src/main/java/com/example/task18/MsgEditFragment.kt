package com.example.task18

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.task18.databinding.FragmentMsgEditBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MsgEditFragment : Fragment() {
    private lateinit var binding: FragmentMsgEditBinding
    private val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private lateinit var msg: MsgItem

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMsgEditBinding.inflate(inflater, container, false)

        msg = MsgEditFragmentArgs.fromBundle(requireArguments()).msg

        binding.etTitle.setText(msg.title)
        binding.etBody.setText(msg.body)

        binding.btnUpdateData.setOnClickListener {
            msg.title = binding.etTitle.text.toString()
            msg.body = binding.etBody.text.toString()
            updateData()
        }

        return binding.root
    }

    private fun updateData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(apiInterface::class.java)

        val retrofitData = retrofitBuilder.updateData(msg.body, msg.id, msg.title, msg.userId)

        retrofitData.enqueue(object : Callback<MsgItem?> {
            override fun onResponse(call: Call<MsgItem?>, response: Response<MsgItem?>) {
                Log.d("MsgEditFragment", response.message())
            }

            override fun onFailure(call: Call<MsgItem?>, t: Throwable) {
                Log.d("MsgEditFragment", "onFailure: " + t.message)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = MsgEditFragment()
    }
}