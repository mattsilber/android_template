package com.guardanis.blank.tools

import java.util.HashMap

class PendingEvents protected constructor() {

    private val pendingEvents = HashMap<String, () -> Unit>()

    fun register(key: String, event: () -> Unit) {
        pendingEvents.put(key, event)
    }

    fun unregister(key: String) {
        pendingEvents.remove(key)
    }

    fun trigger(key: String) {
        pendingEvents[key]
                ?.let({ it.invoke() })
    }

    fun has(key: String): Boolean {
        return pendingEvents[key] != null
    }

    companion object {

        private var sharedInstance: PendingEvents? = null
        fun getInstance(): PendingEvents {
            if (sharedInstance == null)
                sharedInstance = PendingEvents()

            return sharedInstance!!
        }
    }
}