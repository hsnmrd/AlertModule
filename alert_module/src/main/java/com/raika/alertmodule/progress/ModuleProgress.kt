package com.raika.alertmodule.progress

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

open class ModuleProgress(
    var activity: Activity,
    var layout: Int,
    var cancelable: Boolean = true,
    var dimAmount: Float = 0f,
    onViewCreate: ((View) -> Unit)? = null,
) {

    var progressActivity = activity
    private var progress: Progress? = null
    private var clickListener: ((context: Context) -> MutableLiveData<Boolean>)? = null

    init {
        progress = Progress(
            activity = activity,
            layout = layout,
            cancelable = cancelable,
            moduleProgress = this,
            dimAmount = dimAmount,
            onViewCreate = onViewCreate,
        )
        progress?.setCancelable(cancelable)
        progress?.setOnCancelListener {
            clickListener?.let {
                it(activity).observe(activity as LifecycleOwner) { isLoading ->
                    if (isLoading) show() else if (cancelable) hide()
                }
            }
        }
        progress?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun clickListener(clickListener: ((context: Context) -> MutableLiveData<Boolean>)?) {
        this.clickListener = clickListener
    }

    fun hide() {
        progress?.dismiss()
    }

    fun show() {
        (activity as LifecycleOwner).lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
            fun showDialog() {
                if (progress?.isShowing == false) {
                    progress?.show()
                    progress?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun hideDialog() {
                progress?.dismiss()
            }
        })
    }
}