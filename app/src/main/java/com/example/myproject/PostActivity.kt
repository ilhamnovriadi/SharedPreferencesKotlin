package com.example.myproject

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PostActivity : AppCompatActivity() {

    private lateinit var progressBar: ProgressBar
    private lateinit var rvPosts: RecyclerView
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        progressBar = findViewById(R.id.progressBar)
        rvPosts = findViewById(R.id.rvPosts)

        rvPosts.layoutManager = LinearLayoutManager(this)
        postAdapter = PostAdapter(emptyList())
        rvPosts.adapter = postAdapter

        fetchPosts()
    }

    private fun fetchPosts() {
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val posts = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.getPosts()
                }
                postAdapter.updatePosts(posts)
            } catch (e: Exception) {
                Toast.makeText(this@PostActivity, "Error fetching posts: ${e.message}", Toast.LENGTH_LONG).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }
}
