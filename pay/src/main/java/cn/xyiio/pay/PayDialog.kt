package cn.xyiio.pay

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.widget.TextView
import cn.xyiio.pay.listener.OnPayDialogButtonClickListener

enum class PayType { ALIPAY, WECHAT }

class PayDialog (private val context: Context, private val money: String) {

    private lateinit var type: PayType

    private lateinit var showDialog: AlertDialog
    private lateinit var payDialog: AlertDialog

    fun show() {
        val items = arrayOf("支付宝", "微信")

        val builder = AlertDialog.Builder(context)

        builder.setSingleChoiceItems(items, -1) { _, which ->
            type = if (which == 0)
                PayType.ALIPAY
            else
                PayType.WECHAT

            showPayDialog(mContext as OnPayDialogButtonClickListener).let {
                showDialog.dismiss()
            }
        }

        showDialog = builder.create()

        showDialog.show()
    }

    @Suppress("DEPRECATION")
    @SuppressLint("InflateParams")
    private fun showPayDialog(listener: OnPayDialogButtonClickListener) {
        val builder = AlertDialog.Builder(context)

        if (type == PayType.ALIPAY)
            builder.setTitle("支付宝")
        else
            builder.setTitle("微信")

        val view = LayoutInflater.from(context).inflate(R.layout.custom_dialog, null)

        view.findViewById<TextView>(R.id.title).text =
            Html.fromHtml("<p>您需支付 <font color=\"#ff0000\">$mMoney</font> 元</p>")

        view.findViewById<TextView>(R.id.destruction).text =
            Html.fromHtml("<p>5.订单将在 <font color=\"#ff0000\">5</font> 分钟后失效</p>")

        builder.setView(view)

        builder.setPositiveButton("支付", null)
        builder.setNegativeButton("取消", null)

        builder.setNeutralButton("查询结果", null)

        payDialog = builder.create()

        payDialog.setCanceledOnTouchOutside(false)
        payDialog.show()

        payDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            listener.onClickPayButton(payDialog)
        }

        payDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
            listener.onClickCancelButton(payDialog, this)
        }

        payDialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener {
            listener.onClickQueryButton(payDialog)
        }
    }

    fun showDestructionDialog() {
        val builder = AlertDialog.Builder(context)

        builder.setMessage("如果有正在支付的订单退出后将会重新开始，是否退出？")

        builder.setPositiveButton("退出", null)
        builder.setNegativeButton("取消", null)

        val dialog = builder.create()

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            (context as OnPayDialogButtonClickListener).onClickDestructionCancelButton(payDialog).let {
                dialog.dismiss()
            }
        }
    }
}
