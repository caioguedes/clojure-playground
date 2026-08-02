(ns main
      (:require [clojure.pprint :refer [pprint]])
    (:import
      graphql.ExecutionResult
      graphql.GraphQL
      graphql.schema.GraphQLSchema
      graphql.schema.StaticDataFetcher
      graphql.schema.idl.RuntimeWiring
      graphql.schema.idl.SchemaGenerator
      graphql.schema.idl.SchemaParser
      graphql.schema.idl.TypeDefinitionRegistry))

(def schema "type Query{hello: String}")
(def parser (SchemaParser.))
(def definition (.parse parser schema))

(def runtime (-> (RuntimeWiring/newRuntimeWiring)
                 (.type "Query" (fn [b] (.dataFetcher b "hello" (StaticDataFetcher. "world"))))
                 (.build)))

(def generator (SchemaGenerator.))
(def graphql-schema (.makeExecutableSchema generator definition runtime))

(def build (.build (GraphQL/newGraphQL graphql-schema)))

(def result (.execute build "{hello}"))

(pprint (.getData result))