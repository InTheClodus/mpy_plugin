#import "MpyPlugin.h"
#if __has_include(<mpy_plugin/mpy_plugin-Swift.h>)
#import <mpy_plugin/mpy_plugin-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "mpy_plugin-Swift.h"
#endif

@implementation MpyPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftMpyPlugin registerWithRegistrar:registrar];
}
@end
