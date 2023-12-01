(ns liberator-tutorial.core
  (:require [compojure.core :refer [ANY defroutes]]
            [liberator.core :refer [defresource]]
            [ring.adapter.jetty :as ring-jetty]
            [ring.middleware.params :refer [wrap-params]])
  (:import (org.eclipse.jetty.server Server)))

(defresource parameter [txt]
             :available-media-types ["text/plain"]
             :handle-ok (fn [_] (format "The text is %s" txt)))

(defroutes app
           (ANY "/bar/:txt" [txt] (parameter txt)))

(def handler
  (-> app
      wrap-params))

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
