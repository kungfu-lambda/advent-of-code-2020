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

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
