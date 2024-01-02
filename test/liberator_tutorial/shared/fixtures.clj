(ns liberator-tutorial.shared.fixtures
  (:require [clojure.test :refer :all]
            [com.stuartsierra.component :as component]
            [liberator-tutorial.components.database :as database]))

(defn with-system-lifecycle [system-atom]
  (fn [f]
    (reset! system-atom (component/start-system @system-atom))
    (f)
    (reset! system-atom (component/stop-system @system-atom))))

(defn with-empty-database [system-atom]
  (fn [f]
    (f)
    (database/clear (:database @system-atom))))
