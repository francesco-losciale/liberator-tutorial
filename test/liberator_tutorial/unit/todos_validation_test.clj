(ns liberator-tutorial.unit.todos-validation-test
  (:require [clojure.test :refer :all]
            [liberator-tutorial.todos-resource :as todos-resource]
            [malli.core :as malli]))

(deftest validate-request-body
  (testing "valid body"
    (is (malli/validate todos-resource/request-body-schema {:todos []}))
    (is (malli/validate todos-resource/request-body-schema {:todos [{:title "abc"}]}))
    (is (malli/validate todos-resource/request-body-schema {:todos [{:title "abc"} {:title "abc"}]})))
  (testing "invalid body"
    (is (false? (malli/validate todos-resource/request-body-schema {})))
    (is (false? (malli/validate todos-resource/request-body-schema {:todos [{}]})))
    (is (false? (malli/validate todos-resource/request-body-schema {:todos [{:title nil}]})))
    (is (false? (malli/validate todos-resource/request-body-schema {:todos [{:wrong nil}]})))))
