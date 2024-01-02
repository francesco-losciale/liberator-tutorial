(ns liberator-tutorial.resources.todo-lists-test
  (:require
    [clojure.test :refer :all]
    [jason.convenience :as json]
    [liberator-tutorial.database :as database]
    [liberator-tutorial.shared.fixtures :as fixtures]
    [liberator-tutorial.system :as system]
    [org.httpkit.client :as http]))

(def port 8080)
(def server-base-url (str "http://localhost:" port))
(def system-atom (atom (system/new-system {:port port})))
(use-fixtures :once (fixtures/with-system-lifecycle system-atom))
(use-fixtures :each database/in-memory-fixture)

(deftest can-view-todo-lists
  (let [response @(http/get (str server-base-url "/todo-lists") {:as :text})]
    (testing "returns 200"
      (is (= 200 (:status response)))
      (is (= [] (json/<-wire-json (:body response)))))))

(deftest can-create-todo-list
  (let [todo-list {:todos [{:title "abc"}]}
        response @(http/post (str server-base-url "/todo-lists")
                             {:body    (json/->wire-json todo-list)
                              :headers {"content-type" "application/json"}})]
    (testing "returns 201"
      (is (= 201 (:status response)))
      (is (= {:id 1 :todos [{:title "abc"}]} (json/<-wire-json (:body response)))))))

(deftest can-view-created-todo-lists
  (let [todo-list {:todos [{:title "abc"}]}
        _ @(http/post (str server-base-url "/todo-lists")
                      {:body    (json/->wire-json todo-list)
                       :headers {"content-type" "application/json"}})
        response @(http/get (str server-base-url "/todo-lists") {:as :text})]
    (testing "returns 200"
      (is (= 200 (:status response)))
      (is (= [{:id 1 :todos [{:title "abc"}]}] (json/<-wire-json (:body response)))))))

(deftest fail-creating-todo-list
  (let [response @(http/post (str server-base-url "/todo-lists") {:body (json/->wire-json {})})]
    (testing "returns 422"
      (is (= 422 (:status response))))))
