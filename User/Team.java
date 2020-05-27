package User;

import java.util.ArrayList;

/**
 * Team class that holds all the team attribute and team members attribute
 */
public class Team {

    /**
     * team name
     */
    private String teamName;
    /**
     * arraylist of team members of that team
     */
    private ArrayList<TeamMember> members;
    /**
     * favouriteTeam, a boolean to indicate whether this is the favourite team or not
     */
    private boolean favouriteTeam = false;

    /**
     * team constructor for initialising attributes such as team name, members as well as setting the favourite team to false unitll
     * the player chooses to change it to true
     */
    public Team() {
        teamName = "";
        members = new ArrayList<>();
        favouriteTeam = false;
    }

    /**
     * team member class holds the names and emails of each team member
     */
    public class TeamMember {

        /**
         * name of the member
         */
        private String name;

        /**
         * email of the member
         */
        private String email;

        /**
         * team member constructor
         * @param name to initialise name
         * @param email to initialise email
         */
        public TeamMember(String name, String email) {
            this.name = name;
            this.email = email;
        }

        /**
         * @return getter for member name
         */
        public String getName() {
            return this.name;
        }

        /**
         * @param name setter for member name
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * @return getter for member email
         */
        public String getEmail() {
            return this.email;
        }

        /**
         * @param email setter for member email
         */
        public void setEmail(String email) {
            this.email = email;
        }

        /**
         * @return a string holdiing all the member info such as name and email
         */
        @Override
        public String toString() {
            return "Member Name: " + name + '\n' +
                    "Member e-mail: " + email;
        }


    }

    /**
     * invites a member
     * @param member the member to be invited
     */
    public void invite(TeamMember member) {

    }

    /**
     * @return getter for team name
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @param teamName setter for team name
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * @return getter for team members arraylist
     */
    public ArrayList<TeamMember> getMembers() {
        return this.members;
    }

    /**
     * @return returns whether is that the favoutite team or not
     */
    public boolean isFavouriteTeam() {
        return favouriteTeam;
    }

    /**
     * @param favouriteTeam setter for favoutite team
     */
    public void setFavouriteTeam(boolean favouriteTeam) {
        this.favouriteTeam = favouriteTeam;
    }

    /**
     * @return a string containing the team members attributes within that team
     */
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
