(ns liberator-tutorial.system
  (:require [com.stuartsierra.component :as component]
            [liberator-tutorial.components.server :as server]))

(defn new-system [{:keys [port]}]
  (component/system-map
    :app (component/using
           (server/new-webserver port)
           {})))
