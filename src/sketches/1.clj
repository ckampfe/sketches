(ns sketches.1
  (:require [quil.core :as q]))

(def size [415 500])
(def min-line-length 100)

(defn line-length [[x1 y1 x2 y2]]
  (Math/sqrt
   (+ (Math/pow (- x2 x1) 2.0)
      (Math/pow (- y2 y1) 2.0))))

(defn midpoint [[x1 y1 x2 y2]]
  [(/ (+ x1 x2) 2.0)
   (/ (+ y1 y2) 2.0)])

(defn horizontal? [[x1 y1 x2 y2]]
  (let [ydiff (- y2 y1)
        xdiff (- x2 x1)]
    (< (* 1.0
          (/ (- y2 y1)
             (- x2 x1))) 1)))

(defn fuzz-factor []
  (let [negative-positive (rand 1)
        fuzz-factor-value (rand 0.1)]
    (if (> negative-positive 0.5)
      (+ 1.01 fuzz-factor-value)
      (- 1.01 fuzz-factor-value))))

(defn next-line [previous-lines]
  (let [previous-line   (first previous-lines)
        was-horizontal? (horizontal? previous-line)
        [nx1 ny1]       (midpoint previous-line)
        [nx2 ny2]       (if was-horizontal?
                          [(* nx1 (fuzz-factor)) 1.1]
                          [1.1 (* ny1 (fuzz-factor))])]
    [nx1 ny1 nx2 ny2]))

(defn draw [state]
  (q/fill 255 255 (:color state))
  (q/background 240)
  (loop [lines (:lines state)]
    (if (> (line-length (first lines)) 4)
      (recur
       (cons (next-line lines) lines))
      (dorun (for [line lines]
               (apply q/line line))))))

(defn setup []
  (q/color-mode :hsb)
  (q/fill 255 255 255)
  (draw {:lines (list [210 1 229 500])
         :color 255}))

(q/defsketch sketches
  :title "#1"
  :setup setup
  :size size
  :features [:keep-on-top])

(defn -main [& args])
