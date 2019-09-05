package augarte.sendo.fragment

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

        github_button.setOnClickListener{
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(Constants.GITHUB_LINK))
            startActivity(browserIntent)

        }
    }
}