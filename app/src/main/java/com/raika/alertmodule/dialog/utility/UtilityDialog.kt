package com.raika.alertmodule.dialog.utility

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.raika.alertmodule.dialog.base.Alert
import com.raika.alertmodule.dialog.model.ADModel

class UtilityDialog(context: Context, private val layout: Int) : Alert(context) {
    
    private var listener: ((ADModel) -> Unit)? = null
    
    fun setListener(listener: (ADModel) -> Unit) {
        this.listener = listener
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(layout, null)
        view?.let {
            setContentView(it)
            listener?.invoke(ADModel(context, view, this))
        }
    }
    
    fun onContinue() {
        onContinue?.invoke()
    }
    
    fun onReject() {
        onReject?.invoke()
    }
    
}