package com.feragusper.imdblite.movies.android

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.feragusper.imdblite.R
import com.feragusper.imdblite.common.extension.inflate
import com.feragusper.imdblite.common.extension.loadFromUrl
import com.feragusper.imdblite.common.navigation.Navigator
import com.feragusper.imdblite.movies.domain.Movie
import kotlinx.android.synthetic.main.row_movie.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class MoviesAdapter
@Inject constructor() : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    internal var collection: List<Movie> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var clickListener: (Movie, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.row_movie))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie, clickListener: (Movie, Navigator.Extras) -> Unit) {
            itemView.moviePoster.loadFromUrl(movie.poster)
            itemView.movieTitle.text = movie.title
            itemView.movieRating.text = movie.voteAverage
            itemView.setOnClickListener { clickListener(movie, Navigator.Extras(itemView.moviePoster)) }
        }
    }
}
