/** Represents a social network. The network has users, who follow other users.
 * Each user is an instance of the User class. */
public class Network {

    // Fields
    private User[] users;  // the users in this network (an array of User objects)
    private int userCount; // actual number of users in this network

    /** Creates a network with a given maximum number of users. */
    public Network(int maxUserCount) {
        this.users = new User[maxUserCount];
        this.userCount = 0;
    }

    /** Creates a network with some users for testing. */
    public Network(int maxUserCount, boolean gettingStarted) {
        this(maxUserCount);
        users[0] = new User("Foo");
        users[1] = new User("Bar");
        users[2] = new User("Baz");
        userCount = 3;
    }

    public int getUserCount() {
        return this.userCount;
    }

    /** Finds in this network, and returns, the user that has the given name. */
    public User getUser(String name) {
        for (int userIdx = 0; userIdx < this.userCount; userIdx++)
        {
            if (this.users[userIdx].getName().equals(name)){
                return this.users[userIdx];
            }
        }
        return null;
    }

    /** Adds a new user with the given name to this network. */
    public boolean addUser(String name) {
        if (this.userCount == this.users.length){
            return false;
        }
        if (this.getUser(name) != null){
            return false;
        }
        this.users[this.userCount] = new User(name);
        this.userCount = this.userCount + 1;
        return true;
    }

    /** Makes the user with name1 follow the user with name2. */
    public boolean addFollowee(String name1, String name2) {    
        User follower = this.getUser(name1);
        User target = this.getUser(name2);
        
        if (follower == null || target == null){
            return false;
        }
        if (name1.equals(name2))
            return false;  

        return follower.addFollowee(name2);
    }
    
    /** Recommends another user to follow based on mutual followees. */
    public String recommendWhoToFollow(String name) {
        User requester = this.getUser(name);
        if (requester == null)
            return null;

        User bestSuggestion = null;
        int highestMutualCount = -1;

        for (int i = 0; i < this.userCount; i++){
            User potentialMatch = this.users[i];

            // Cannot recommend the user to themselves
            if (potentialMatch == requester){
                continue;
            }
            // Cannot recommend someone the user already follows
            if (requester.follows(potentialMatch.getName())){
                continue;
            }

            int currentMutualScore = requester.countMutual(potentialMatch);
            if (currentMutualScore > highestMutualCount){
                highestMutualCount = currentMutualScore;
                bestSuggestion = potentialMatch;
            }
        }

        return (bestSuggestion != null) ? bestSuggestion.getName() : null;
    }

    /** Returns the name of the most popular user in this network. */
    public String mostPopularUser() {
        if (this.userCount == 0)
            return null;

        User leadUser = this.users[0];
        int maxFollowerCount = this.followeeCount(this.users[0].getName());

        for (int i = 1; i < this.userCount; i++){
            int currentFollowerCount = this.followeeCount(this.users[i].getName());
            if (maxFollowerCount < currentFollowerCount){
                maxFollowerCount = currentFollowerCount;
                leadUser = this.users[i];
            }
        }
        return leadUser.getName();
    }

    /** Returns the number of times that the given name appears in follows lists. */
    private int followeeCount(String name) {
        int occurrenceTotal = 0;
        for (int i = 0; i < this.userCount; i++)
        {
            // A user doesn't count as their own follower in this context
            if (this.users[i].getName().equals(name)){
                continue;
            }
            if (this.users[i].follows(name)){
                occurrenceTotal++;
            }
        }
        return occurrenceTotal;
    }

    /** Returns a textual description of the network. */
    public String toString() {
        if (this.userCount == 0)
            return "Network:";

        String report = "Network:\n";
        for (int i = 0; i < this.userCount; i++)
        {
            report = report + this.users[i].toString();
            if (i < this.userCount - 1){
                report = report + "\n";
            }
        }
        return report;
    }
}