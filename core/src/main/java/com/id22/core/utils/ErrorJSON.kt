package com.id22.core.utils

import org.json.JSONObject
import retrofit2.Response

object ErrorJSON {
    fun errBody(value: Response<*>?): String {
        val err = value?.errorBody()?.string()
        return if (!err.isNullOrEmpty()) err else ""
    }

    fun errMessage(
        errorBody: String,
        objectKey1: String,
        objectKey2: String? = null,
        objectKey3: String? = null
    ): String {
        return if (!objectKey2.isNullOrEmpty()) {
            JSONObject(errorBody)
                .getJSONObject(objectKey1)
                .getString(objectKey2)
        } else if (!objectKey2.isNullOrEmpty() && !objectKey3.isNullOrEmpty()) {
            JSONObject(errorBody)
                .getJSONObject(objectKey1)
                .getJSONObject(objectKey2)
                .getString(objectKey3)
        } else {
            JSONObject(errorBody)
                .getString(objectKey1)
        }
    }
}
