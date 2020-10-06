package com.vahabgh.repoinfo.presentation.util

import android.content.Context
import android.content.SharedPreferences

object Preferences {

    const val NAME = "SharedPreferences"

    fun preferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(NAME, 0)
    }

    fun editor(
        context: Context
    ): SharedPreferences.Editor {
        return preferences(context).edit()
    }

}
