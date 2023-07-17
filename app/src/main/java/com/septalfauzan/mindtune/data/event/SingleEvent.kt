package com.septalfauzan.mindtune.data.event

sealed class SingleEvent {
    data class MessageEvent(val message: String): SingleEvent()
}