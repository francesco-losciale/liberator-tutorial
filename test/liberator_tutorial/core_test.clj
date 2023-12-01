(ns liberator-tutorial.core-test
  (:require [clojure.test :refer :all]
            [freeport.core :refer [get-free-port!]]
            [liberator-tutorial.core :as core]
            [org.httpkit.client :as http]))

(def port (get-free-port!))

(defn running-server [f]
  (let [state (atom {})]
    (core/start-server state port)
    (f)
    (core/stop-server state)))

(use-fixtures :once running-server)

(def base-url (str "http://localhost:" port))

(deftest test-resource
  (let [response @(http/get (str base-url "/bar/test") {:as :text})]
    (testing "returns 200"
      (is (= 200 (:status response)))
      (is (= "The text is test" (:body response))))))
