package com.raika.alertmodule.dialog.base

import android.content.Context
import androidx.appcompat.app.AlertDialog

open class Alert(context : Context) : AlertDialog(context) {

    var onContinue : (() -> Unit)? = null
    var onReject : (() -> Unit)? = null

    fun onRejectListener(onReject : () -> Unit) : Alert {
        this.onReject = onReject
        return this
    }

    fun onContinueListener(onContinue : () -> Unit) : Alert {
        this.onContinue = onContinue
        return this
    }

}
