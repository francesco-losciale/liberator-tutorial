(ns liberator-tutorial.todos-resource
  (:require
    [jason.convenience :as json]
    [liberator.core :as liberator]
    [liberator-mixin.json.core :refer [with-body-parsed-as-json]]
    [liberator-tutorial.database :as database]
    [malli.core :as malli]))

(def todo-list-schema
  (malli/schema
    [:vector
     [:map
      [:title [:string]]]]))

(def request-body-schema
  (malli/schema
    [:map {:closed true}
     [:todos todo-list-schema]]))

(def resource
  (liberator/resource
    (merge
      (with-body-parsed-as-json)
      {:allowed-methods       [:get :post]
       :available-media-types ["application/json"]
       :processable?          (fn [{:keys [request]}]
                                (condp = (:request-method request)
                                  :post (malli/validate request-body-schema (:body request))
                                  true))
       :post!                 (fn [{:keys [request]}]
                                {:todo-list (database/add-todo-list! (:body request))})
       :handle-ok             (fn [{:keys [request]}]
                                (json/->wire-json (database/get-add-todo-lists)))
       :handle-created        (fn [{:keys [todo-list]}]
                                (json/->wire-json todo-list))})))
