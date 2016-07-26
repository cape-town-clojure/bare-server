(ns bare-server.app
  (:require [bare-server.server :as server]
            [com.stuartsierra.component :as component]))

(defn build-app [config]
  (component/system-map
   :server (server/new config)))
