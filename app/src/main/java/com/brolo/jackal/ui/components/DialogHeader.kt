package com.brolo.jackal.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.brolo.jackal.R
import kotlinx.android.synthetic.main.dialog_header.view.*

class DialogHeader @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {
    init {
        LayoutInflater.from(context).inflate(R.layout.dialog_header, this, true)

        attrs?.let {
            val typedArray = context.obtainStyledAttributes(
                it, R.styleable.DialogHeader, 0, 0
            )

            val title = resources.getText(
                typedArray.getResourceId(
                    R.styleable.DialogHeader_header_title,
                    R.string.app_name
                )
            )

            dialog_title.text = title

            typedArray.recycle()
        }
    }
}
