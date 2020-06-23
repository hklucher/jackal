package com.brolo.jackal.ui.components

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.brolo.jackal.R
import kotlinx.android.synthetic.main.chart_legend.view.*

class ChartLegend @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyle, defStyleRes) {
    init {
        LayoutInflater.from(context).inflate(R.layout.chart_legend, this, true)

        attrs?.let {
            val drawable = chartLegendBox.background as GradientDrawable
            val defBackgroundColor = ContextCompat.getColor(context, R.color.colorMaterialBlue)
            val typedArray = context.obtainStyledAttributes(
                it, R.styleable.ChartLegend, defStyle, defStyleRes
            )

            val backgroundColor = ContextCompat.getColor(
                context,
                typedArray.getResourceId(
                    R.styleable.ChartLegend_background_color,
                    defBackgroundColor
                )
            )

            val title = resources.getText(
                typedArray.getResourceId(
                    R.styleable.ChartLegend_legend_title,
                    R.string.app_name
                )
            )

            chartLegendTitle.text = title

            drawable.setColor(backgroundColor)

            typedArray.recycle()
       }
    }
}
