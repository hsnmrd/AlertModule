package com.raika.alertmodule.progress

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.raika.alertmodule.R

class Progress(
    context: Context?,
    private val cancelable: Boolean,
    private val moduleProgress: ModuleProgress,
    private var layout: Int,
    private val onTaskCancelListener: (ModuleProgress) -> Unit,
    private var onViewCreate: ((view: View) -> Unit)? = null,
) : AlertDialog(context, R.style.DialogTheme) {

    init {
        this.setCancelable(false)
        val window = this.window
        val layoutParams = window?.attributes
        window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
        layoutParams?.dimAmount = 0f
        window?.attributes = layoutParams
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)

        window?.decorView?.rootView?.let { onViewCreate?.invoke(it) }
        window?.decorView?.rootView?.setOnClickListener {
            if (cancelable) {
                onTaskCancelListener(moduleProgress)
                moduleProgress.hide()
            }
        }

    }

}