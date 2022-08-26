package com.example.gittrends.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.gittrends.R
import com.example.gittrends.databinding.ActivityMainBinding
import com.example.gittrends.interfaces.APIInterface
import com.example.gittrends.model.TrendingGitResp
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.toolbar.setTitle(getString(R.string.toolbar_title))
        var retrofit = Retrofit.Builder()
            .baseUrl("http://api.github.com/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        val api = retrofit.create(APIInterface::class.java); //Part 2
        binding.toolbar.setOnClickListener() {

        }
    }
}