(ns liberator-tutorial.components.server
  (:require [bidi.ring :as bidi]
            [com.stuartsierra.component :as component]
            [liberator-tutorial.resources.todo-lists :as todo-lists]
            [ring.adapter.jetty :as ring-jetty])
  (:import (org.eclipse.jetty.server Server)))

(def handler
  (->
    (bidi/make-handler
      ["/" {"todo-lists" #'todo-lists/resource}])))

(defrecord WebServer [port handler]
  component/Lifecycle

  (start [component]
    (let [^Server http-server (ring-jetty/run-jetty handler {:port port :join? false})]
      (assoc component :http-server http-server)))

  (stop [component]
    (let [http-server (:http-server component)]
      (.stop ^Server http-server)
      (dissoc component :http-server))))

(defn new-webserver
  [port]
  (map->WebServer {:port (or port 8080) :handler handler}))
