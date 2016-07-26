(ns bare-server.app
  (:require [bare-server.server :as server]
            [com.stuartsierra.component :as component]))

(defn build-app [config]
  (component/system-map
   :server (server/new config)))

(def config
  {:port 8080})

(defn -main []
  (println "Starting app....")
  (let [app (component/start (build-app config))]
    (Thread/sleep 20000)
    (println "Stopping app!")
    (component/stop app)))
