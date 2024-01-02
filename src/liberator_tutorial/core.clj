(ns liberator-tutorial.core
  (:require [com.stuartsierra.component :as component]
            [liberator-tutorial.system :as system]))

(defn main []
  (let [system (component/start-system (system/new-system {:port 3000}))]
    (.addShutdownHook
      (Runtime/getRuntime)
      (new Thread (fn* [] (component/stop-system system))))))
