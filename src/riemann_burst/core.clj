(ns riemann-burst.core
  (:require [riemann.client :as riemann]
            [clojure.core.async :as async :refer [<! timeout chan go]]))

(defn burst! [delay events]
  (let [riemann-client (riemann/tcp-client {:host "127.0.0.1"})]
    (go
     (doseq [e events]
       (<! (timeout delay))
       (riemann/send-event riemann-client e)))))

(defn events [n template-event metrics] (map #(assoc template-event :metric %) (take n metrics)))

(defn cycle-1-10 [n] (events n {:service "debug-metric-series" :description "cycling metric 1..10"} (cycle [0 1 2 3 4 5 6 7 8 9])))




;;function calls below will be evaluated when this file is loaded (using C-c C-k in emacs/CIDER)

(burst! 100 (cycle-1-10 10)) ;;burst a 1-10 metric cycle with 10 events and a delay of 100 ms between each event

