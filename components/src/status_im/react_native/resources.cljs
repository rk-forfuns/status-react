(ns status-im.react-native.resources)

(def ui
  {:empty-chats-header (js/require "./resources/images/ui/empty-chats-header.png")
   :starter-pack       (js/require "./resources/images/ui/starter-pack.png")
   :welcome            (js/require "./resources/images/ui/welcome.jpg")
   :intro1             (js/require "./resources/images/ui/intro1.jpg")
   :intro2             (js/require "./resources/images/ui/intro2.jpg")
   :intro3             (js/require "./resources/images/ui/intro3.jpg")
   :sample-key         (js/require "./resources/images/ui/sample-key.jpg")
   :lock               (js/require "./resources/images/ui/lock.png")
   :tribute-to-talk    (js/require "./resources/images/ui/tribute-to-talk.png")
   :hardwallet-card    (js/require "./resources/images/ui/hardwallet-card.png")
   :keycard-lock       (js/require "./resources/images/ui/keycard-lock.png")
   :keycard            (js/require "./resources/images/ui/keycard.png")
   :keycard-logo       (js/require "./resources/images/ui/keycard-logo.png")
   :keycard-logo-blue  (js/require "./resources/images/ui/keycard-logo-blue.png")
   :keycard-logo-gray  (js/require "./resources/images/ui/keycard-logo-gray.png")
   :keycard-key        (js/require "./resources/images/ui/keycard-key.png")
   :keycard-empty      (js/require "./resources/images/ui/keycard-empty.png")
   :keycard-phone      (js/require "./resources/images/ui/keycard-phone.png")
   :keycard-connection (js/require "./resources/images/ui/keycard-connection.png")
   :keycard-wrong      (js/require "./resources/images/ui/keycard-wrong.png")
   :not-keycard        (js/require "./resources/images/ui/not-keycard.png")
   :status-logo        (js/require "./resources/images/ui/status-logo.png")
   :warning-sign       (js/require "./resources/images/ui/warning-sign.png")
   :phone-nfc-on       (js/require "./resources/images/ui/phone-nfc-on.png")
   :phone-nfc-off      (js/require "./resources/images/ui/phone-nfc-off.png")
   :dapp-store         (js/require "./resources/images/ui/dapp-store.png")
   :ens-header         (js/require "./resources/images/ui/ens-header.png")
   :new-chat-header    (js/require "./resources/images/ui/new-chat-header.png")
   :onboarding-phone   (js/require "./resources/images/ui/onboarding-phone.png")})

(def loaded-images (atom {}))

(defn get-image [k]
  (if (contains? @loaded-images k)
    (get @loaded-images k)
    (get (swap! loaded-images assoc k
                (get ui k)) k)))
