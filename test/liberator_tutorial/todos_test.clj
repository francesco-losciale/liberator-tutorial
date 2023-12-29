(ns liberator-tutorial.todos-test
  (:require
    [clojure.test :refer :all]
    [jason.convenience :as json]
    [liberator-tutorial.shared.fixtures :refer [running-server server-base-url]]
    [org.httpkit.client :as http]))

(use-fixtures :once running-server)

(deftest can-view-todo-lists
  (let [response @(http/get (str server-base-url "/todos") {:as :text})]
    (testing "returns 200"
      (is (= 200 (:status response)))
      (is (= [] (json/<-wire-json (:body response)))))))

(deftest can-create-todo-list
  (let [todo-list {:todos [{:title "abc"}]}
        response @(http/post (str server-base-url "/todos")
                             {:body    (json/->wire-json todo-list)
                              :headers {"content-type" "application/json"}})]
    (testing "returns 201"
      (is (= 201 (:status response)))
      (is (= {:id 1 :todos [{:title "abc"}]} (json/<-wire-json (:body response)))))))

(deftest fail-creating-todo-list
  (let [response @(http/post (str server-base-url "/todos") {:body (json/->wire-json {})})]
    (testing "returns 422"
      (is (= 422 (:status response))))))
