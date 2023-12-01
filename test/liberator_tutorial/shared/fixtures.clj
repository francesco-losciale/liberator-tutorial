(ns liberator-tutorial.shared.fixtures
  (:require [clojure.test :refer :all]
            [freeport.core :refer [get-free-port!]]
            [liberator-tutorial.core :as core]))

(def port (get-free-port!))
(def server-base-url (str "http://localhost:" port))

(defn running-server [f]
  (let [state (atom {})]
    (core/start-server state port)
    (f)
    (core/stop-server state)))
