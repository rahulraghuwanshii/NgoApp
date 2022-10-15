package com.rahulraghuwanshi.ngoapp.utils

import android.app.ProgressDialog
import android.content.Context

class Utils {

    companion object{
        fun progressDialog(context: Context) : ProgressDialog =  ProgressDialog.show(context, "Loading", "Please wait...", true)
    }
}