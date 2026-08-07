(ns main
  (:require [clojure.pprint :refer [pprint]])
  (:import graphql.ExecutionResult
           graphql.GraphQL
           graphql.schema.GraphQLSchema
           graphql.schema.StaticDataFetcher
           graphql.schema.DataFetcher
           graphql.schema.idl.TypeRuntimeWiring$Builder
           graphql.schema.idl.RuntimeWiring
           graphql.schema.idl.SchemaGenerator
           graphql.schema.idl.SchemaParser
           graphql.schema.idl.TypeDefinitionRegistry))

(def schema (slurp "resources/schema.graphql"))
(def parser (SchemaParser.))
(def definition (.parse parser schema))

(defn resolve-hello [b]
  (.dataFetcher b "hello" (StaticDataFetcher. "world")))

(defn book-definition [^TypeRuntimeWiring$Builder builder]
  (.dataFetcher builder "books" (StaticDataFetcher. [{"id" 1 "title" "Book 1"}])))

; Wire
(def runtime (-> (RuntimeWiring/newRuntimeWiring)
                 (.type "Query" book-definition)
                 (.build)))

(def generator (SchemaGenerator.))
(def graphql-schema (.makeExecutableSchema generator definition runtime))
(def build (.build (GraphQL/newGraphQL graphql-schema)))

;; Query
(def result (.execute build "{books {id, title}}"))

(doall
 (println (.getData result))
 (println (.getErrors result)))
