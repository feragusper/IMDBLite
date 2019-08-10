package com.feragusper.imdblite.movies.exception

import com.feragusper.imdblite.common.exception.Failure

class MovieFailure {
    class ListNotAvailable : Failure.FeatureFailure()
    class NonExistentMovie : Failure.FeatureFailure()
}