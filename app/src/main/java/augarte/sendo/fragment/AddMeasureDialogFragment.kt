package augarte.sendo.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import augarte.sendo.R
import augarte.sendo.activity.MainActivity
import augarte.sendo.database.SelectTransactions
import kotlinx.android.synthetic.main.dialog_add_measure.view.*


class AddMeasureDialogFragment: DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val i = activity!!.layoutInflater
        val view = i.inflate(R.layout.dialog_add_measure, null)

        val list = MainActivity.dbHandler!!.getMeasureType(SelectTransactions.SELECT_ALL_MEASURETYPE, null)
        val items = MutableList(list!!.size) { i -> list[i].name}

        val arrayAdapter = ArrayAdapter<String>(context!!, R.layout.item_spinner, items)
        view.measureTypeSpiner.adapter = arrayAdapter

        return AlertDialog.Builder(activity)
                .setTitle("Add measure")
                .setPositiveButton("ADD",
                        DialogInterface.OnClickListener { dialog, whichButton ->

                        }
                )
                .setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, whichButton -> dialog.dismiss() }
                )
                .setView(view)
                .create()
    }
}