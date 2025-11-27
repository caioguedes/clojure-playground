(ns core
  (:import [io.undertow Undertow]
           [io.undertow.server HttpHandler HttpServerExchange]
           [io.undertow.util Headers]))

(def handler (reify HttpHandler
               (handleRequest [_ exchange]
                 (println "Helllooo!!!")
                 (println (.getRequestHeaders exchange))
                 (doto (.getResponseHeaders exchange)
                   (.put Headers/CONTENT_TYPE "application/json"))
                 (doto (.getResponseSender exchange)
                   (.send "{\"messag\": \"Hello World\"}"))
                 (Thread/sleep 1000))))

(def builder (doto (Undertow/builder)
               (.addHttpListener 8080 "localhost")
               (.setHandler handler)))

(def server (.build builder))

(.start server)
(.stop server)
