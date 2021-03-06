(ns status-im.ui.screens.wallet.accounts.views
  (:require-macros [status-im.utils.views :as views])
  (:require [status-im.ui.components.react :as react]
            [status-im.ui.components.icons.vector-icons :as icons]
            [status-im.ui.components.toolbar.styles :as toolbar.styles]
            [status-im.ui.components.colors :as colors]
            [status-im.i18n :as i18n]
            [status-im.ui.components.list.views :as list]
            [status-im.ui.components.chat-icon.screen :as chat-icon]
            [status-im.ui.components.list-item.views :as list-item]
            [status-im.wallet.utils :as wallet.utils]
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [status-im.ui.screens.wallet.accounts.sheets :as sheets]
            [status-im.ui.screens.wallet.accounts.styles :as styles]
            [status-im.utils.utils :as utils.utils]))

(def state (reagent/atom {:tab :assets}))

(views/defview account-card [{:keys [name color address type] :as account}]
  (views/letsubs [currency        [:wallet/currency]
                  portfolio-value [:account-portfolio-value address]]
    [react/touchable-highlight
     {:on-press      #(re-frame/dispatch [:navigate-to :wallet-account account])
      :on-long-press #(re-frame/dispatch [:bottom-sheet/show-sheet
                                          {:content        (fn [] [sheets/send-receive account type])
                                           :content-height 130}])}
     [react/view {:style (styles/card color)}
      [react/view {:flex-direction :row :align-items :center :justify-content :space-between}
       [react/nested-text {:style {:color colors/white-transparent-persist
                                   :font-weight "500" :flex-shrink 1}}
        [{:style {:color colors/white-persist}} portfolio-value]
        " "
        (:code currency)]
       [react/touchable-highlight
        {:on-press #(re-frame/dispatch [:show-popover
                                        {:view :share-account :address address}])}
        [icons/icon :main-icons/share {:color colors/white-persist}]]]
      [react/view
       [react/text {:style {:color colors/white-persist :font-weight "500" :line-height 22}} name]
       [react/text {:number-of-lines 1 :ellipsize-mode :middle
                    :style {:line-height 22 :font-size 13
                            :font-family "monospace"
                            :color colors/white-transparent-70-persist}}
        address]]]]))

(defn add-card []
  [react/touchable-highlight {:on-press #(re-frame/dispatch [:bottom-sheet/show-sheet
                                                             {:content        sheets/add-account
                                                              :content-height 260}])}
   [react/view {:style (styles/add-card)}
    [react/view {:width       40 :height 40 :justify-content :center :border-radius 20
                 :align-items :center :background-color colors/blue-transparent-10 :margin-bottom 8}
     [icons/icon :main-icons/add {:color colors/blue}]]
    [react/text {:style {:color colors/blue}} (i18n/label :t/add-account)]]])

(defn tab-title [state key label active?]
  [react/view {:align-items :center}
   [react/touchable-highlight {:on-press #(swap! state assoc :tab key)
                               :underlay-color colors/gray-lighter
                               :style {:border-radius 8}}
    [react/view {:padding-horizontal 12 :padding-vertical 9}
     [react/text {:style {:font-weight "500" :color (if active? colors/black colors/gray) :line-height 22}}
      label]]]
   (when active?
     [react/view {:width 24 :height 3 :border-radius 4 :background-color colors/blue}])])

