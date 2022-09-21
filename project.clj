(defproject stock-market-bot "0.1.0-SNAPSHOT"

  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :main ^:skip-aot stock-market-bot.core
  :target-path "target/%s"

  :profiles {:uberjar {:aot :all}}

  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [yahoo-finance-api "0.1.5"]
                 [environ             "1.1.0"]
                 [morse               "0.2.4"]
                 [org.clojure/core.async "0.4.500"]
                 [clj-http "3.9.1"]
                 [org.clojure/data.json "2.4.0"]]

  :repl-options {:init-ns stock-market-bot.core})
