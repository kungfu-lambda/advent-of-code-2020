(ns toboggan-trajectory.core
  (:gen-class)
  (:require [clojure.string :as cstr]
            [clojure.java.io :as io]))

(defn read-input! []
  (->> "input.txt"
       io/resource
       slurp
       cstr/split-lines))

(defn count-trees
  "down is step size going down,
   right and x-step is step size going right.
   x is the current location on the x axis, cnt is
   the ecountered tree count."
  ([lines down right] 
   (count-trees (->> lines (take-nth down) (drop 1)) right 0 0))
  ([lines x-step x cnt]
   (let [[current & remaining] lines
         points (cstr/split current #"")
         ndx (mod (+ x-step x) (count points))
         tree? (= (points ndx) "#")
         cnt (if tree? (inc cnt) cnt)]
     (if (= remaining nil)
       cnt
       (recur remaining x-step ndx cnt)))))

(defn counting-part-2 [lines part1]
  (let [a (count-trees lines 1 1)
        b part1
        c (count-trees lines 1 5)
        d (count-trees lines 1 7)
        e (count-trees lines 2 1)]
    (* a b c d e)))

(defn find-answers [lines]
  (let [part-1 (count-trees lines 1 3)
        part-2 (counting-part-2 lines part-1)]
    {:part-1 part-1 :part-2 part-2}))

(defn -main
  "main."
  [& args]
  (->> (read-input!) (find-answers)))
