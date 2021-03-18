package com.raika.alertmodule.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.raika.alertmodule.R
import com.raika.alertmodule.dialog.model.DialogModel
import com.raika.alertmodule.dialog.utility.UtilityFullScreen

open class ModuleFullScreenAlert(var context: Context, layout: Int) {

    private var adUtility: UtilityFullScreen = UtilityFullScreen(context, layout, R.style.FullScreenDialogLight)

    init {
        adUtility.setCancelable(false)
        adUtility.requestWindowFeature(Window.FEATURE_NO_TITLE)
        adUtility.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        adUtility.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    /**
     * **setCancelable** sets whether this dialog is cancelable with the
     * @param cancelable: pass a boolean (true: cancelable - false: not cancelable)
     */
    fun setCancelable(cancelable: Boolean): ModuleFullScreenAlert {
        adUtility.setCancelable(cancelable)
        return this
    }

    /**
     * **onViewCreate** access to [ADModel.view] and [ADModel.dialogRD]
     * @param listener: lambda function to receive objects
     */
    fun onViewCreate(listener: (DialogModel) -> Unit): ModuleFullScreenAlert {
        adUtility.setListener(listener)
        return this
    }

    /**
     * **show** show dialog box
     */
    fun show(): UtilityFullScreen {
        (context as LifecycleOwner).lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun showDialog() {
                if (!adUtility.isShowing) {
                    adUtility.show()
                    adUtility.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
                }
            }
        
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun hideDialog() {
                adUtility.dismiss()
            }
        })
    
        return adUtility
    }

    /**
     * **dismiss** dismiss dialog box
     */
    fun dismiss() {
        adUtility.dismiss()
    }

    /**
     * **build** get created dialog box
     */
    fun build(): UtilityFullScreen {
        return adUtility
    }

}