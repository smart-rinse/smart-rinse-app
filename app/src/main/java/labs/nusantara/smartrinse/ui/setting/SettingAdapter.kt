package labs.nusantara.smartrinse.ui.setting

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import labs.nusantara.smartrinse.R

class SettingAdapter(
    context: Context,
    private val items: Array<String>
) : ArrayAdapter<String>(context, R.layout.item_setting, R.id.text_item, items) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(R.id.text_item)
        val arrowView = view.findViewById<ImageView>(R.id.img_arrowitem)

        if (items[position] == "Logout") {
            textView.setTextColor(ContextCompat.getColor(context, R.color.sr_main_red))
            arrowView.setColorFilter(ContextCompat.getColor(context, R.color.sr_main_red))
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.sr_main_black))
            arrowView.setColorFilter(ContextCompat.getColor(context, R.color.sr_main_black))
        }

        return view
    }
}
