(ns liberator-tutorial.unit.validation.todo-lists-test
  (:require [clojure.test :refer :all]
            [liberator-tutorial.resources.todo-lists :as todo-lists-resource]
            [malli.core :as malli]))

(deftest todo-lists-request-body
  (testing "valid request body"
    (is (malli/validate todo-lists-resource/request-body-schema {:todos []}))
    (is (malli/validate todo-lists-resource/request-body-schema {:todos [{:title "abc"}]}))
    (is (malli/validate todo-lists-resource/request-body-schema {:todos [{:title "abc"} {:title "abc"}]})))
  (testing "invalid request body"
    (is (false? (malli/validate todo-lists-resource/request-body-schema {})))
    (is (false? (malli/validate todo-lists-resource/request-body-schema {:todos [{}]})))
    (is (false? (malli/validate todo-lists-resource/request-body-schema {:todos [{:title nil}]})))
    (is (false? (malli/validate todo-lists-resource/request-body-schema {:todos [{:wrong nil}]})))))
