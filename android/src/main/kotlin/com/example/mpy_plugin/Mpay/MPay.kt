package com.example.mpy_plugin.Mpay

import android.app.Activity
import android.util.Log
import com.example.mpy_plugin.PaymentAdapter
import com.example.mpy_plugin.PaymentChannel
import com.macau.pay.sdk.OpenSdk
import com.macau.pay.sdk.base.PayResult
import com.macau.pay.sdk.interfaces.OpenSdkInterfaces
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * @ClassName MPay
 * @Description TODO
 * @date 2021/10/23 0:34
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Version 1.0
 * @Author 李建新
 */
class MPay(params: Map<String, String>, val mActivity: Activity): PaymentChannel,
    OpenSdkInterfaces {

    val _methods  = params.getOrElse("methods"){ "" }.split(",").fold(HashMap<String, PaymentAdapter<PayResult>>(), { result, code ->
        when (code) {
            "alipay" -> {
                result[code] = Alipay(this)
                result
            }
            "wechat" -> {
                result[code] = WeChat(this)
                result
            }
            "mpay" -> {
                result[code] = MPayModel(this)
                result
            }
            else -> result
        }
    })

    override val channel = "map"

    var result : MethodChannel.Result? = null

    override fun pay(method: String, params: MethodCall, result: MethodChannel.Result) {
        this.result = result
        val adapter = methods()[method]
        if (adapter == null) {
            result.error("error", "适配器未初始化", params)
            return
        }
        val data = params.argument<String>("signData");

        if (data is String) {
            OpenSdk.newPayAll(mActivity, data, this)
            return
        }
        result.error("error", "error", "error")
    }

    fun handleResult(result : PayResult?) {
        val map = HashMap<String, String>();
        map["message"] = result!!.result;
        if (result.resultStatus == "9000") {
            map["result"] = "Y"
        }else{
            map["result"] = "N"
        }
        this.result?.success(map);
    }

    override fun methods(): HashMap<String, PaymentAdapter<PayResult>> {
        if (_methods.isEmpty()) return HashMap()
        return _methods
    }

    override fun OpenSDKInterfaces(p0: PayResult?) {

    }

    override fun AliPayInterfaces(p0: PayResult?) {
        _methods["alipay"]?.handleResult(p0!!)
    }

    override fun MPayInterfaces(p0: PayResult?) {
        _methods["mpay"]?.handleResult(p0!!)
    }

    override fun WeChatPayInterfaces(p0: PayResult?) {
        _methods["wxpay"]?.handleResult(p0!!)
    }
}