(ns liberator-tutorial.todos-test
  (:require
    [jason.convenience :as json]
    [clojure.test :refer :all]
    [liberator-tutorial.shared.fixtures :refer [running-server server-base-url]]
    [org.httpkit.client :as http]))

(use-fixtures :once running-server)

(deftest can-get-todos
  (let [response @(http/get (str server-base-url "/todos") {:as :text})]
    (testing "returns 200"
      (is (= 200 (:status response)))
      (is (= [] (json/<-wire-json (:body response)))))))
