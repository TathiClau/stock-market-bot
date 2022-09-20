(ns stock-market-bot.service
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            [stock-market-bot.fixture :as fixture]
            ))


(defn call-yahoo-finance
  [code]
  (client/post "https://yahoo-finance97.p.rapidapi.com/stock-info" {:headers     {:X-RapidAPI-Key  "e9699c6a20mshbe24f39168d7058p1f0afejsne7e0887311d5"
                                                                                  :X-RapidAPI-Host "yahoo-finance97.p.rapidapi.com"}
                                                                    :form-params {:symbol code}}))

(defn print-response
  [api-response]
  (println api-response))

(defn common-stokes-url-call
  [code]
  (let [raw-response (:body (call-yahoo-finance code))
        response (->> raw-response
                      json/read-json
                      :data)
        stock-name (-> response
                        :shortName)
        ]
    response)
  )

;(println (common-stokes-url-call "AAPL"))
(print-response fixture/api-response)
