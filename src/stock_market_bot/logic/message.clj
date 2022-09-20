(ns stock-market-bot.logic.message)

(def company-list
  {:apple     "AAPL"
   :nubank    "NUBR33"
   :petrobras "PETR4"
   })

(defn filter-input
  [user-str-input]
  (filter (fn
             [x]
             (= (key x) user-str-input))
           company-list))

;(println (filter-input apple))


(->> (filter-input :apple)
    first
    val
    (println "esse é o valor "))