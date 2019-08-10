package com.feragusper.imdblite.movies

import android.os.Parcel
import com.feragusper.imdblite.common.android.KParcelable
import com.feragusper.imdblite.common.android.parcelableCreator

data class MovieView(val id: Int, val poster: String?) : KParcelable {
    companion object {
        @JvmField
        val CREATOR = parcelableCreator(::MovieView)
    }

    constructor(parcel: Parcel) : this(parcel.readInt(), parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        with(dest) {
            writeInt(id)
            writeString(poster)
        }
    }
}
