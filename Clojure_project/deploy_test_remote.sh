sudo scp -r -i /home/jervelund/.ssh/id_rsa /mnt/f/OneDrive/Dokumenter/GitHub/thesis/Clojure_project/ jervelund@20.67.241.178:
sudo ssh -i /home/jervelund/.ssh/id_rsa jervelund@20.67.241.178 "cd Clojure_project && lein test"