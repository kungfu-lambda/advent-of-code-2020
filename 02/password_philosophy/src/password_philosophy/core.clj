(ns password-philosophy.core
  (:gen-class)
  (:require [clojure.string :as cstr]
            [clojure.java.io :as io]))

(defn parse-line [line-str]
  (let [line (cstr/replace-first line-str #":" "")
        [min-max letter passwd] (cstr/split line #" ")
        [min max] (map #(Integer/parseInt %)
                       (cstr/split min-max #"-"))]
    {:letter letter :passwd passwd
     :min min :max max}))

(defn valid?-1
  "Validation for the part-1"
  [{:keys [passwd letter min max] :as parsed}]
  (let [cnt (get (frequencies passwd) (first letter) 0)
        valid? (and (<= min cnt) (>= max cnt))]
    valid?))

(defn valid?-2
  "Validation for the part-2"
  [{:keys [passwd letter min max] :as parsed}]
  (let [min (dec min)
        max (dec max)
        letters (vec (map str passwd))
        minl (letters min)
        maxl (letters max)]
    (and (or (= minl letter) (= maxl letter))
         (not= minl maxl))))

(defn find-answers [parsed-lines]
  (reduce (fn [{:keys [cnt1 cnt2]} parsed]
            (let [v?1 (valid?-1 parsed)
                  v?2 (valid?-2 parsed)]
              {:cnt1 (if v?1 (inc cnt1) cnt1)
               :cnt2 (if v?2 (inc cnt2) cnt2)}))
          {:cnt1 0 :cnt2 0}
          parsed-lines))

(defn read-input
  "Reads input resource and returns lines as vector"
  []
  (->> "input.txt"
       io/resource
       slurp
       cstr/split-lines))

(defn -main
  "Automatically returns part-1 result"
  [& args]
  (->> (read-input)
       (map parse-line)
       find-answers))
