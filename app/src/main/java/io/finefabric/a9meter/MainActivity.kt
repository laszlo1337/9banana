package io.finefabric.a9meter

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

const val ninegagPackageString = "com.ninegag.android.app"

class MainActivity : AppCompatActivity() {

    var nineIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        launch9gag()
    }

    fun launch9gag(){
        nineIntent = packageManager.getLaunchIntentForPackage(ninegagPackageString)
        if (nineIntent != null) {
            nineIntent?.addCategory(Intent.CATEGORY_LAUNCHER)
            startService(Intent(this, ScrollDetectorService::class.java))
            startActivity(nineIntent)
        } else {
            AlertDialog.Builder(this)
                    .setTitle("9GAG app is not installed")
                    .setMessage("Looks like you haven't installed 9GAG app yet, would you like to do it now?")
                    .setNegativeButton("No") { dialog, _ ->
                        dialog.dismiss()
                        finish()
                    }
                    .setPositiveButton("Yes", { dialog, _ ->
                        dialog.dismiss()
                        try {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + ninegagPackageString)))
                        } catch (ex: ActivityNotFoundException) {
                            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + ninegagPackageString)))
                        }
                    }).show()
        }
    }
}
