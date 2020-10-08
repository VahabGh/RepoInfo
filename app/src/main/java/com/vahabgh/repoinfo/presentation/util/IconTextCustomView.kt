package com.vahabgh.repoinfo.presentation.util

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.vahabgh.repoinfo.R
import com.vahabgh.repoinfo.databinding.IconTextCustomViewBinding

class IconTextCustomView : ConstraintLayout {

    var binding : IconTextCustomViewBinding?=null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init(){
        val inflater = LayoutInflater.from(context)
        binding = IconTextCustomViewBinding.inflate(inflater, this, true)
    }

    fun bind(icon : Int,title : String,value: String){
        binding?.ivIcon?.setImageResource(icon)
        binding?.tvIcon?.text = title
        binding?.tvIconValue?.text = value
    }
}

@BindingAdapter("icon","title","value")
fun IconTextCustomView.bindItem(icon : Int,title: String,value: String){
    bind(icon, title, value)
}


