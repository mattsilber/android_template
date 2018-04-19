package com.guardanis.blank.tools.actions

import android.content.Context
import java.util.ArrayList

class ActionResolver {

    private val resolvables = ArrayList<Resolvable>()

    interface Resolvable {
        fun isResolutionRequired(context: Context): Boolean
        fun resolve(context: Context, success: () -> Unit, fail: () -> Unit)
    }

    fun add(resolvable: Resolvable): ActionResolver {
        resolvables.add(resolvable)
        return this
    }

    fun resolve(context: Context, success: () -> Unit, fail: (() -> Unit)?) {
        for (resolvable in resolvables) {
            if (resolvable.isResolutionRequired(context)) {
                resolvable.resolve(context, { resolve(context, success, fail) }, fail ?: { })

                return
            }
        }

        success.invoke()
    }
}
