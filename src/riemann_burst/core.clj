(ns riemann-burst.core
  (:require [riemann.client :as riemann]
            [clojure.core.async :as async :refer [<! timeout chan go]]))

(defn burst! [delay events]
  (let [riemann-client (riemann/tcp-client {:host "127.0.0.1"})]
    (go
     (doseq [e events]
       (<! (timeout delay))
       (riemann/send-event riemann-client e)))))

(defn events [template-event metrics] (map #(assoc template-event :metric %) metrics))

(def cycle-1-10
  (events {:service "debug-metric-series" :description "cycling metric 1..10"}
          (cycle [0 1 2 3 4 5 6 7 8 9])))

(def spike-every-100
  (events {:service "debug-metric-series" :description "spike every 100"}
          (cycle (conj (take 99 (cycle [0 1 2 3])) 9))))

(def strange-message-every-100
  (cycle (conj (repeat 99 {:service "debug-metric-series" :description "just another message"})
               {:service "debug-metric-series" :description "wohoo, this is strange!"})))


;;function calls below will be evaluated when this file is loaded (using C-c C-k in emacs/CIDER)

(burst! 100 (take 20 cycle-1-10))

#_(burst! 10 (take 200 spike-every-100))

#_(burst! 10 (take 101 strange-message-every-100))

