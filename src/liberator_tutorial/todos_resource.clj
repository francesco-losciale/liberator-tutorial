(ns liberator-tutorial.todos-resource
  (:require [liberator.core :as liberator]
            [jason.convenience :as json]))

(def resource
  (liberator/resource
    :allowed-methods [:get :post]
    :available-media-types ["application/json"]
    :post! (fn [context])
    :handle-ok (fn [{:keys [request]}]
                 (json/->wire-json []))
    :handle-created (fn [context]
                      (json/->wire-json []))))
