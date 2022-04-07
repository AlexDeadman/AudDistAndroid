package com.alexdeadman.schedulecomposer.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.alexdeadman.schedulecomposer.R
import com.alexdeadman.schedulecomposer.databinding.ListItemBinding
import com.alexdeadman.schedulecomposer.model.entity.Attributes
import com.alexdeadman.schedulecomposer.model.entity.Entity
import com.github.florent37.expansionpanel.ExpansionLayout
import com.mikepenz.fastadapter.binding.AbstractBindingItem

class ListItem(
    private val entity: Entity<out Attributes>,
    private val relatives: List<Entity<out Attributes>>? = null,
) : AbstractBindingItem<ListItemBinding>() {

    override var identifier: Long
        get() = entity.hashCode().toLong()
        set(value) {
            super.identifier = value
        }

    override val type: Int get() = R.id.list_item_id

    val entityTitle = entity.title

    private var expansion: ExpansionLayout? = null
    var expanded = false
        set(value) {
            field = value
            if (value) expansion?.expand(true)
            else expansion?.collapse(true)
        }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): ListItemBinding =
        ListItemBinding.inflate(inflater, parent, false)

    override fun bindView(binding: ListItemBinding, payloads: List<Any>) {
        binding.apply {
            expansionLayout.run {
                expansion = this

                singleListener = true
                addListener { _, exp -> expanded = exp }

                if (expanded) expand(false)
                else collapse(false)
            }
            entity.run {
                textViewTitle.apply {
                    setCompoundDrawablesRelativeWithIntrinsicBounds(iconId, 0, 0, 0)
                    text = title
                }
                textViewDetails.text = root.resources.getString(
                    detailsPhId,
                    *getDetails(relatives.orEmpty()).toTypedArray()
                )
            }
        }
    }
}