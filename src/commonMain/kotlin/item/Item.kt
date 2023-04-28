package item

import data.BibTex
import kotlinx.serialization.Serializable

typealias ItemId = String

@Serializable
data class Item(
    val _id: ItemId,
    val elem: BibTex
)