(ns liberator-tutorial.core
  (:require [bidi.ring :as bidi]
            [liberator.core :as liberator]
            [liberator-tutorial.todos-resource :as todos]
            [ring.adapter.jetty :as ring-jetty])
  (:import (org.eclipse.jetty.server Server)))

(def example-resource
  (liberator/resource
    :available-media-types ["text/plain"]
    :handle-ok (fn [{:keys [request]}]
                 (str "The text is " (get-in request [:params :txt])))))

(def handler
  (->
    (bidi/make-handler
      ["/" {["bar/" :txt] example-resource
            "todos"        #'todos/resource}])))

(defn start-server
  [state port]
  (let [server (ring-jetty/run-jetty handler {:port (or port 8080) :join? false})]
    (swap! state assoc :server server)))

(defn stop-server
  [state]
  (let [server (:server @state)]
    (.stop ^Server server)
    (swap! state assoc :server nil)))

(defn main
  [_]
  (let [state (atom {})]
    (start-server state 3000)
    (.addShutdownHook
      (Runtime/getRuntime)
      (new Thread (fn* [] (stop-server state))))))
