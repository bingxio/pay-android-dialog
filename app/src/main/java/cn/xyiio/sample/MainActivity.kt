package cn.xyiio.sample

import android.app.AlertDialog
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import cn.xyiio.pay.PayDialog
import cn.xyiio.pay.listener.OnPayDialogButtonClickListener

class MainActivity : AppCompatActivity(), OnPayDialogButtonClickListener {

    private var hasOrder = false

    override fun onClickDestructionCancelButton(payDialog: AlertDialog) = payDialog.dismiss().let { hasOrder = false }

    override fun onClickPayButton(dialog: AlertDialog) =
        Toast.makeText(this, "开始支付", Toast.LENGTH_SHORT).show().let {
            hasOrder = true
        }

    override fun onClickCancelButton(dialog: AlertDialog, payDialogInstance: PayDialog) = if (hasOrder) {
        payDialogInstance.showDestructionDialog()
    } else {
        dialog.dismiss()
    }

    override fun onClickQueryButton(dialog: AlertDialog) {
        if (!hasOrder) {
            Toast.makeText(this, "您当前未提交订单", Toast.LENGTH_SHORT).show()

            return
        }

        val progressDialog = ProgressDialog.show(this, null, "查询中", true, false)

        Handler().postDelayed({
            progressDialog.dismiss().let { Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show() }.let {
                dialog.dismiss()
            }
        }, 2000)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv).setOnClickListener {
            PayDialog(this, "10.00").show()
        }
    }
}
