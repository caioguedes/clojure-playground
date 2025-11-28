(ns core
  (:require [clojure.spec.alpha :as s]
            [clojure.string :as str]))

;; Spec Schema
;; ProcessExecution {id, process-id, started-at, finished-at, state = {running, complete, failed}}
;; Process {id, name, created-at}

;; Entity Commons
(s/def :entity/id uuid?)
(s/def :entity/at inst?) ;; not empty?

(s/def :process/name string?) ;; not empty?
(s/def :process/created-at :entity/at)
(s/def :entity/process
  (s/keys
   :req-un [:entity/id :process/name :process/created-at]))

(s/def :execution/id uuid?)
(s/def :execution/process-id string?)
(s/def :execution/created-at :entity/at)
(s/def :execution/finished-at :entity/at)
(s/def :execution/state keyword?)

(s/def :entity/execution
  (s/keys
   :req [:execution/id
         :process/id
         :execution/state
         :execution/created-at]
   :opt [:execution/finished-at]))

(defn -main []
  (println "Hello"))

(defn create-execution [process]
  (s/assert
   :entity/execution
   {:execution/id (random-uuid)
    :process/id (-> :process/id process)
    :execution/state :running
    :execution/created-at (java.util.Date.)}))

(comment
  (s/conform :process/id (random-uuid)) ;; true
  (s/conform :process/id (str (random-uuid))) ;; invalid, we need coercion

  (s/check-asserts true)

  (def process-1 (s/conform
                  :entity/process
                  {:process/id (random-uuid)
                   :process/name "hello"
                   :process/created-at (java.util.Date.)}))

  (create-execution process-1)

  (s/conform :entity/process {:id (random-uuid)
                              :name "foobar"
                              :created_at (java.util.Date.)})

  ;; Check methods

  (s/valid? :entity/process {})
  (s/assert :entity/process {})

  :hodor)
