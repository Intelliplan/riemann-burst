(ns riemann-burst.core
  (:require [riemann.client :as riemann]
            [clojure.core.async :as async :refer [<! timeout chan go]]))

(defn burst! [delay events]
  (let [riemann-client (riemann/tcp-client {:host "127.0.0.1"})]
    (go
     (doseq [e events]
       (<! (timeout delay))
       (riemann/send-event riemann-client e)))))

(defn apply-metric-series [template-event metrics] (map #(assoc template-event :metric %) metrics))

(def cycle-1-10
  (apply-metric-series {:service "test-cycle-1-10" :ttl 5 :description "cycling metric 1..10"}
          (cycle [0 1 2 3 4 5 6 7 8 9])))

(def spike-every-100
  (apply-metric-series {:service "test-spike-every-100" :ttl 5 :description "spike every 100"}
          (cycle (conj (take 99 (cycle [0 1 2 3])) 9))))

(def strange-message-every-100
  (cycle (conj (repeat 99 {:service "test-strange-message" :ttl 5 :description "just another message"})
               {:service "test-strange-message" :description "wohoo, this is strange!"})))


;;function calls below will be evaluated when this file is loaded (using C-c C-k in emacs/CIDER)

(burst! 100 (apply-metric-series {:service "test-12345" :description "hello!"} [1 2 3 4 5]))

#_(burst! 20 (take 300 spike-every-100))

#_(burst! 10 (take 101 strange-message-every-100))

#_(burst! 100 (take 300 cycle-1-10))
#_(burst! 100 (take 300 spike-every-100))