(ns core
  (:require [clojure.spec.alpha :as s]))

;; Spec Schema
;; ProcessExecution {id, process-id, started-at, finished-at, state = {running, complete, failed}}
;; Process {id, name, created-at}

(s/def :process/id uuid?)
(s/def :process/name string?) ;; not empty?
(s/def :process/created_at inst?) ;; not empty?
(s/def :entity/process
  (s/keys
   :req-un [:process/id :process/name :process/created_at]))

(defn -main []
  (println "Hello"))

(comment
  (s/conform :process/id (random-uuid)) ;; true
  (s/conform :process/id (str (random-uuid))) ;; invalid, we need coercion

  (def process-1 {:id (random-uuid)
                  :name "hello"
                  :created_at (java.util.Date.)})

  (s/conform :entity/process {:id (random-uuid)
                              :name "foobar"
                              :created_at (java.util.Date.)})
  :hodor)
