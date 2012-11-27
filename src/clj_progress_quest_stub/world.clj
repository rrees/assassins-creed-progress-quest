(ns clj-progress-quest-stub.world)

(defn create-world [] {
	:quest-text "The quest begins"
	:quests-completed 0
	})

(defn update-world [world progress]
	(if (= progress 100)
		(let [quests-completed (inc (:quests-completed world))
			quest-text (str "Carrying out quest " quests-completed)]
		(assoc world :quest-text quest-text :quests-completed quests-completed))
		world))
