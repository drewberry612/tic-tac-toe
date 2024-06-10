In this project, I created an AI bot to play Tic-tac-toe, which used a heuristic function and minimax search to become unbeatable by a human player, only draws are possible. I designed the heuristic function myself to determine a score/value of the given gamestate, which was then passed to the minimax search algorithm to choose the best move. The minimax search algorithm has an early stopping clause of depth 6, so that the algorithm doesn't search every single gamestate until it reaches endgame, so it works in an adequate time for a bot. This also includes alpha-beta pruning.
