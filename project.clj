(defproject liberator-tutorial "0.1.0-SNAPSHOT"
  :dependencies [[compojure "1.6.3"]
                 [http-kit "2.8.0-beta3"]
                 [freeport "1.0.0"]
                 [liberator "0.15.2"]
                 [org.clojure/clojure "1.11.1"]
                 [org.eclipse.jetty/jetty-util "9.4.50.v20221201"]
                 [org.eclipse.jetty/jetty-server "9.4.50.v20221201"]
                 [org.eclipse.jetty/jetty-servlet "9.4.50.v20221201"]
                 [ring "1.10.0"]
                 [ring/ring-core "1.9.6"]
                 [ring/ring-jetty-adapter "1.9.5"]]
  :main liberator-tutorial.core/main)
