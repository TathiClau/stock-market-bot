(ns stock-market-bot.service
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            ))

(defn call-yahoo-finance
  [code]
  (client/post "https://yahoo-finance97.p.rapidapi.com/stock-info" {:headers     {:X-RapidAPI-Key  "e9699c6a20mshbe24f39168d7058p1f0afejsne7e0887311d5"
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

(defn filter-items-price
  [coll]
  (into (sorted-map) (filter (comp #{:currentPrice
                                     :currency
                                     :symbol} key) coll)))

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

(defn format-response-price
  [my-seq]
  (str (get my-seq :symbol) " " (get my-seq :currency) " " (get my-seq :currentPrice)))

(defn common-stokes-url-call
  [code]
  (let [raw-response (:body (call-yahoo-finance code))
        response (->> raw-response
                      json/read-json
                      :data)
        items (filter-items response)
        items-map (format-response items)]
        items-map))

(defn common-stokes-url-call-price
  [code]
  (let [raw-response (:body (call-yahoo-finance code))
        response (->> raw-response
                      json/read-json
                      :data)
        items (filter-items-price response)
        items-map (format-response-price items)]
    items-map))



