package User;

import java.util.ArrayList;

public class Team {

    private String teamName;
    private ArrayList<TeamMember> members;
    private boolean favouriteTeam = false;

    public Team() {
        teamName = "";
        members = new ArrayList<>();
        favouriteTeam = false;
    }

    protected class TeamMember {

        private String name;

        private String email;

        /**
         * @param name
         * @param email
         */
        public TeamMember(String name, String email) {
            this.name = name;
            this.email = email;
        }

        public String getName() {
            return this.name;
        }

        /**
         * @param name
         */
        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return this.email;
        }

        /**
         * @param email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        @Override
        public String toString() {
            return "Member Name: " + name + '\n' +
                    "Member e-mail: " + email;
        }


    }

    /**
     * @param member
     */
    public void invite(TeamMember member) {

    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<TeamMember> getMembers() {
        return this.members;
    }

    public boolean isFavouriteTeam() {
        return favouriteTeam;
    }

    public void setFavouriteTeam(boolean favouriteTeam) {
        this.favouriteTeam = favouriteTeam;
    }

    @Override
    public String toString() {
        StringBuilder teamInfo = new StringBuilder();
        int i = 1;
        for (TeamMember member : members) {
            teamInfo.append("Member Number ").append(i).append("info:\n").append(member).append('\n');
            i++;
        }
        return teamInfo.toString();
    }
}