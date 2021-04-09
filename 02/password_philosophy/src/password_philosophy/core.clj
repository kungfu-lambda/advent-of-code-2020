(ns password-philosophy.core
  (:gen-class)
  (:require [clojure.string :as cstr]
            [clojure.java.io :as io]))

(defn analyze-line [line-str]
  (let [line (cstr/replace-first line-str #":" "")
        [min-max letter passwd] (cstr/split line #" ")
        [min max] (map #(Integer/parseInt %) (cstr/split min-max #"-"))
        cnt (get (frequencies passwd) (first letter) 0)
        valid? (and (<= min cnt) (>= max cnt))]
    {:letter letter :passwd passwd
     :min min :max max
     :cnt cnt :valid? valid?}))

(defn read-input
  "Reads input resource and returns lines as vector"
  []
  (->> "input.txt"
       io/resource
       slurp
       cstr/split-lines))

(defn count-valids [lines]
  (->> lines
       (map analyze-line)
       (filter #(:valid? %))
       count))

(defn -main
  "Automatically returns part-1 result"
  [& args]
  (count-valids (read-input)))
