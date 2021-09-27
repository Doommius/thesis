(ns jepsen.SFJepsen.Driver.core_test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :refer [debug info warn]]
            [cheshire.core :refer :all]
            [jepsen.SFJepsen.Driver.core :as sfc]))

(def c (sfc/connect "jepsen.northeurope.cloudapp.azure.com" {:timeout 100}))

; Delete all data before each test
;(use-fixtures :each #(do (sfc/delete-all! c nil) (%)))


(deftest write-get-test
  (testing "a simple key"
    (sfc/write c "test" 10)
    (is (= 10 (sfc/get c "test")))
    )

  (testing "Paths and unicode"
    (sfc/write c "ℵ" 20)
    (is (= 20 (sfc/get c "ℵ")))
    )
  )

(deftest puthttp
  (testing "Does the testput?"

    (is (= (str "http://" (:endpoint c) ":35112/api") (sfc/base-url c)))

    (is (= 10 (sfc/write c "rand" 10)))
    ;(print (sfc/get c "rand"))
    (is (= 10 (sfc/get c "rand")))
    )
  )


;(deftest missing-values
;  (is (false? (sfc/get c "SDFSDFSDFSD:nonexistent"))))


(deftest cas-test!
  (is (= 0 (sfc/write c "foo" 0)))
  (is (= 0 (sfc/get c "foo")))
  (is (= 0 (sfc/cas c "foo" 5 10)))
  (is (= 0 (sfc/get c "foo")))
  (is (= 5 (sfc/write c "foo" 5)))
  (is (= 52 (sfc/cas c "foo" 52 5)))
  (is (= 52 (sfc/get c "foo"))))




;Queue


(deftest queue-cycle-test
  (testing "Addeding item to queue"
    (info (sfc/enqueue c 12312312312))
    )

  (testing "checking queue count."
    (is (= 1 (sfc/queuecount c)))
    )

  (testing "Dequeueing"
    (is (= 12312312312 (sfc/dequeue c)))
    )
  )


(deftest queue-dequeue-test

  (testing "Dequeueing"
    (info (sfc/dequeue c)))
  )

