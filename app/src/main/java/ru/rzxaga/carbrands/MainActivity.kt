package ru.rzxaga.carbrands

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

const val COLUMN_WIDTH = 128 + 16 + 16 // image size + image margin + card margin

class MainActivity : AppCompatActivity() {

    private val gridAdapter = GridAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val displayMetrics = resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val spanCount = (dpWidth / COLUMN_WIDTH).toInt()

        val viewManager = GridLayoutManager(this, spanCount)

        gridRecyclerView.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = gridAdapter

            addItemDecoration(GridSpacingItemDecoration(spanCount, COLUMN_WIDTH * displayMetrics.density))
        }
    }

    private inner class GridAdapter : RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup,
                                        viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.grid_item, parent, false) as View

            return ViewHolder(view)
        }

        // Replace the contents of a view (invoked by the layout manager)
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val imageView = holder.view.findViewById<ImageView>(R.id.image)
//            imageView.setImageBitmap(bitmap)

//            val detectedAge = holder.view.findViewById<TextView>(R.id.detectedAge)
//            detectedAge.text = photo.detectedAgeString(this@ModelActivity)

//            holder.view.setOnClickListener { showPhoto(position, bitmap, photo.agePrediction, photo.ageCorrection) }
        }

        // Return the size of your dataset (invoked by the layout manager)
        override fun getItemCount() = 0

    }

}
