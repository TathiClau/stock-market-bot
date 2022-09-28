(ns stock-market-bot.logic.cmd-logic)

(defn cmd-help [prefix text send-tx! _ _]
  (send-tx!
    "How to use this bot:"
    (apply str
           (map #(str "\n" prefix %)
                ["stock stock code (ex. AMZN)\n Here you can have real time details about a specific stock"
                 "wallet stock code (ex. AMZN)\n Here you can add a stock to your wallet"]))))
