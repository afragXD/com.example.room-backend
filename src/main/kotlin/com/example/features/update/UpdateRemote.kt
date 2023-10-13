package com.example.features.update

import kotlinx.serialization.Serializable

@Serializable
data class UpdateReceiveRemote(
    var updateValue: String,
)
