(ns liberator-tutorial.shared.fixtures
  (:require [clojure.test :refer :all]
            [com.stuartsierra.component :as component]))

(defn with-system-lifecycle [system-atom]
  (fn [f]
    (reset! system-atom (component/start-system @system-atom))
    (f)
    (reset! system-atom (component/stop-system @system-atom))))
