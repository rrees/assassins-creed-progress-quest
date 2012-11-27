(ns clj-progress-quest-stub.core
	[:use [clj-progress-quest-stub.world :as world]]
	[:import [javax.swing JLabel JPanel JFrame JProgressBar JButton SwingUtilities]])

;; Stolen from Stuart Sierra's blog
(defmacro with-action [component event & body]
	`(. ~component addActionListener
		(proxy [java.awt.event.ActionListener] []
		(actionPerformed [~event] ~@body))))




(defn perform-heroic-action [status-message progress-bar world]
	(let [current-progress (.getValue progress-bar) _ (println current-progress)
		new-world (world/update world current-progress)
		current-quest (->> new-world :quest-line  first)]
	(if (= current-progress 100)
		(do
			(.setValue progress-bar 0)
			(.setText status-message (:quest-text current-quest)))

		(.setValue progress-bar (+ current-progress 10)))
	(Thread/sleep (world/speeds (get current-quest :speed :normal)))
	(recur status-message progress-bar new-world)))

(defn progress-quest []
		(let [status-message (JLabel. "Explore the legacy of the assassins")
		progress-bar (JProgressBar. 0 100)
		start-button (JButton. "Play")
		panel (doto (JPanel.) (. add status-message) (. add progress-bar) (. add start-button))]
	(with-action start-button e
		(do (.setEnabled start-button false)
			(.setText status-message "Starting the game")
			(.setValue progress-bar 0)
			(future (perform-heroic-action status-message progress-bar (world/create)))))
	(doto (JFrame. "Assassin Creed Progress Quest")
		(.setDefaultCloseOperation JFrame/EXIT_ON_CLOSE)
		(.setContentPane panel)
		(.pack)
		(.setVisible true))))

(defn -main [& args]
	(SwingUtilities/invokeLater progress-quest))