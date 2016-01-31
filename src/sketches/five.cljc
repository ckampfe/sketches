(ns sketches.five
  (:require [quil.core :as q :include-macros true]))

(def size [830 1000])
(def diameter 20)

(defrecord Bound [lower upper])
(defrecord Point [x y])

(defn base-factor [n m] (/ n m))

(defn partitions [n m]
  (cons 0 (map #(* % (base-factor n m))
               (range 1 (+ m 1)))))

(defn draw-circle [point]
  (q/ellipse (:x point)
             (:y point)
             (/ diameter 2)
             (/ diameter 2)))

(defn cos [radians]
  #?(:clj (Math/cos radians)
     :cljs (.cos js/Math radians)))

(defn sin [radians]
  #?(:clj (Math/sin radians)
     :cljs (.sin js/Math radians)))

(defn to-radians [degrees]
  #?(:clj (Math/toRadians degrees)
     :cljs (* (/ (.-PI js/Math)
                 180)
              degrees)))

(defn random-angle [{:keys [lower upper]}]
  (+
   (rand-int (- (+ 1 upper)
                lower))
   lower))

(defn point-on-arc [point radius degrees]
  (let [[x y] [(:x point) (:y point)]]
    (->Point (->> degrees
                  (to-radians)
                  (cos)
                  (* radius)
                  (+ x))
             (->> degrees
                  (to-radians)
                  (sin)
                  (* radius)
                  (+ y)))))

(defn random-point-on-arc [origin-point angle-bounds radius]
  (let [degrees (random-angle angle-bounds)]
    (point-on-arc origin-point radius degrees)))

(defn draw-squiggly [color width height]
  (apply q/fill [color (/ color 2) color])
  (apply q/stroke [color (/ color 2) color])
  (loop [last-point (->Point 0 height)
         angle-bounds (->Bound -90 90)]
    (if (< (:x last-point) width)
      (let [point (random-point-on-arc
                   last-point
                   angle-bounds
                   (/ diameter 5.8))]
        (draw-circle point)
        (recur point angle-bounds)))))

(defn setup []
  (let [[width height] size]
    (q/color-mode :hsb (/ height 3))
    (dorun
     (map (fn [h]
            (let [color (random-angle (->Bound 200 400))]
              (draw-squiggly color width h)))
          (partitions height 240)))))

(q/defsketch sketches
  :title "#5"
  :host "sketch5"
  :setup setup
  :size size
  :features [:keep-on-top])
