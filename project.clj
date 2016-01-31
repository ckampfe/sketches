(defproject sketches "0.1.0-SNAPSHOT"
  :description "sketches"
  :url "https://github.com/ckampfe/sketches"
  :license {:name "GNU AGPL"
            :url "https://www.gnu.org/licenses/agpl.txt"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [quil "2.3.0"]
                 [org.clojure/clojurescript "1.7.228"]]

  :plugins [[lein-cljsbuild "1.1.1"]]
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:builds [{:source-paths ["src"]
                        :compiler {:output-to "js/main.js"
                                   :output-dir "out"
                                   :main "sketches.core"
                                   :optimizations :none
                                   :pretty-print true}}]})
