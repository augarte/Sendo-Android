package augarte.sendo.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import augarte.sendo.R
import android.content.Intent
import android.net.Uri
import augarte.sendo.utils.Constants
import kotlinx.android.synthetic.main.fragment_settings.*
import android.content.pm.PackageManager
import android.os.Handler
import augarte.sendo.activity.MainActivity
import augarte.sendo.dataModel.DateType
import augarte.sendo.dataModel.LengthType
import augarte.sendo.dataModel.WeightType
import augarte.sendo.database.DatabaseHandler
import augarte.sendo.database.SelectTransactions
import kotlinx.android.synthetic.main.fragment_measurements.*

class SettingsFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            val pInfo = context!!.packageManager.getPackageInfo(context!!.packageName, 0)
            val versionName = pInfo.versionName

            val applicationInfo = context!!.applicationInfo
            val stringId = applicationInfo.labelRes
            val appName = if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else context!!.getString(stringId);

            val versionCode = "$appName $versionName"
            version_code.text = versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        val list1: ArrayList<WeightType> = MainActivity.dbHandler!!.getWeightType(SelectTransactions.SELECT_ALL_WEIGHTTYPE, null)!!
        var selectedWeightIndex = 0
        list1.forEach { x-> if(x.choosed) selectedWeightIndex = list1.indexOf(x) }
        val items1 = Array(list1.size) { i -> list1[i].code}
        weight_type_chooseTV.text = items1[selectedWeightIndex]
        weight_type_choose.setOnClickListener{
            AlertDialog.Builder(context)
                .setTitle("Weight")
                .setSingleChoiceItems(items1, selectedWeightIndex) { dialog, item ->
                    selectedWeightIndex = item
                    MainActivity.dbHandler!!.updateWeightTypeChoosed(list1[selectedWeightIndex])
                    weight_type_chooseTV.text = items1[selectedWeightIndex]
                    Handler().postDelayed({dialog.dismiss()}, 500)
                }
                .show()
        }

        val list2: ArrayList<LengthType> = MainActivity.dbHandler!!.getLengthType(SelectTransactions.SELECT_ALL_LENGTHTYPE, null)!!
        var selectedLengthIndex = 0
        list2.forEach { x-> if(x.choosed) selectedLengthIndex = list2.indexOf(x) }
        val items2 = Array(list2.size) { i -> list2[i].code}
        length_type_chooseTV.text = items2[selectedLengthIndex]
        length_type_choose.setOnClickListener{
            AlertDialog.Builder(context)
                    .setTitle("Length")
                    .setSingleChoiceItems(items2, selectedLengthIndex) { dialog, item ->
                        selectedLengthIndex = item
                        MainActivity.dbHandler!!.updateLengthTypeChoosed(list2[selectedLengthIndex])
                        length_type_chooseTV.text = items2[selectedLengthIndex]
                        Handler().postDelayed({dialog.dismiss()}, 500)
                    }
                    .show()
        }

        github_button.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GITHUB_LINK))
            startActivity(browserIntent)

        }
    }
}