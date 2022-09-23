(ns stock-market-bot.service
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]
            ))

(def api-field-mappings
  {
   :name {:api-param "n" :type "text"}
   :last-price {:api-param "l1" :type "numeric"}
   :ask {:api-param "a" :type "numeric"}
   :bid {:api-param "b" :type "numeric"}
   :prev-close {:api-param "c" :type "mixed"}
   :open {:api-param "o" :type "numeric"}
   :change {:api-param "c1" :type "mixed"}
   :change-and-percent {:api-param "c" :type "mixed"}
   :change-percent {:api-param "p2" :type "mixed"}
   :last-trade-date {:api-param "d1" :type "date"}
   :last-trade-time {:api-param "t1" :type "time"}
   }
  )




(defn call-yahoo-finance
  [code]
  (client/post "https://yahoo-finance97.p.rapidapi.com/stock-info" {:headers     {:X-RapidAPI-Key  ""
                                                                                  :X-RapidAPI-Host "yahoo-finance97.p.rapidapi.com"}
                                                                    :form-params {:symbol code}}))

(defn filter-items
  [coll]
  (filter (comp #{:longName
                  :currency
                  :currentPrice
                  :bid
                  :bidSize
                  :dayHigh
                  :dayLow} key) coll))

(defn format-response
  [my-seq]

  (str "Name:")


  )


(defn common-stokes-url-call
  [code]
  (let [raw-response (:body (call-yahoo-finance code))
        response (->> raw-response
                      json/read-json
                      :data)
        items (filter-items response)]
        items))


;(println (keys (common-stokes-url-call "AMZN")))
(def oi {:a 1})

(println (str "Name: " (:a oi)
              "\nCurrency: "))
