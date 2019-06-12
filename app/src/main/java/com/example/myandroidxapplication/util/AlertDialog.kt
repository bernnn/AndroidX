package com.example.myandroidxapplication.util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.myandroidxapplication.R
import java.lang.Exception

fun AppCompatActivity.showDialog(
    title: String? = "Oops!",
    message: String = "",
    okBtnTitle: String = "Ok",
    okClickListener: ((dialog: DialogInterface, which: Int) -> Unit)? = { d, w -> },
    cancelBtnTitle: String? = null,
    cancelBtnListener: ((dialog: DialogInterface, which: Int) -> Unit)? = { d, w -> }
    ) {
        val dialog = GcDialog(
            if (title.isNullOrEmpty()) "" else title!!,
            if (message.isNullOrEmpty()) "" else message,
            if (okBtnTitle.isNullOrEmpty()) "Ok" else okBtnTitle,
            DialogInterface.OnClickListener { dialog, which ->
                okClickListener?.let { it.invoke(dialog, which) }
            },
            if (cancelBtnTitle.isNullOrEmpty()) null else cancelBtnTitle,
            DialogInterface.OnClickListener { dialog, which ->
                cancelBtnListener?.let { it.invoke(dialog, which) }
            })

        try {
            if (!isFinishing) {
                val handler = Handler(Looper.getMainLooper())
                handler.post {
                    dialog.isCancelable = false
                    dialogShowManager(supportFragmentManager, dialog, "alert-dialog-fragment")

                }
            }
        }catch (ex: Exception){
            Log.e("alertDialogException", ex.message )
        }
    }

class GcDialog : DialogFragment {

    companion object {
        val dialogFragmentLifecycle: FragmentManager.FragmentLifecycleCallbacks by lazy { Lifecycle() }
    }

    private var title: String = ""
    private var message: String = ""
    private var okBtnTitle: String = ""
    private var okBtnListener: DialogInterface.OnClickListener? = null
    private var cancelBtnTitle: String? = ""
    private var cancelBtnListener: DialogInterface.OnClickListener? = null

    constructor()

    @SuppressLint("ValidFragment")
    constructor(
        title: String = "Oops!",
        message: String = "",
        okBtnTitle: String = "Ok",
        okClickListener: DialogInterface.OnClickListener? = null,
        cancelBtnTitle: String? = null,
        cancelBtnListener: DialogInterface.OnClickListener? = null) : this() {
        this.title = title
        this.message = message
        this.okBtnTitle = okBtnTitle
        this.okBtnListener = okClickListener
        this.cancelBtnTitle = cancelBtnTitle
        this.cancelBtnListener = cancelBtnListener

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return createDialog(this.activity!!)
    }

    fun createDialog(context: Context): Dialog {
        val builder = AlertDialog.Builder(context, R.style.AppTheme)

        return builder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(okBtnTitle, okBtnListener)
            setNegativeButton(cancelBtnTitle, cancelBtnListener)
            setCancelable(false)
        }.create()
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        if (activity != null && !activity!!.isFinishing) {
            manager?.registerFragmentLifecycleCallbacks(dialogFragmentLifecycle, true)
            super.show(manager, tag)
        }
    }

    class Lifecycle : FragmentManager.FragmentLifecycleCallbacks() {

        override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
            super.onFragmentAttached(fm, f, context)

            val tag = f?.tag
            fm?.fragments?.forEach f@{

                /**
                 * Check if tag is null or empty
                 */
                if (tag.isNullOrEmpty()) return@f

                /**
                 * Filter the tag, since there will be another fragment that will be registered but different tag name
                 */
                if (!tag.equals(it.tag, true)) return@f

                /**
                 * Remove the fragment that is added.
                 * Avoid multiple fragment since we want to show only 1 fragment at a time.
                 * If there is an existing fragment, it will be closed/dismissed.
                 */
                if (!it.toString().equals(f.toString(), true)) {
                    fm.beginTransaction().remove(it).commitAllowingStateLoss()
                }
            }
        }
    }

}



fun dialogShowManager(supportFragmentManager: FragmentManager, dialog: DialogFragment, tag: String) {
//    supportFragmentManager?.registerFragmentLifecycleCallbacks(Lifecycle, true)
    supportFragmentManager?.beginTransaction()?.add(dialog, "$tag")?.commitAllowingStateLoss()
}

