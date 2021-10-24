import 'dart:collection';

import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:mpy_plugin/mpy_plugin.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  @override
  void initState() {
    super.initState();
    MpyPlugin.init(ConfigType.mPay, "alipay,wechat,mpay");
  }
  dynamic result;
  Future<void> pays() async {
    Map<String, dynamic> params = <String, dynamic>{
      "org_id": "888538299146558",
      "sign_type": "MD5",
      "sign": "OGY6YKPHRXEGVHTYESNXS68FOK2LXHLE",
      "pay_channel": "mpay",
      "total_fee": "0.10",
      "body": "我的商品1",
      "sub_appid": "wxf99765ac6d0c6567",
      "subject": "專業髮廊",
      "extend_params": {
        "sub_merchant_name": "儒林教育",
        "sub_merchant_id": "888538299146558",
        "sub_merchant_industry": "8299",
      },
    };
    Options options = Options(headers: {
      "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36",
      "Content-Type": "application/json",
      "accept": "*/*",
      "connection": "Keep-Alive"
    });
    Response rep = await Dio().post(
        "https://uatqr.macaupay.com.mo/mpay/merchantSign.do",
        data: params,
        options: options);
    print(rep.data);
    HashMap<String, dynamic> paramss = HashMap.from(rep.data);
    paramss.putIfAbsent("method", () => "mpay");
    result = await MpyPlugin.pay(params: paramss);
    print("====$rep");
  }
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child:Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("$result"),
              TextButton(
                onPressed: pays,
                child: Text("测试"),
              )
            ],
          ),
        ),
      ),
    );
  }
}
