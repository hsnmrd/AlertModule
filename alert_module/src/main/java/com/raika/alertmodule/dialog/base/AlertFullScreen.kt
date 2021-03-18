package com.raika.alertmodule.dialog.base

import android.content.Context
import androidx.appcompat.app.AlertDialog

open class AlertFullScreen(context : Context, style: Int) : AlertDialog(context, style) {

    var onContinue : (() -> Unit)? = null
    var onReject : (() -> Unit)? = null

    fun onRejectListener(onReject : () -> Unit) : AlertFullScreen {
        this.onReject = onReject
        return this
    }

    fun onContinueListener(onContinue : () -> Unit) : AlertFullScreen {
        this.onContinue = onContinue
        return this
    }

}
