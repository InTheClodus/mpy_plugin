package com.example.mpy_plugin

import com.macau.pay.sdk.base.PayResult
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**
 * @ClassName PaymentChannel
 * @Description TODO
 * @date 2021/10/23 0:31
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Version 1.0
 * @Author 李建新
 */
interface PaymentChannel {

    val channel: String;

    fun methods(): HashMap<String, PaymentAdapter<PayResult>>;

    fun pay(method: String, params: MethodCall, result: MethodChannel.Result) {
        val adapter = methods()[method];

        if (adapter == null) {
            result.error("500", "适配器是空的", params);
        } else {
            adapter.pay(params, result);
        }

    }
}