(ns liberator-tutorial.todos-test
  (:require
    [clojure.test :refer :all]
    [jason.convenience :as json]
    [liberator-tutorial.shared.fixtures :refer [running-server server-base-url]]
    [org.httpkit.client :as http]))

(use-fixtures :once running-server)

(deftest can-get-todos
  (let [response @(http/get (str server-base-url "/todos") {:as :text})]
    (testing "returns 200"
      (is (= 200 (:status response)))
      (is (= [] (json/<-wire-json (:body response)))))))

(deftest can-post-todo
  (let [response @(http/post (str server-base-url "/todos") {:body (json/->wire-json [])})]
    (testing "returns 201"
      (is (= 201 (:status response)))
      (is (= [] (json/<-wire-json (:body response)))))))
