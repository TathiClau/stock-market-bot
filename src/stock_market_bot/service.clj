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
       ;"\nCurrency: " (get (nth my-seq 2) 1)
       ;"\nCurrent Price: " (get (nth my-seq 5) 1)
       ;"\nBid: " (get (nth my-seq 4) 1)
       ;"\nBid: " (get (nth my-seq 5) 1)
       ;"\nBid Size: " (get (nth my-seq 6) 1)
       ;"\nDay Size: " (get (nth my-seq 7) 1)
       ;"\nDay Low: " (get (nth my-seq 8) 1)
       ))

(defn common-stokes-url-call
  [code]
  (let [raw-response (:body (call-yahoo-finance code))
        response (->> raw-response
                      json/read-json
                      :data)
        items (filter-items response)
        items-map (format-response items)]
        items-map))

(println (common-stokes-url-call "AMZN"))
;(def oi
;  #{[:bidSize 1000] [:dayHigh 116.05] [:dayLow 112.06] [:currentPrice 113.78] [:currency "USD"] [:longName " Amazon.com, Inc."] [:bid 0]})
;
;(println (select-keys oi [:dayLow]))
;(println (sort (comp #{:longName
;                       :currency
;                       :currentPrice
;                       :bid
;                       :bidSize
;                       :dayHigh
;                       :dayLow} oi) oi))


;(println (str "Name: " (:a oi)
;              "\nCurrency: "))
