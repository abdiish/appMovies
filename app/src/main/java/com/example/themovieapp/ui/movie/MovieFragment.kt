package com.example.themovieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.themovieapp.R
import com.example.themovieapp.core.Resourse
import com.example.themovieapp.data.model.Movie
import com.example.themovieapp.data.remote.MovieDataSource
import com.example.themovieapp.databinding.FragmentMovieBinding
import com.example.themovieapp.presentation.MovieViewModel
import com.example.themovieapp.presentation.MovieViewModelFactory
import com.example.themovieapp.repository.MovieRepositoryImpl
import com.example.themovieapp.repository.RetrofitClient
import com.example.themovieapp.ui.movie.adapters.concat.MovieAdapter
import com.example.themovieapp.ui.movie.adapters.concat.PopularConcatAdapter
import com.example.themovieapp.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.example.themovieapp.ui.movie.adapters.concat.UpcomingConcatAdapter

// Los : significa que extiende
class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    // Injección de dependencias
    private lateinit var binding: FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> { MovieViewModelFactory(MovieRepositoryImpl(
        MovieDataSource(RetrofitClient.webService)
    )) }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Resourse.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    Log.d("LiveData", "Loading...")
                }
                is Resourse.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0, UpcomingConcatAdapter(MovieAdapter(result.data.first.results, this@MovieFragment)))
                        addAdapter(1, TopRatedConcatAdapter(MovieAdapter(result.data.second.results, this@MovieFragment)))
                        addAdapter(2, PopularConcatAdapter(MovieAdapter(result.data.third.results, this@MovieFragment)))

                    }

                    binding.rvMovies.adapter = concatAdapter

                    Log.d("LiveData", "Upcoming ${result.data.first} \n \n TopRated ${result.data.second} \n \n Popular ${result.data.third}")
                }
                is Resourse.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("Error", "${result.exception}")
                }
            }
        })

    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date)
        findNavController().navigate(action)
        Log.d("Movie", "onMovieClick: $movie")
    }
}