(defn render-asset [currency & [on-press]]
  (fn [{:keys [icon decimals amount color value] :as token}]
    [list-item/list-item
     (cond-> {:title-prefix         (wallet.utils/format-amount amount decimals)
              :title                (wallet.utils/display-symbol token)
              :title-color-override colors/gray
              :accessibility-label (str (:symbol token)  "-asset-value")
              :subtitle             (str (if value value 0) " " currency)
              :icon                 (if icon
                                      [list/item-image icon]
                                      [chat-icon/custom-icon-view-list (:name token) color])}
       on-press
       (assoc :on-press #(on-press token)))]))

(views/defview assets []
  (views/letsubs [{:keys [tokens nfts]} [:wallet/all-visible-assets-with-values]
                  currency [:wallet/currency]]
    [list/flat-list {:data               tokens
                     :default-separator? false
                     :key-fn             :name
                     :render-fn          (render-asset (:code currency))}]))

(views/defview total-value []
  (views/letsubs [currency        [:wallet/currency]
                  portfolio-value [:portfolio-value]]
    [react/view {:style {:padding-horizontal 16}}
     [react/nested-text {:style {:font-size 32 :color colors/gray :font-weight "600"}}
      [{:style {:color colors/black}} portfolio-value]
      " "
      (:code currency)]
     [react/text {:style {:color colors/gray}} (i18n/label :t/wallet-total-value)]]))

(defn- request-camera-permissions []
  (let [options {:handler :wallet.send/qr-scanner-result}]
    (re-frame/dispatch
     [:request-permissions
      {:permissions [:camera]
       :on-allowed
       #(re-frame/dispatch [:wallet.send/qr-scanner-allowed options])
       :on-denied
       #(utils.utils/set-timeout
         (fn []
           (utils.utils/show-popup (i18n/label :t/error)
                                   (i18n/label :t/camera-access-error)))
         50)}])))

(views/defview accounts-options []
  (views/letsubs [{:keys [mnemonic]} [:multiaccount]
                  empty-balances?           [:empty-balances?]]
    [react/view {:flex-direction :row :align-items :center}
     [react/view {:flex 1 :padding-left 16}
      (when (and mnemonic
                 (not empty-balances?))
        [react/touchable-highlight
         {:on-press #(re-frame/dispatch [:navigate-to :backup-seed])}
         [react/view {:flex-direction :row :align-items :center}
          [react/view {:width           14 :height 14 :background-color colors/gray :border-radius 7 :align-items :center
                       :justify-content :center :margin-right 9}
           [react/text {:style {:color       colors/white
                                :font-size   13
                                :font-weight "700"}}
            "!"]]
          [react/text {:style               {:color colors/gray}
                       :accessibility-label :back-up-your-seed-phrase-warning}
           (i18n/label :t/back-up-your-seed-phrase)]]])]
     [react/touchable-highlight
      {:on-press #(request-camera-permissions)}
      [react/view {:height          toolbar.styles/toolbar-height
                   :width 24 :align-items :center
                   :justify-content :center}
       [icons/icon :main-icons/qr {:accessibility-label :accounts-qr-code}]]]
     [react/touchable-highlight
      {:on-press #(re-frame/dispatch [:bottom-sheet/show-sheet
                                      {:content        (sheets/accounts-options mnemonic)
                                       :content-height (if mnemonic 250 190)}])}
      [react/view {:height          toolbar.styles/toolbar-height
                   :width toolbar.styles/toolbar-height :align-items :center
                   :justify-content :center}
       [icons/icon :main-icons/more {:accessibility-label :accounts-more-options}]]]]))

(views/defview send-button []
  (views/letsubs [account [:multiaccount/default-account]]
    [react/view styles/send-button-container
     [react/touchable-highlight
      {:accessibility-label :send-transaction-button
       :on-press            #(re-frame/dispatch [:wallet/prepare-transaction-from-wallet account])}
      [react/view (styles/send-button)
       [icons/icon :main-icons/send {:color colors/white-persist}]]]]))

(views/defview accounts []
  (views/letsubs [accounts [:multiaccount/accounts]]
    [react/scroll-view {:horizontal                        true
                        :shows-horizontal-scroll-indicator false}
     [react/view {:flex-direction     :row
                  :padding-horizontal 8}
      (for [account accounts]
        ^{:key account}
        [account-card account])
      [add-card]]]))

(defn accounts-overview []
  [react/view {:flex 1}
   [react/scroll-view
    [accounts-options]
    [react/view {:margin-top 8}
     [total-value]
     [accounts]]
    [assets]
    [react/view {:height 68}]]
   [send-button]])
