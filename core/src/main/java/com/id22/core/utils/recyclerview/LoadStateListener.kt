package com.id22.core.utils.recyclerview

import android.app.Activity
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.id22.core.utils.*
import com.id22.core.utils.ErrorJSON.errBody
import com.id22.core.utils.ErrorJSON.errMessage
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException

class LoadStateListener(private var loadState: CombinedLoadStates) {
    fun showLoadingState(): Boolean {
        return loadState.source.refresh is LoadState.Loading
    }

    fun showNotLoadingState(): Boolean {
        return loadState.source.refresh is LoadState.NotLoading
    }

    fun showEmptyState(itemCount: Int): Boolean {
        return loadState.refresh is LoadState.NotLoading && itemCount == 0
    }

    fun showErrorState(): Boolean {
        return loadState.source.refresh is LoadState.Error
    }

    fun showErrorMessageState(activity: Activity, message: String) {
        val errState = when {
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }

        when (val e = errState?.error) {
            is HttpException -> {
                val errorMessage = errMessage(
                    errorBody = errBody(e.response()),
                    objectKey1 = "status_message",
                )

                showSnackBarAlert(activity, errorMessage, LENGTH_SHORT)
            }
            is IOException,
            is JSONException,
            -> showSnackBarAlert(activity, message, LENGTH_SHORT)
        }
    }

    fun getCodeErrorState(): Int {
        val errCode = (loadState.source.refresh as LoadState.Error).error.localizedMessage

        return if (errCode?.contains(ERROR_101) == true) {
            101
        } else if (errCode?.contains(ERROR_400) == true) {
            400
        } else if (errCode?.contains(ERROR_401) == true) {
            401
        } else if (errCode?.contains(ERROR_402) == true) {
            402
        } else if (errCode?.contains(ERROR_403) == true) {
            403
        } else if (errCode?.contains(ERROR_404) == true) {
            404
        } else if (errCode?.contains(ERROR_406) == true) {
            406
        } else if (errCode?.contains(ERROR_409) == true) {
            409
        } else if (errCode?.contains(ERROR_412) == true) {
            412
        } else if (errCode?.contains(ERROR_417) == true) {
            417
        } else if (errCode?.contains(ERROR_428) == true) {
            428
        } else if (errCode?.contains(ERROR_500) == true) {
            500
        } else if (errCode?.contains(ERROR_502) == true) {
            502
        } else if (errCode?.contains(ERROR_503) == true) {
            503
        } else {
            999
        }
    }
}
