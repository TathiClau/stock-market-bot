(ns stock-market-bot.core
  (:require [clojure.core.async :refer [<!!]]
            [clojure.string :as str]
            [environ.core :refer [env]]
            [morse.handlers :as h]
            [morse.polling :as p]
            [morse.api :as t]
            [stock-market-bot.logic.cmd-logic :as logic]
            [stock-market-bot.service :as service])
  (:gen-class))


(def token (env :telegram-token))

;(h/defhandler handler
;
;              (h/command-fn "start"
;                            (fn [{{id :id :as chat} :chat}]
;                              (println "Bot joined new chat: " chat)
;                              (t/send-text token id "Hello, I am a bot to report stock data for you! Please give me a stock code. Example: AMZN")))
;
;              (h/command-fn "wallet"
;                            (fn [{{id :id :as chat} :chat}]
;                              (println "Bot joined new chat: " chat)
;                              (t/send-text token id "Add a stocks to your wallet")
;                              (h/message-fn
;                                (fn [{{id :id} :chat :as message}]
;                                  (println "Intercepted message: " message)
;                                  (t/send-text token id (service/common-stokes-url-call (:text message)))
;                                  )))
;                            )
;
;              (h/message-fn
;                (fn [{{id :id} :chat :as message}]
;                  (println "Intercepted message: " message)
;                  (t/send-text token id (service/common-stokes-url-call (:text message)))
;                  ))
;
;              (h/message-fn
;                (fn [{{id :id} :chat :as message}]
;                  (println "Intercepted message: " message)
;                  (t/send-text token id (add-stock-wallet (:text message)))
;                  ))
;              )

(defn send-tx! [id & body]
  (t/send-text token id (apply str body)))

(defn send-md! [id & body]
  (t/send-text token id {:parse_mode "Markdown"} (apply str body)))

(defn cmd-start [{{:keys [id]} :chat}]
  (send-tx! id "Hi, welcome to Stock Market Bot! This bot basically work as an assistant for you to keep an eye on the stock market and your wallet.
  \nType /help for options."))

(defn cmd! [command!
            {{:keys [id]}  :chat
             {user-id :id} :from
             text          :text}]
  (command!
    text
    (partial send-tx! id)
    (partial send-md! id)
    (str "tg:" user-id)))

(h/defhandler handler-telegram
              (h/command-fn "start"    cmd-start)
              (h/command-fn "help"     (partial cmd! (partial logic/cmd-help "/")))
              (h/command-fn "stock"    (partial cmd! logic/cmd-stock!))
              (h/command-fn "wallet-add"   (partial cmd! logic/cmd-wallet-add!)))

(defn -main
  [& args]
  (when (str/blank? token)
    (println "Please provide token in TELEGRAM_TOKEN environment variable!")
    (System/exit 1))

  (println "Starting the nu-tik-talk-bot")
  (<!! (p/start token handler-telegram)))
