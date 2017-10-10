package io.finefabric.ninebanana

import io.finefabric.ninebanana.entities.Setting
import io.objectbox.BoxStore
import io.objectbox.kotlin.boxFor

class PresetsManager(database: BoxStore) {

    private val SHOW_KILOMETERS: String = "show_kilometers"
    private val SHOW_BANANAS: String = "show_bananas"
    private val SHOW_MILES: String = "show_miles"
    private val DARK_MODE: String = "dark_mode"

    init {
        val settingsBox = database.boxFor<Setting>()

        if (settingsBox.all.size == 0){
            settingsBox.put(arrayListOf(
                    Setting(0, SHOW_KILOMETERS, false),
                    Setting(0, SHOW_BANANAS, true),
                    Setting(0, SHOW_MILES, false),
                    Setting(0, DARK_MODE, true)
            ))
        }
    }
}