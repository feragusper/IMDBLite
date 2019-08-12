package com.feragusper.imdblite.movies.exception

import com.feragusper.imdblite.common.exception.Failure

class MovieFailure {
    object ListNotAvailable : Failure.FeatureFailure()
    object NonExistentMovie : Failure.FeatureFailure()
}