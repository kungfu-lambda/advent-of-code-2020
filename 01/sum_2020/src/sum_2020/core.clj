(ns sum-2020.core
  (:gen-class)
  (:require [clojure.string :as cstr]
            [clojure.java.io :as io]))

;; here i am returning a set because
;; (contains? some-set) is nearly instant.
(defn read-input! []
  (->>
   "input.txt"
   io/resource
   slurp
   cstr/split-lines
   (map #(Integer/parseInt %))
   set))

(defn find-pair [nums]
  (let [x (->> nums
               (filter #(contains? nums (- 2020 %)))
               first)
        y (- 2020 x)]
    [x y]))

(defn multiply-pair [nums]
  (apply * (find-pair nums)))

(defn find-triplet [nums]
  (->> (for [x nums
             y nums
             :let [z (- 2020 x y)]
             :when (contains? nums z)]
         [x y z])
       first))

(defn multiply-triplet [nums]
  (apply * (find-triplet nums)))

(defn get-answers [nums]
  (let [multiplied-pair (multiply-pair nums)
        multiplied-triplet (multiply-triplet nums)]
    [multiplied-pair multiplied-triplet]))

(defn -main
  [& args]
  (get-answers (read-input!)))
