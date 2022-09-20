(ns stock-market-bot.core
  (:require [clojure.core.async :refer [<!!]]
            [clojure.string :as str]
            [environ.core :refer [env]]
            [morse.handlers :as h]
            [morse.polling :as p]
            [morse.api :as t]
            [stock-market-bot.logic.message :as logic]
            [clj-http.client :as http-client]
            [clojure.data.json :as json])
  (:gen-class))


(def token (env :telegram-token))

(h/defhandler handler

              (h/command-fn "start"
                            (fn [{{id :id :as chat} :chat}]
                              (println "Bot joined new chat: " chat)
                              (t/send-text token id "Which company are you looking for?")))

              (h/message-fn
                (fn [{{id :id} :chat :as message}]
                  (println "Intercepted message: " message)
                  (logic/company-list (:text message))
                  ())))

              (h/message-fn
                (fn [{{id :id} :chat :as message}]
                  (println "Intercepted message: " message)
                  (cond
                    (= "1" (:text message)) (t/send-text token id "You chose common stocks")
                    (= "2" message) (t/send-text token id "You chose common crypto")
                    :else (t/send-text token id "Help me to help you and choose 1 or 2, please."))))

              (h/command-fn "help"
                            (fn [{{id :id :as chat} :chat}]
                              (println "Help was requested in " chat)
                              (t/send-text token id "Help is on the way")))

              ;(h/message-fn
              ;  (fn [{{id :id} :chat :as message}]
              ;    (println "Intercepted message: " message)
              ;    (t/send-text token id "I don't do a whole lot ... yet.")))
              )

(defn -main
  [& args]
  (when (str/blank? token)
    (println "Please provide token in TELEGRAM_TOKEN environment variable!")
    (System/exit 1))

  (println "Starting the nu-tik-talk-bot")
  (<!! (p/start token handler)))