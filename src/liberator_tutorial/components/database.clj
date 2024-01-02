(ns liberator-tutorial.components.database
  (:require [com.stuartsierra.component :as component]))

(defrecord Database []
  component/Lifecycle

  (start [component]
    (let [state-atom (atom {:todo-lists []})]
      (assoc component :state state-atom)))

  (stop [component]
    (let [state-atom (:state component)]
      (reset! state-atom {:todo-lists []})
      (dissoc component :state))))

(defn new-database []
  (map->Database {}))

(defn add-todo-list
  [database todo-list]
  (let [todo-list (assoc todo-list :id 1)]
    (swap!
      (:state database)
      (fn [state] {:todo-lists (conj (:todo-lists state) todo-list)}))
    todo-list))

(defn get-add-todo-lists
  [database]
  (:todo-lists @(:state database)))

(defn clear
  [database]
  (let [state-atom (:state database)]
    (reset! state-atom {:todo-lists []})))
