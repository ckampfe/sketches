(ns sketches.4
  (:require [quil.core :as q]))

(def size [830 1000])
(def grid-n 5)
(def base-width
  (let [[width height] size]
    (/ width grid-n)))

(def partitions
  (cons 0 (map #(* % base-width)
               (range 1 (+ grid-n 1)))))

(defn setup []
  (q/color-mode :hsb 830)
  (doseq [part partitions]
    (q/with-fill [part part 400]
      (q/rect part -1 base-width 1000))))

(q/defsketch sketches
  :title "#4"
  :setup setup
  :size size
  :features [:keep-on-top])

(defn -main [& args])
