package com.example.mpy_plugin

/**
 * @ClassName WeChatOrderResult
 * @Description TODO
 * @date 2021/10/23 0:32
 *      _              _           _     _   ____  _             _ _
 *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
 *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
 *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
 *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
 * @Version 1.0
 * @Author 李建新
 */

data class WeChatOrderResult(
    val Data: Data,
    val respCode: String,
    val respMsg: String,
    val sign: String,
    val sign_type: String
)

data class Data(
    val out_trade_no: String,
    val qr_code: String,
    val trade_no: String
)