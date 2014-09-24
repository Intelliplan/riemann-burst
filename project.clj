(defproject riemann-burst "0.1.0-SNAPSHOT"
  :description "Tools to support Riemann testing by bursting events."
  :url "https://github.com/Intelliplan/riemann-burst"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [riemann-clojure-client "0.2.11"]
                 [org.clojure/core.async "0.1.338.0-5c5012-alpha"]
                 [org.slf4j/slf4j-log4j12 "1.6.4"]]
  :repl-options {:port 6667})
