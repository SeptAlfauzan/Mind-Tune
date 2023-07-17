package com.septalfauzan.mindtune.utils

fun Int.formatTwoDigits(): String = when(this){
    in 0 until 10 -> "0$this"
    else -> this.toString()
}