package tournament;

import java.util.ArrayList;

/**
 * Team of 2 players for a Belote tournament
 */
public class Team {

    private String name;
    private ArrayList<TournamentPlayer> players = new ArrayList<>();
    private int score;

    public Team(String name) {
        this.name = name;
        this.score = 0;
    }

    public void addPlayer(TournamentPlayer p) {
        if (players.size() < 2) players.add(p);
    }

    public ArrayList<TournamentPlayer> getPlayers() {
        return players;
    }

    public String getName() {
        return name;
    }

    public void addWinPoints(int pts) {
        score += pts;
        for (int i = 0; i < players.size(); i++) {
            players.get(i).recordPlayed();
            players.get(i).recordWin();
        }
    }

    public void addLoss() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).recordPlayed();
        }
    }

    public int getScore() {
        return score;
    }

    public String toString() {
        return name + " (Score: " + score + ")";
    }
}
