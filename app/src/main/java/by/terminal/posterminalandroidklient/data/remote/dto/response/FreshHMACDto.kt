package by.terminal.posterminalandroidklient.data.remote.dto.response

import com.google.gson.annotations.SerializedName

internal data class FreshHMACDto(
    @SerializedName("hmacKey") val hmacKey: String,
    var count: Int = 1
)