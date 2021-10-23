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
      "org_id": "替换你的id", // 订单ID
      "sign_type": "MD5", // 加密方式
      "sign": "替换你的密钥", /// 密钥
      "pay_channel": "mpay", /// 支付方式
      "total_fee": "0.10", // 金额
      "body": "我的商品1", // 商品名
      "sub_appid": "替换你的appid", // 微信支付appid
      "subject": "替换店铺",// 店铺
      "extend_params": {
        "sub_merchant_name": "儒林教育", // app Name
        "sub_merchant_id": "替换你的编号", //z子商户编号
        "sub_merchant_industry": "替换你的行业", //子商户行业
      },
    };
    Options options = Options(headers: {
      "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.198 Safari/537.36",
      "Content-Type": "application/json",
      "accept": "*/*",
      "connection": "Keep-Alive"
    });
    Response rep = await Dio().post(
        "替换你的后台api接口",
        data: params,
        options: options);
    print(rep.data);
    HashMap<String, dynamic> paramss = HashMap.from(rep.data);
    paramss.putIfAbsent("method", () => "mpay");
    setState(() async{
      result = await MpyPlugin.pay(params: paramss);
      print("====$result");
      print("----------");
    });

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