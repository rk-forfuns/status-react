(ns status-im.hardwallet.simulated-keycard
  (:require [status-im.hardwallet.keycard :as keycard]))

(defn check-nfc-support [{:keys [on-success]}])
(defn check-nfc-enabled [{:keys [on-success]}])
(defn open-nfc-settings [])
(defn register-card-events [args])
(defn on-card-connected [callback])
(defn on-card-disconnected [callback])
(defn remove-event-listener [event])
(defn remove-event-listeners [])
(defn get-application-info [args])
(defn install-applet [args])
(defn init-card [args])
(defn install-applet-and-init-card [args])
(defn pair [args])
(defn generate-mnemonic [args])
(defn generate-and-load-key [args])
(defn unblock-pin [args])
(defn verify-pin [args])
(defn change-pin [args])
(defn unpair [args])
(defn delete [args])
(defn remove-key [args])
(defn remove-key-with-unpair [args])
(defn export-key [args])
(defn unpair-and-delete [args])
(defn get-keys [args])
(defn sign [args])

(defrecord SimulatedKeycard []
  keycard/Keycard
  (keycard/check-nfc-support [this args]
    (check-nfc-support args))
  (keycard/check-nfc-enabled [this args]
    (check-nfc-enabled args))
  (keycard/open-nfc-settings [this]
    (open-nfc-settings))
  (keycard/register-card-events [this args]
    (register-card-events args))
  (keycard/on-card-connected [this callback]
    (on-card-connected callback))
  (keycard/on-card-disconnected [this callback]
    (on-card-disconnected callback))
  (keycard/remove-event-listener [this event]
    (remove-event-listener event))
  (keycard/remove-event-listeners [this]
    (remove-event-listeners))
  (keycard/get-application-info [this args]
    (get-application-info args))
  (keycard/install-applet [this args]
    (install-applet args))
  (keycard/init-card [this args]
    (init-card args))
  (keycard/install-applet-and-init-card [this args]
    (install-applet-and-init-card args))
  (keycard/pair [this args]
    (pair args))
  (keycard/generate-mnemonic [this args]
    (generate-mnemonic args))
  (keycard/generate-and-load-key [this args]
    (generate-and-load-key args))
  (keycard/unblock-pin [this args]
    (unblock-pin args))
  (keycard/verify-pin [this args]
    (verify-pin args))
  (keycard/change-pin [this args]
    (change-pin args))
  (keycard/unpair [this args]
    (unpair args))
  (keycard/delete [this args]
    (delete args))
  (keycard/remove-key [this args]
    (remove-key args))
  (keycard/remove-key-with-unpair [this args]
    (remove-key-with-unpair args))
  (keycard/export-key [this args]
    (export-key args))
  (keycard/unpair-and-delete [this args]
    (unpair-and-delete args))
  (keycard/get-keys [this args]
    (get-keys args))
  (keycard/sign [this args]
    (sign args)))
