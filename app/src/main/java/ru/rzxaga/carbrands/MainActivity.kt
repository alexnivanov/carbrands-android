package ru.rzxaga.carbrands

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


const val COLUMN_WIDTH = 128 + 16 + 16 // image size + image margin + card margin

class MainActivity : AppCompatActivity() {

    private val brands = arrayOf(
            Brand("audi", R.drawable.audi, R.string.audi),
            Brand("bmw", R.drawable.bmw, R.string.bmw),
            Brand("mercedes", R.drawable.mercedes, R.string.mercedes)
    )

    private val gridAdapter = GridAdapter()

    private var mediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val spanCount = (dpWidth / COLUMN_WIDTH).toInt()

        val viewManager = GridLayoutManager(this, spanCount)

        gridRecyclerView.apply {
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = gridAdapter

            addItemDecoration(GridSpacingItemDecoration(spanCount, COLUMN_WIDTH * displayMetrics.density))
        }
    }

    fun playAudio(fileName: String) {
        try {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.release()
            }

            mediaPlayer = MediaPlayer()

            val descriptor = assets.openFd(fileName)
            mediaPlayer.setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
            descriptor.close()

            mediaPlayer.prepare()
            mediaPlayer.setVolume(1f, 1f)
            mediaPlayer.start()
        } catch (e: Exception) {
            Log.e("AUDIO", "Failed to play audio: " + e.message, e)
        }
    }

    private inner class GridAdapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false) as View

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val brand = brands[position]

            val imageView = holder.view.findViewById<ImageView>(R.id.image)
            imageView.setImageResource(brand.image)

            val nameView = holder.view.findViewById<TextView>(R.id.name)
            nameView.text = getString(brand.name)

            holder.view.setOnClickListener {
                playAudio(brand.audioFileName())
            }
        }

        override fun getItemCount() = brands.size

    }

}

data class Brand(val audio: String, val image: Int, val name: Int) {

    fun audioFileName(): String {
        return "$audio.mp4"
    }

}
