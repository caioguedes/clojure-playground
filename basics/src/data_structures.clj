(ns data-structures
  (:require [clojure.repl :refer :all]))
;;
;; Vectors
;;
(def vec-1 [1 2 3 "boo" "bar" [:key 1]])

(type vec-1)
(type [])
(ancestors (type vec-1))

;;
;; Sets
;;
(def set-1 #{1 2 3})

(type set-1)
(type #{})
(ancestors (type set-1))

(comment
  #{1 2 3 4 1})

;;
;; Maps
;;

(def map-1 {"key" "value" "key-2" "value-2"})

(def map-2 {:k [1 2 3] :s #{"b" "c" "a"}})

(type map-1)
(type {})
(ancestors (type map-1))

(comment
  {{"map-as-key" "key-map-value"} "value"}
  {"key" "value" "missing-pair"})

;;
;; Lists
;;
(def list-1 '(1 2 "a" #{1 2 3} {:key :value}))

(type list-1)
(type '())
(ancestors (type list-1))


;;
;; Sequences
;;
(seq nil)
(seq [])
(seq '())

(type (seq '(1 2 3)))
(type (seq [1 2 3]))
(type (seq "my a string"))

(comment
  (type (seq 1234)))
