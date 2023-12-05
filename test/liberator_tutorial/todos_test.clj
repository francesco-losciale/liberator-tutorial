(ns liberator-tutorial.todos-test
  (:require
    [clojure.test :refer :all]
    [jason.convenience :as json]
    [liberator-tutorial.shared.fixtures :refer [running-server server-base-url]]
    [liberator-tutorial.todos-resource :as todos-resource]
    [org.httpkit.client :as http]))

(use-fixtures :once running-server)

(deftest can-get-todo-lists
  (let [response @(http/get (str server-base-url "/todos") {:as :text})]
    (testing "returns 200"
      (is (= 200 (:status response)))
      (is (= [] (todos-resource/->todo-list response))))))

(deftest can-post-todo-list
  (let [todo-list {:todos []}
        response @(http/post (str server-base-url "/todos") {:body (json/->wire-json todo-list)})]
    (testing "returns 201"
      (is (= 201 (:status response)))
      (is (= {:id 1 :todos []} (todos-resource/->todo-list response))))))
