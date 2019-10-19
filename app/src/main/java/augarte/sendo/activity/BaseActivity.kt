package augarte.sendo.activity

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import augarte.sendo.R
import augarte.sendo.utils.Constants

abstract class BaseActivity : AppCompatActivity() {

    override fun getTheme(): Resources.Theme {
        val theme = super.getTheme()

        val sharedPreferences: SharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES, Context.MODE_PRIVATE)
        val themeId = sharedPreferences.getInt(Constants.SHARED_THEME, R.style.DarkTheme_NoActionBar)

        try {
           theme.applyStyle(themeId, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return theme
    }
}