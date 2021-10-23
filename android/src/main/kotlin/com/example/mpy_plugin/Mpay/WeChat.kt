package com.example.mpy_plugin.Mpay

import com.example.mpy_plugin.PaymentAdapter
import com.macau.pay.sdk.base.PayResult
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * @ClassName WeChat
 * @Description TODO
 * @date 2021/10/23 0:38
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Version 1.0
 * @Author 李建新
 */
class WeChat (val mPay: MPay): PaymentAdapter<PayResult> {

    override val method: String = "wechat";

    override fun pay(params: MethodCall, result: MethodChannel.Result) {

    }

    override fun  handleResult(result: PayResult) {
        mPay.handleResult(result);
    }


}