package io.finefabric.ninebanana.entities

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Setting(
        @Id var id: Long = 0,
        var key: String? = null,
        var value: Boolean? = null)