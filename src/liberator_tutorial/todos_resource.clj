(ns liberator-tutorial.todos-resource
  (:require [liberator.core :as liberator]
            [jason.convenience :as json]))

(def resource
  (liberator/resource
    :available-media-types ["application/json"]
    :handle-ok (fn [{:keys [request]}]
                 (json/->wire-json []))))
