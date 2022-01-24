package com.agaperra.filmslight.data.dto.credits

import com.agaperra.filmslight.domain.model.Actor


data class CastResponse(
        val cast: List<Actor>
)