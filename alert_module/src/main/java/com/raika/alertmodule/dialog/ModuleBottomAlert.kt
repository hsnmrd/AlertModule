package com.raika.alertmodule.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.raika.alertmodule.dialog.model.ADModel
import com.raika.alertmodule.dialog.utility.UtilityDialog

/**
 * **ADTaskBottom** a class to show a dialog box in bottom of screen
 * @param context: pass context
 * @param layout: pass a layout to show in dialog box
 */
open class ModuleBottomAlert(var context: Context, layout: Int, dimValue: Float = 0.5f) {

    private val adUtilityDialog: UtilityDialog = UtilityDialog(context, layout)

    init {
        adUtilityDialog.setCancelable(false)
        val window = adUtilityDialog.window
        val lp = window?.attributes
        window?.setDimAmount(dimValue)
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        lp?.width = ViewGroup.LayoutParams.MATCH_PARENT
        lp?.gravity = Gravity.BOTTOM
        
        window?.attributes = lp
    }
    
    /**
     * **setCancelable** sets whether this dialog is cancelable with the
     * @param cancelable: pass a boolean (true: cancelable - false: not cancelable)
     */
    fun setCancelable(cancelable: Boolean): ModuleBottomAlert {
        adUtilityDialog.setCancelable(cancelable)
        adUtilityDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        adUtilityDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return this
    }
    
    
    /**
     * **setListener** access to [ADModel.view] and [ADModel.utilityDialogRD]
     * @param listener: lambda function to receive objects
     */
    fun setListener(listener: (dialog: ADModel) -> Unit): ModuleBottomAlert {
        adUtilityDialog.setListener(listener)
        return this
    }
    
    
    /**
     * **onViewCreate** access to [ADModel.view] and [ADModel.utilityDialogRD]
     * @param listener: lambda function to receive objects
     */
    fun onViewCreate(listener: (dialog: ADModel) -> Unit): ModuleBottomAlert {
        adUtilityDialog.setListener(listener)
        return this
    }

    /**
     * **dismiss** dismiss dialog box
     */
    fun dismiss() {
        adUtilityDialog.dismiss()
    }
    
    /**
     * **show** show dialog box
     */
    fun show() : UtilityDialog {
        (context as LifecycleOwner).lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun showDialog() {
                if (!adUtilityDialog.isShowing) {
                    adUtilityDialog.show()
                    adUtilityDialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
                }
            }
        
            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun hideDialog() {
                adUtilityDialog.dismiss()
            }
        })
        
        return adUtilityDialog
    }
    
    /**
     * **build** get created dialog box
     */
    fun build() : UtilityDialog {
        return adUtilityDialog
    }

}