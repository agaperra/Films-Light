package com.agaperra.filmslight.ui.interactor

import android.content.Context
import com.agaperra.filmslight.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class StringInteractorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : StringInteractor {

    override val textActor: String
        get() = context.getString(R.string.actor)
    override val textNoOverview: String
        get() = context.getString(R.string.noOverview)
    override val textUnknownRuntime: String
        get() = context.getString(R.string.unknownRuntime)
    override val textHour: String
        get() = context.getString(R.string.h)
    override val textMinute: String
        get() = context.getString(R.string.min)
    override val textUnknown: String
        get() = context.getString(R.string.unknown)
}
