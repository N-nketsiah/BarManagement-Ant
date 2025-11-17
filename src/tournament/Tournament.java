
package tournament;

/**
 *
 * @author NAOMI
 */

import java.util.ArrayList;
import java.util.Collections;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * TOURNAMENT CLASS - Manages tournament
 */
public class Tournament {
    private String tournamentName;
    private ArrayList<Team> teams;
    private ArrayList<Match> matches;
    private Team champion;
    private boolean isRunning;

    /**
     * Constructor
     */
    public Tournament(String tournamentName) {
        this.tournamentName = tournamentName;
        this.teams = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.isRunning = false;
    }

    /**
     * Register team
     */
    public void registerTeam(Team team) throws Exception {
        if (isRunning) {
            throw new Exception("Cannot register teams while tournament running");
        }
        teams.add(team);
        System.out.println(team.getTeamName() + " registered");
    }

    /**
     * Start tournament
     */
    public void startTournament() throws Exception {
        if (teams.size() < 2) {
            throw new Exception("Need at least 2 teams");
        }
        isRunning = true;
        System.out.println("\n========== " + tournamentName + " STARTED ==========");
        System.out.println("Teams: " + teams.size() + "\n");
        playTournament();
    }

    /**
     * Play tournament matches
     */
    private void playTournament() throws Exception {
        for (int i = 0; i < teams.size() - 1; i += 2) {
            Team team1 = teams.get(i);
            Team team2 = teams.get(i + 1);

            Match match = new Match(team1, team2);
            match.playMatch();
            matches.add(match);
        }

        finishTournament();
    }
    /**
 * Save tournament to file
 */
public void saveTournamentToFile() {
    try {
        Files.createDirectories(Paths.get("data"));
    } catch (IOException e) {
        System.out.println("Error creating data directory: " + e.getMessage());
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/tournaments.csv", true))) {
        String teamsList = teams.stream()
            .map(Team::getTeamName)
            .reduce((a, b) -> a + "|" + b)
            .orElse("Unknown");

        String line = String.format("%s,%s,%d,%d,%s",
            tournamentName,
            champion != null ? champion.getTeamName() : "TBD",
            teams.size(),
            matches.size(),
            new java.util.Date());
        
        writer.write(line);
        writer.newLine();
        System.out.println(" Tournament saved to file");
    } catch (IOException e) {
        System.out.println("Error saving tournament: " + e.getMessage());
    }
}

    /**
     * Finish tournament
     */
    private void finishTournament() {
        isRunning = false;

        ArrayList<Team> standings = new ArrayList<>(teams);
        Collections.sort(standings, (t1, t2) -> t2.getTotalScore() - t1.getTotalScore());

        champion = standings.get(0);

        System.out.println("\n========== " + tournamentName + " FINISHED ==========");
        printStandings(standings);
        System.out.println("\n CHAMPION: " + champion.getTeamName() + 
                          " (Score: " + champion.getTotalScore() + ")\n");
    }

    /**
     * Print standings
     */
    public void printStandings(ArrayList<Team> standings) {
        System.out.println("FINAL STANDINGS:");
        for (int i = 0; i < standings.size(); i++) {
            Team t = standings.get(i);
            System.out.println((i+1) + ". " + t.getTeamName() + " - " + t.getTotalScore() + " points");
        }
    }

    public ArrayList<Team> getTeams() {
        return new ArrayList<>(teams);
    }

    public ArrayList<Match> getMatches() {
        return new ArrayList<>(matches);
    }

    public Team getChampion() {
        return champion;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    @Override
    public String toString() {
        return tournamentName + " [Teams: " + teams.size() + ", Matches: " + matches.size() + "]";
    }
}
