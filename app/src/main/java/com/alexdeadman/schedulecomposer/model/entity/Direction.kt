package com.alexdeadman.schedulecomposer.model.entity

import com.alexdeadman.schedulecomposer.R

data class Direction(
    override var type: String,
    override var id: Int,
    override var attributes: DirectionAttributes,
) : Entity<Direction.DirectionAttributes> {

    override val title get() = attributes.code
    override val iconId get() = R.drawable.ic_direction

    override val detailsPhId: Int get() = R.string.ph_direction_details
    override val details get() = mutableListOf(attributes.name)

    data class DirectionAttributes(
        var code: String,
        var name: String,
    ) : Attributes
}
