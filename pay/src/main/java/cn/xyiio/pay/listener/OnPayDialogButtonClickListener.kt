package cn.xyiio.pay.listener

import android.app.AlertDialog
import cn.xyiio.pay.PayDialog

interface OnPayDialogButtonClickListener {

    fun onClickPayButton (dialog: AlertDialog)

    fun onClickCancelButton (dialog: AlertDialog, payDialogInstance: PayDialog)

    fun onClickQueryButton (dialog: AlertDialog)

    fun onClickDestructionCancelButton (payDialog: AlertDialog)

}