(ns bare-server.server
  (:require [org.httpkit.server :as http]
            [com.stuartsierra.component :as component]))

(defn handler [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    {:a 5 :b 6}})

(defrecord Server [config]
  component/Lifecycle
  (start [this]
    (let [current-instance (http/run-server handler config)]
      (assoc this :instance current-instance)))
  (stop [this]
    (let [stop-fn (:instance this)]
      (stop-fn)
      (dissoc this :instance))))

(defn new [config]
  (->Server config))
