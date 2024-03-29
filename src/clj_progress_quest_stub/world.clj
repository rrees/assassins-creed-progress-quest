(ns clj-progress-quest-stub.world)

(def speeds {:fast 600 :normal 1000 :slow 1300})

(def states {:prologue [:assassinating :town :wilderness]
	:wilderness [:town]
	:town [:town :assassinating :wilderness]
	:assassinating [:town :wilderness]})

(defn create [] {
	:quest-line [ {:quest-text "Starting the game"}
		{:quest-text "Entering the prologue" :speed :fast}
		{:quest-text "Flying over colonial Boston"}
		{:quest-text "Flying over the wilderness"}
		{:quest-text "Contemplating an eagle" :speed :fast}
		{:quest-text "Contrasting the lives of natives and Europeans"}
		{:quest-text "Staring at a massive logo" :speed :slow}]
	:quests-completed 0
	:state :prologue
	})

(defn hide [] (rand-nth ["barrel" "haystack" "cart" "shop"]))
(defn person [] (rand-nth ["merchant" "noble" "thug" "captain"]))
(defn wilderness-location [] (rand-nth ["river" "road" "plain" "tribe" "mountain range" "ford"]))
(defn recruit [] (rand-nth ["urchin" "native" "soldier" "sailor" "revolutionary"]))

(def quest-generators {
	:assassinating (fn [] [{:quest-text "Agreeing to kill someone"}
		{:quest-text "Following someone around"}
		{:quest-text "Checking the situation out"}
		{:quest-text (str "Killing a " (person)) :speed :fast}
		{:quest-text "Running from guards" :speed :fast}
		{:quest-text (str "Hiding in a " (hide)) :speed :slow}
		(rand-nth [{:quest-text "Claiming a reward"} {:quest-text "Being betrayed"}])])
	:town (fn [] [ {:quest-text "Entering Boston"}
		(rand-nth [ {:quest-text "Wandering round town"}
		{:quest-text "Performing sedition" :speed :slow}
		{:quest-text "Upgrading equipment"}
		{:quest-text "Gossiping with town's people" :speed :fast}
		{:quest-text "Falling off a roof"}
		{:quest-text (str "Recruiting a " (recruit))}
		{:quest-text "Accidentally stabbing someone instead of talking to them"}])])
	:wilderness (fn [] [
			{:quest-text "Entering the wilderness"}
			{:quest-text (str "Crossing a " (wilderness-location))}
			(rand-nth [{:quest-text "Speaking with elders"}
				{:quest-text "Brooding" :speed :slow}
				{:quest-text "Making a clumsy philosophical point" :speed :fast}
				{:quest-text "Getting lost"}])])})

(defn generate-quest-line [mode]
	((mode quest-generators)))

(defn update [world progress]
	(let [quests-completed (inc (:quests-completed world))
		remaining-quests (rest (:quest-line world))]
		(if (= progress 100)
				(if (empty? remaining-quests)
					(let [current-state (:state world)
						next-state (rand-nth (current-state states))]
					(assoc world
						:quest-line (generate-quest-line next-state)
						:quests-completed quests-completed
						:state next-state))
					(assoc world
						:quests-completed quests-completed
						:quest-line remaining-quests))
		world)))
