(ns toboggan-trajectory.core
  (:gen-class)
  (:require [clojure.string :as cstr]
            [clojure.java.io :as io]))

(defn read-input! []
  (->> "input.txt"
       io/resource
       slurp
       cstr/split-lines))

(defn count-trees-1 
  ([lines] (count-trees-1 (rest lines) 0 0))
  ([lines x cnt]
   (let [[current & remaining] lines
         points (cstr/split current #"")
         ndx (mod (+ 3 x) (count points))
         tree? (= (points ndx) "#")
         cnt (if tree? (inc cnt) cnt)]
     (if (= remaining nil)
       cnt
       (recur remaining ndx cnt)))))

(defn find-answers [lines]
  (let [cnt1 (count-trees-1 lines)]
    {:cnt1 cnt1}))

(defn -main
  "main."
  [& args]
  (->> (read-input!) (find-answers)))
