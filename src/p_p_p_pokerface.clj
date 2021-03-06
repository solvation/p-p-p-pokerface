(ns p-p-p-pokerface)

(defn rank [card]
  (let [picture-values {\T 10, \J 11, \Q 12, \K 13, \A 14}
        [val _] card]
     (if (Character/isDigit val)
           (Integer/parseInt (str val))
           (picture-values val))
    )
  )

(rank "4H")

(defn suit [card]
  (let [[_ col] card]
    (str col)
    )
  )

(defn pair? [hand]
  (if (= (count (filter (fn [x] (== x 2)) (vals (frequencies (map rank hand))))) 1)
    true
    false)
  )

(defn three-of-a-kind? [hand]
  (if (some #{3} (vals (frequencies (map rank hand))))
    true
    false)
  )

(defn four-of-a-kind? [hand]
  (if (some #{4} (vals (frequencies (map rank hand))))
    true
    false))

(defn flush? [hand]
  (if (some #{5} (vals (frequencies (map suit hand))))
    true
    false))

(defn full-house? [hand]
  (and (pair? hand) (three-of-a-kind? hand))
  )

(defn two-pairs? [hand]
  (or
   (four-of-a-kind? hand)
   (= (count (filter (fn [x] (== x 2)) (vals (frequencies (map rank hand))))) 2))
  )

(defn straight? [hand]
  (let [sorted-values (sort (map rank hand))
        sorted-values-ace-as-one (sort (replace {14 1} sorted-values))]
    (or
     (= sorted-values (range (first sorted-values) (+ (first sorted-values) 5)))
     (= sorted-values-ace-as-one (range (first sorted-values-ace-as-one) (+ (first sorted-values-ace-as-one) 5)))
     )
   )
  )

(defn straight-flush? [hand]
  (and (straight? hand) (flush? hand))
  )

(defn high-card? [hand]
  true)

(defn value [hand]
  (let [checkers #{[high-card? 0]
                   [pair? 1]
                   [two-pairs? 2]
                   [three-of-a-kind? 3]
                   [straight? 4]
                   [flush? 5]
                   [full-house? 6]
                   [four-of-a-kind? 7]
                   [straight-flush? 8]}
        test-results (map (fn [a-func] (let [the-func (first a-func)] (if (the-func hand) (second a-func) 0))) checkers)]
        (apply max test-results)
    )
)


