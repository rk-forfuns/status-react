(ns status-im.ui.screens.group.subs
  (:require [re-frame.core :as re-frame]
            [status-im.group-chats.db :as group-chats.db]
            [status-im.chat.models :as chat.models]))

(re-frame/reg-sub
 ::chat-joined?
 :<- [:multiaccount/public-key]
 :<- [:chats/active-chats]
 (fn [[my-public-key chats] [_ chat-id]]
   (let [current-chat (get chats chat-id)]
     (and (chat.models/group-chat? current-chat)
          (group-chats.db/joined? my-public-key current-chat)))))
