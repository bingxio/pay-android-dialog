package cn.xyiio.pay.listener

import android.app.AlertDialog
import cn.xyiio.pay.PayDialog
import cn.xyiio.pay.PayType

interface OnPayDialogButtonClickListener {

    fun onClickPayButton (dialog: AlertDialog, type: PayType)

    fun onClickCancelButton (dialog: AlertDialog, payDialogInstance: PayDialog)

    fun onClickQueryButton (dialog: AlertDialog)

    fun onClickDestructionCancelButton (payDialog: AlertDialog)

}
