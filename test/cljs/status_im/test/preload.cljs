(ns status-im.test.preload)

(.mock js/jest "react-native"
       (fn [] #js {"StyleSheet" #js {"create" (.fn js/jest)}
                   "NativeModules" #js {"RNGestureHandlerModule" #js {"Direction" (.fn js/jest)}
                                        "ReanimatedModule" #js {"configureProps" (.fn js/jest)}}
                   "requireNativeComponent" (fn [] #js {"propTypes" ""})
                   "Animated" #js {"createAnimatedComponent" (.fn js/jest)}
                   "Easing" #js {"bezier" (.fn js/jest)
                                 "poly" (.fn js/jest)
                                 "out" (.fn js/jest)
                                 "in" (.fn js/jest)
                                 "inOut" (.fn js/jest)}
                   "Dimensions" #js {"get" (fn [] #js {"height" "" "width" ""})}
                   "Platform" #js {"select" (.fn js/jest)}
                   "I18nManager" #js {"isRTL" ""}
                   "NativeEventEmitter" (.fn js/jest)}))
