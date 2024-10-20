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
(type map-2)
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

(when (seq? [1 2 3 ]) :not-empty)
(when (empty? []) :empty)

(comment
  (type (seq 1234)))

;;
;; Functions
;;

;;
;; get, get-in
;;
(get [] 0)
(get [1 2 3] 0)
(get "a b c" 2)
(get ["foo" "bar"] 2 "buzz")
(get {:key "value" :key-2 "value-2"} :key)

(comment
  (get '(0 1 2) 0)
  (get #{1 2 3} 0))

(get-in [["A" "C" "_"]
         ["_" "X" "_"]
         ["B" "_" "_"]] [1 1])
(get-in {:foo {:bar "bar-value"}
         :buzz {:zig "zig-value"}} [:buzz :zig])
(get-in {:foo {:bar "bar-value"}} [:foo :buzz] :not-found)
(get-in [1 2 3] [0])

(comment
  (get-in '(1 2 3) [0])
  (get-in #{1 2 3} [0]))

;;
;; nth
;;
(nth '(1 2 3) 2)
(nth [1 2 3] 2)
(nth "a b c" 2)

(comment
  (nth #{1 2 3} 0)
  (nth {:a :b} :a))

;;
;; assoc, assoc-in
;;
(assoc [1 2 3] 0 10)
(assoc {:foo "foo-value"
        :bar "bar-value"} :foo "foo-value-updated")

(comment
  (assoc '(1 2 3) 0 10)
  (assoc #{1 2 3} 0 10)
  (assoc (vec "foo bar") 0 \a))

(assoc-in [["x" "_" "o"]
           ["_" "_" "o"]
           ["_" "_" "x"]] [1 1] "x")

(assoc-in {:foo "foo-value"
           :bar {:nested {:buzz "buzz-value"}}}
          [:bar :nested :buzz] "buzz-value-updated")