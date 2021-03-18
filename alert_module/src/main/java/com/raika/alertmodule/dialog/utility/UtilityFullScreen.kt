package com.raika.alertmodule.dialog.utility

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import com.raika.alertmodule.R
import com.raika.alertmodule.attr.ResAttribute
import com.raika.alertmodule.dialog.base.AlertFullScreen
import com.raika.alertmodule.dialog.model.DialogModel

class UtilityFullScreen(context: Context, private val layout: Int, style: Int) : AlertFullScreen(context, style) {
    
    private var listener: ((DialogModel) -> Unit)? = null
    
    fun setListener(listener: (DialogModel) -> Unit) {
        this.listener = listener
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val layoutInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(layout, null)
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window?.statusBarColor = ResAttribute(context, R.attr.white).getColor()
        }
        view?.let {
            setContentView(it)
            listener?.invoke(DialogModel(view, this))
        }
    }
    
    fun onContinue() {
        onContinue?.invoke()
    }
    
    fun onReject() {
        onReject?.invoke()
    }
    
}