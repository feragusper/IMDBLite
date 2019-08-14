package com.feragusper.imdblite.movies.android

import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
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

    internal var itemClickListener: (Movie, Navigator.Extras) -> Unit = { _, _ -> }

    internal var favoriteButtonClickListener: (Movie, Boolean) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.row_movie))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(
            movie = collection[position],
            itemClickListener = itemClickListener,
            favoriteChangeListener = favoriteButtonClickListener
        )

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            movie: Movie,
            itemClickListener: (Movie, Navigator.Extras) -> Unit,
            favoriteChangeListener: (Movie, Boolean) -> Unit
        ) {

            itemView.moviePoster.loadFromUrl(movie.poster)
            itemView.movieTitle.text = movie.title
            itemView.movieRating.text = movie.voteAverage
            itemView.favoriteButton.isChecked = movie.favorite
            itemView.favoriteButton.setOnClickListener { view ->
                favoriteChangeListener(movie, (view as CheckBox).isChecked)
            }
            itemView.setOnClickListener { itemClickListener(movie, Navigator.Extras(itemView.moviePoster)) }
        }
    }
}
