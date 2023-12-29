(ns liberator-tutorial.database)

(def state (atom {:todo-lists []}))

(defn in-memory-fixture [f]
  (reset! state {:todo-lists []})
  (f)
  (reset! state {:todo-lists []}))

(defn add-todo-list!
  [todo-list]
  (let [todo-list (assoc todo-list :id 1)]
    (swap! state (fn [state] {:todo-lists (conj (:todo-lists state) todo-list)}))
    todo-list))

(defn get-add-todo-lists
  []
  (:todo-lists @state))
