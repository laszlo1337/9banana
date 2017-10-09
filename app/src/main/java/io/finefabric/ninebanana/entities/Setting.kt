package io.finefabric.ninebanana.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Setting(val key: String, val value: Boolean) {
    @Id
    var id: Long = 0
}