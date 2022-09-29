(ns stock-market-bot.service
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            ))

(defn call-yahoo-finance
  [code]
  (client/post "https://yahoo-finance97.p.rapidapi.com/stock-info" {:headers     {:X-RapidAPI-Key  ""
                                                                                  :X-RapidAPI-Host "yahoo-finance97.p.rapidapi.com"}
                                                                    :form-params {:symbol code}}))

(defn filter-items
  [coll]
  (into (sorted-map) (filter (comp #{:longName
                   :currency
                   :currentPrice
                   :bid
                   :bidSize
                   :dayHigh
                   :dayLow} key) coll)))

(defn format-response
  [my-seq]
  (str "Name: " (get my-seq :longName)
       "\nCurrency: " (get my-seq :currency)
       "\nCurrent Price: " (get my-seq :currentPrice)
       "\nBid: " (get my-seq :bid)
       "\nBid Size: " (get my-seq :bidSize)
       "\nDay High: " (get my-seq :dayHigh)
       "\nDay Low: " (get my-seq :dayLow)
       ))

(defn common-stokes-url-call
  [code]
  (println code)
  (let [raw-response (:body (call-yahoo-finance code))
        response (->> raw-response
                      json/read-json
                      :data)
        items (filter-items response)
        items-map (format-response items)]
        items-map))


