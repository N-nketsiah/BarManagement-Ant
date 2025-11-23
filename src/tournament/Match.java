package tournament;

import game.BeloteGame;
import game.GamePlayer;
import pubmanagement.Bar;
import pubmanagement.Client;
import pubmanagement.Human;
import pubmanagement.Beverage;
import java.util.List;

/**
 * Match between two teams, extended with:
 * - Pre-match drinks
 * - Skill-level point bonuses
 * - Popularity rewards
 */
public class Match {

    private Team t1;
    private Team t2;
    private Team winner;
    private Bar bar; // required for serving drinks

    public Match(Team t1, Team t2, Bar bar) {
        this.t1 = t1;
        this.t2 = t2;
        this.bar = bar;
    }

    public void play() {

        System.out.println("\n=======================");
        System.out.println(" MATCH: " + t1.getName() + " vs " + t2.getName());
        System.out.println("=======================\n");

        // 1) Drink before the match
        preMatchDrinks(t1.getPlayers());
        preMatchDrinks(t2.getPlayers());

        // 2) Play Belote game
        BeloteGame game = new BeloteGame();

        for (TournamentPlayer tp : t1.getPlayers()) {
            game.addPlayer(tp.getName(), 1);
        }
        for (TournamentPlayer tp : t2.getPlayers()) {
            game.addPlayer(tp.getName(), 2);
        }

        game.startGame();
        game.playGame();

        // 3) Determine the winning team
        String winningPlayer = game.getWinnerName();
        boolean t1Wins = t1.getPlayers().stream()
                .anyMatch(tp -> tp.getName().equals(winningPlayer));

        if (t1Wins) {
            winner = t1;
            awardWin(t1, t2);
        } else {
            winner = t2;
            awardWin(t2, t1);
        }

        System.out.println("\nWinner of the match: " + winner.getName());
    }

    public Team getWinner() {
        return winner;
    }

    // ------------------------------------------------------------
    // PRE-MATCH DRINKS
    // ------------------------------------------------------------
    private void preMatchDrinks(List<TournamentPlayer> players) {

        for (TournamentPlayer tp : players) {

            Human human = tp.getHuman();

            if (human instanceof Client client) {

                Beverage drink = client.getFavoriteDrink();
                if (bar.getBeverageStock(drink) > 0) {
                    bar.serveDrink(client, drink);
                    System.out.println(" → " + client.getFirstName() +
                            " drinks a " + drink.getName() + " before match.");
                }
            }
        }
    }

    // ------------------------------------------------------------
    // WINNER GETS POINTS + POPULARITY BONUS
    // ------------------------------------------------------------
    private void awardWin(Team winner, Team loser) {

        int basePoints = 10;

        // Add bonus points based on skill level
        for (TournamentPlayer tp : winner.getPlayers()) {
            switch (tp.getSkillLevel()) {

                case BEGINNER -> basePoints += 0;

                case INTERMEDIATE -> basePoints += 2;

                case ADVANCED -> basePoints += 4;

                case EXPERT -> basePoints += 6;
            }
        }

        // Apply points
        winner.addWinPoints(basePoints);
        loser.addLoss();

        // Popularity reward
        for (TournamentPlayer tp : winner.getPlayers()) {

            Human h = tp.getHuman();
            h.increasePopularity(1);

            // Experts get double popularity reward
            if (tp.getSkillLevel() == utils.SkillLevel.EXPERT) {
                h.increasePopularity(1);
            }
        }
    }
}
