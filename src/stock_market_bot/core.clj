(ns stock-market-bot.core
  (:require [clojure.core.async :refer [<!!]]
            [clojure.string :as str]
            [environ.core :refer [env]]
            [morse.handlers :as h]
            [morse.polling :as p]
            [morse.api :as t]
            [stock-market-bot.logic.message :as logic]
            [yahoo-finance-api.core :as f])
  (:gen-class))

(def portifolio (atom #{}))
(def token (env :telegram-token))



(h/defhandler handler

              (h/command-fn "start"
                            (fn [{{id :id :as chat} :chat}]
                              (println "Bot joined new chat: " chat)
                              (t/send-text token id "Hello, I am a bot to report stock data for you! Please give me a stocks ticker. Example: AMZN")))

              (h/message-fn
                (fn [{{id :id} :chat :as message}]
                  (println "Intercepted message: " message)
                  (let [stock-info (f/get-stock message)]
                    stock-info)))

              (h/message-fn
                (fn [{{id :id} :chat :as message}]
                  (println "Intercepted message: " message)
                  (let [company-code (->> (logic/filter-input (keyword (:text message)))
                                          first
                                          val
                                          println
                                          (t/send-text token id (fn [x] (str ))))]
                    company-code)))

              )

(defn -main
  [& args]
  (when (str/blank? token)
    (println "Please provide token in TELEGRAM_TOKEN environment variable!")
    (System/exit 1))

  (println "Starting the nu-tik-talk-bot")
  (<!! (p/start token handler)))
