(ns main
  (:require [clojure.pprint :refer [pprint]])
  (:import graphql.ExecutionResult
           graphql.GraphQL
           graphql.schema.GraphQLSchema
           graphql.schema.DataFetchingEnvironment
           graphql.schema.StaticDataFetcher
           graphql.schema.DataFetcher
           graphql.schema.DataFetcherFactory
           graphql.schema.idl.TypeRuntimeWiring$Builder
           graphql.schema.idl.RuntimeWiring
           graphql.schema.idl.SchemaGenerator
           graphql.schema.idl.SchemaParser
           graphql.schema.idl.TypeDefinitionRegistry
           org.dataloader.BatchLoader))

;; Database
(defn authors []
  [{"id" 1 "name" "Author 1"}
   {"id" 2 "name" "Author 2"}])

(defn books []
  [{"id" 1 "title" "Book 1" "authorId" 1}
   {"id" 2 "title" "Book 2" "authorId" 1}
   {"id" 3 "title" "Book 3" "authorId" 2}
   {"id" 4 "title" "Book 4" "authorId" 2}])

(defn get-author [id] ;; !! n+1 problem
  (first (filter #(= id (get % "id")) (authors))))

;; Fetchers
(def authors-fetcher
  (reify DataFetcher
    (get [_ _] (authors))))

(def book-author-fetcher
  (reify DataFetcher
    (get [_ env]
      (let [book (.getSource env)]
        (get-author (get book "authorId"))))))

(def books-fetcher
  (reify DataFetcher
    (get [_ env] (books))))

(def schema (slurp "resources/schema.graphql"))
(def parser (SchemaParser.))
(def definition (.parse parser schema))

; Wire
(def runtime (-> (RuntimeWiring/newRuntimeWiring)
                 (.type "Query"
                        (fn [builder]
                          (.dataFetcher builder "books" books-fetcher)))
                 (.type "Book"
                        (fn [builder]
                          (.dataFetcher builder "author" book-author-fetcher)))
                 (.build)))

(def generator (SchemaGenerator.))
(def graphql-schema (.makeExecutableSchema generator definition runtime))
(def build (.build (GraphQL/newGraphQL graphql-schema)))

;; Query
(def result (.execute build "{books {id, title, author {name}}}"))

(pprint result)
(pprint (.getData result))
(pprint (.getErrors result))
