(ns sketches.three
  (:require [quil.core :as q :include-macros true]))

(def size [830 1000])
(def light [0 0 200])
(def max-radius 215)
(def golden-ratio (* (/ (+ 1 #?(:clj (Math/sqrt 5)
                                :cljs (.sqrt js/Math 5))
              2)
           1.0)))
(def degrees-per-pixel
  (let [diameter (* max-radius 2)
        circ #?(:clj (* Math/PI diameter)
                :cljs (* (.-PI js/Math) diameter))
        ]
    (* (/ 360 circ) 1.0)))
(def degrees-spacing-factor 30)

(defn to-radians [degrees]
  #?(:clj (Math/toRadians degrees)
     :cljs (* (/ (.-PI js/Math)
                 180)
              degrees)))

(defn cos [radians]
  #?(:clj (Math/cos radians)
     :cljs (.cos js/Math radians)))

(defn sin [radians]
  #?(:clj (Math/sin radians)
     :cljs (.sin js/Math radians)))

(defn random-shortening [n less] (- (rand-int n) less))
(defn n->golden-ratio [length] (/ length golden-ratio))

(defn point-on-circle [[x0 y0] radius degrees]
  [(->> degrees
        (to-radians)
        (cos)
        (* radius)
        (+ x0))
   (->> degrees
        (to-radians)
        (sin)
        (* radius)
        (+ y0))])

(defn inc-degrees [degrees]
  (+ degrees (* degrees-spacing-factor degrees-per-pixel)))

(defn draw-lines [x0 y0]
  (loop [degrees 0.0]
    (if (< degrees 360)
      (let [length-factor (n->golden-ratio x0)
            line-length (random-shortening length-factor (* length-factor 0.88))
            [x1 y1] (point-on-circle [x0 y0] line-length degrees)]
        (apply q/stroke [220 220 20])
        (q/line x0 y0 x1 y1)
        (recur (inc-degrees degrees))))))

(defn setup []
  (q/color-mode :hsb)
  (apply q/background light)
  (apply q/fill light)
  (let [[width length] size
        x0 (* (/ width 2) 1.0)
        y0 (- length (n->golden-ratio length))
        circle-size (n->golden-ratio width)]
    (q/ellipse x0 y0 circle-size circle-size)
    (draw-lines x0 y0)))

(q/defsketch sketches
  :title "#3"
  :host "sketch3"
  :setup setup
  :size size
  :features [:keep-on-top])
