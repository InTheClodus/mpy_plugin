
import 'dart:async';

import 'package:flutter/services.dart';
class ConfigType{
  static String mPay = "config.mpay";
  static String vm = "config.vm";
  static String upop = "config.upop";
  static String aliPay = "config.alipay";
}
class MpyPlugin {
  static const MethodChannel _flutterPay = const MethodChannel('mpy_plugin');

  static Future<String?> get platformVersion async {
    final String? version = await _flutterPay.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<dynamic> pay({required Map<String,dynamic> params})async{
    return await _flutterPay.invokeMapMethod("pay",params);
  }

  /*
   * 初始化
   */
  static Future<void> init(String type,String methods) async {
    final Map<String, dynamic> params = <String, dynamic>{
      "methods": methods,
    };
    await _flutterPay.invokeListMethod(type, params);
  }
}
