(ns clj-progress-quest-stub.world)

(def speeds {:fast 600 :normal 1000 :slow 1300})

(defn create [] {
	:quest-line [{:quest-text "Entering the prologue"}
		{:quest-text "Flying over colonial Boston"}
		{:quest-text "Flying over the wilderness"}
		{:quest-text "Contemplating an eagle" :speed :fast}
		{:quest-text "Contrasting the lives of natives and Europeans"}]
	:quests-completed 0
	})

(defn update [world progress]
	(if (= progress 100)
		(let [quests-completed (inc (:quests-completed world))
			remaining-quests (rest (:quest-line world))]
		(assoc world
			:quests-completed quests-completed
			:quest-line remaining-quests))
		world))
