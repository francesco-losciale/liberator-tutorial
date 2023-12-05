(ns liberator-tutorial.todos-resource
  (:require [liberator.core :as liberator]
            [jason.convenience :as json]))

(defn ->todo-list
  [request]
  (-> request
      :body
      (json/<-wire-json)))

(defn create-todo-list
  [todo-list]
  (assoc todo-list :id 1))

(def resource
  (liberator/resource
    :allowed-methods [:get :post]
    :available-media-types ["application/json"]
    :post! (fn [{:keys [request]}]
             {:todo-list (create-todo-list (->todo-list request))})
    :handle-ok (fn [{:keys [request]}]
                 (json/->wire-json []))
    :handle-created (fn [{:keys [todo-list]}]
                      (json/->wire-json todo-list))))
