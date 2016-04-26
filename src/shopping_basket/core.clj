(ns shopping-basket.core
  (:gen-class))

(def product-catalogue
  [{:code :a01 :name "Jeans" :price 32.99M}
   {:code :b01 :name "Shirt" :price 12.99M}
   {:code :c03 :name "Belt" :price 9.99M}])

(defn add-to-basket
  [basket product]
  (update basket (:code product) (fn [[prod qty]] (if prod
                                                     [prod (inc qty)]
                                                     [product 1]))))

(defmulti cost-of-item
  (fn [[{:keys [code]} _]] code))

(defmethod cost-of-item
  :a01
  [[{:keys [price]} qty]]
  (* price (* 1.5M (/ qty 2M))))

(defmethod cost-of-item
  :default
  [[{:keys [price]} qty]]
  (* price qty))

(defn total-of-basket
  [basket]
  (reduce (fn [acc item] (+ acc (cost-of-item item))) 0 (vals basket)))

(def basket (atom {}))

(comment



  (total-of-basket {:a01 [{:code :a01 :name "Jeans" :price 32.99M} 2]})
  (cost-of-item [{:code :a01 :name "Jeans" :price 32.99M} 2])
  (swap! basket add-to-basket (first product-catalogue))


  (add-to-basket {:a01 [{:code :a01 :name "Jeans" :price 32.99M} 2]} (first product-catalogue))

  (total-of-basket
   (->
     {}
     (add-to-basket (first product-catalogue))
     (add-to-basket (first product-catalogue))
     (add-to-basket (second product-catalogue))
     (add-to-basket (nth product-catalogue 2))))

  )
