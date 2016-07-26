(ns bare-server.core
  (:require [org.httpkit.server :as http]))

(defn handler [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    {:a 5 :b 6}})

(http/run-server handler {:port 8080})
