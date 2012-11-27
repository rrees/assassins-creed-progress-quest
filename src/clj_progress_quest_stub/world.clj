(ns clj-progress-quest-stub.world)

(def speeds {:fast 600 :normal 1000 :slow 1300})

(def states {:prologue [:assassinating :town :wilderness]
	:wilderness [:town]
	:town [:town :assassinating :wilderness]})

(defn create [] {
	:quest-line [ {:quest-text "Starting the game"}
		{:quest-text "Entering the prologue" :speed :fast}
		{:quest-text "Flying over colonial Boston"}
		{:quest-text "Flying over the wilderness"}
		{:quest-text "Contemplating an eagle" :speed :fast}
		{:quest-text "Contrasting the lives of natives and Europeans"}
		{:quest-text "Staring at a massive logo" :speed slow}]
	:quests-completed 0
	:state :prologue
	})

(defn generate-quest-line [mode]
	[{:quest-text "Killing someone"}])

(defn update [world progress]
	(let [quests-completed (inc (:quests-completed world))
		remaining-quests (rest (:quest-line world))]
		(if (= progress 100)
				(if (empty? remaining-quests)
					(let [current-state (:state world)
						next-state (rand-nth (current-state states))]
					(assoc world
						:quest-line (generate-quest-line next-state)
						:quests-completed quests-completed))
					(assoc world
						:quests-completed quests-completed
						:quest-line remaining-quests))
		world)))
