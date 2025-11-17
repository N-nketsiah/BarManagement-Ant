package tournament;

import game.BeloteGame;
import game.GamePlayer;

public class Match {
    private int matchId;
    private static int idCounter = 1;
    private Team team1;
    private Team team2;
    private Team winner;
    private BeloteGame game;
    private boolean isPlayed;

    public Match(Team team1, Team team2) {
        this.matchId = idCounter++;
        this.team1 = team1;
        this.team2 = team2;
        this.isPlayed = false;
    }

    public void playMatch() throws Exception {
        game = new BeloteGame();

        // Add players by name and team
        for (GamePlayer player : team1.getPlayers()) {
            game.addPlayer(player.getPlayerName(), 1);
        }
        for (GamePlayer player : team2.getPlayers()) {
            game.addPlayer(player.getPlayerName(), 2);
        }

        game.startGame();
        game.playRound();

        winner = team1;
        isPlayed = true;
    }

    public Team getWinner() {
        return winner;
    }

    public int getMatchId() {
        return matchId;
    }

    public boolean isPlayed() {
        return isPlayed;
    }
}