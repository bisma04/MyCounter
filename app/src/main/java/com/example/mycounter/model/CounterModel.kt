package com.example.mycounter.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CounterModel(val count: Int) : Parcelable