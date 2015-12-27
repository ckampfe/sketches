(ns sketches.2
  (:require [quil.core :as q]))

(def size [830 1000])
(def light [0 0 200])
(def dark [230 230 0])

(defn factor [m n]
  (* (/ m n) 1.0))

(defn positions [factor n]
  (let [multipliers (range 1 (+ n 1))
        factors (repeat n factor)]
    (butlast (map * factors multipliers))))

(defn grid [width length n]
  (let [width-factor (factor width n)
        length-factor (factor length n)
        widths (positions width-factor n)
        lengths (positions length-factor n)]
    (for [x widths
          y lengths]
      [x y])))

(defn random-circle [x y]
  (let [r (rand-int 2)
        stroke light
        fill (if (> r 0) light dark)]
    (q/with-stroke stroke
      (q/with-fill fill
        (q/ellipse x y 20 20)))))

(defn draw [grid]
  (doseq [[x y] grid]
    (random-circle x y)))

(defn setup []
  (q/color-mode :hsb)
  (apply q/background dark)
  (let [[width length] size
        g (grid width length 32)]
    (draw g)))

(q/defsketch sketches
  :title "#2"
  :setup setup
  :size size
  :features [:keep-on-top])

(defn -main [& args])
