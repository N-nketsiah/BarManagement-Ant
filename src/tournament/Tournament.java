package tournament;

import pubmanagement.Bar;
import java.util.ArrayList;
import java.util.Collections;

public class Tournament {

    private String name;
    private ArrayList<Team> teams = new ArrayList<>();
    private boolean started = false;
    private Bar bar;   // <-- NEW

    public Tournament(String name, Bar bar) {
        this.name = name;
        this.bar = bar;
    }

    public void addTeam(Team t) throws Exception {
        if (started) throw new Exception("Tournament already started.");
        teams.add(t);
    }

    public ArrayList<Team> getTeams() {
        return teams;
    }

    public void start() throws Exception {
        if (teams.size() < 2)
            throw new Exception("Need at least 2 teams");

        started = true;

        System.out.println("\n========== " + name + " STARTED ==========\n");

        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                Match m = new Match(teams.get(i), teams.get(j), bar);
                m.play();
            }
        }

        finish();
    }

    private void finish() {
        Collections.sort(teams, (a, b) -> b.getScore() - a.getScore());

        System.out.println("FINAL STANDINGS:");
        for (int i = 0; i < teams.size(); i++) {
            System.out.println((i + 1) + ". " + teams.get(i));
        }

        System.out.println("\nCHAMPION: " + teams.get(0).getName());
    }
}
