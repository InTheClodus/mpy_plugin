package com.example.mpy_plugin

import android.app.Activity
import android.content.Context
import androidx.annotation.NonNull
import com.example.mpy_plugin.Mpay.MPay

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry

/** MpyPlugin */
class MpyPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {

  private lateinit var channel : MethodChannel
  private val channels = HashMap<String, PaymentChannel>()
  private var mActivity: Activity? = null

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "mpy_plugin")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    when (call.method) {
      "config.mpay" -> {
        channels["pay"] = MPay(call.arguments(), mActivity!!)
      }
      else -> {
        val payType = call.argument<String>("method")
        if (channels.containsKey(call.method) && payType != null) {
          channels[call.method]!!.pay(payType, call, result)
          return
        }
        result.notImplemented()
      }
    }
  }

  /// 旧版本 注册方式
  companion object {
    private var registrar: PluginRegistry.Registrar? = null
    private var mActivity: Activity? = null
    var context: Context? = null
    private var mMethodChannel: MethodChannel? = null

    @JvmStatic
    fun registerWith(registrar: PluginRegistry.Registrar) {
      val channel = MethodChannel(registrar.messenger(), "flutter_pay_plugins")
      channel.setMethodCallHandler(MpyPlugin())
      context = registrar.context()
      MpyPlugin.registrar = registrar
      mActivity = registrar.activity()
      channel.setMethodCallHandler(MpyPlugin())
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  /// 绑定 activty
  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    mActivity = binding.activity
  }

  override fun onDetachedFromActivityForConfigChanges() {
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
  }

  override fun onDetachedFromActivity() {
  }
}
