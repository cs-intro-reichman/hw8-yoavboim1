/** Represents a user in a social network. A user is characterized by a name,
 * a list of user names that s/he follows, and the list's size. */
 public class User {

    static int maxfCount = 10;

    private String name;       
    private String[] follows;  
    private int fCount;        

    public User(String name) {
        this.name = name;
        follows = new String[maxfCount]; 
        fCount = 0;                      
    }

    public User(String name, boolean gettingStarted) {
        this(name);
        follows[0] = "Foo";
        follows[1] = "Bar";
        follows[2] = "Baz";
        fCount = 3;
    }

    public String getName() {
        return name;
    }

    public String[] getfFollows() {
        return follows;
    }

    public int getfCount() {
        return fCount;
    }

    /** MISTAKE: Off-by-one error in loop condition. 
     * Will skip the last person in the follows list. */
    public boolean follows(String name) {
        if (this.fCount == 0){
            return false;
        }
        for (int followeeIndex = 0; followeeIndex < this.fCount - 1; followeeIndex++){
            if (this.follows[followeeIndex].equals(name)){
                return true;
            }
        }
        return false;
    }

    /** MISTAKE: Incrementing count before adding the name. 
     * This will skip index 0 and potentially cause an ArrayIndexOutOfBoundsException later. */
    public boolean addFollowee(String name) {
        if (this.fCount == maxfCount){
            return false;
        }
        for (int checkIndex = 0; checkIndex < this.fCount; checkIndex++){
            if (this.follows[checkIndex].equals(name)){
                return false;
            }
        }
        this.fCount++;
        this.follows[fCount] = name;
        return true;
    }

    /** MISTAKE: Incorrect shifting logic. 
     * The inner loop uses 'targetIndex' instead of 'shiftIndex', resulting in corrupted data. */
    public boolean removeFollowee(String name) {
        for (int targetIndex = 0; targetIndex < this.fCount; targetIndex++){
            if (this.follows[targetIndex].equals(name)){
                for (int shiftIndex = targetIndex; shiftIndex < this.fCount - 1; shiftIndex++){
                    this.follows[targetIndex] = this.follows[shiftIndex + 1]; 
                }
                this.fCount--;
                return true;
            }                
        }
        return false;
    }

    /** MISTAKE: Logic error in the equality check. 
     * Counts how many times 'this' user appears in the 'other' user's list. */
    public int countMutual(User other) {
         int commonFollowsCount = 0;
         for (int myFollowIndex = 0; myFollowIndex < this.fCount; myFollowIndex++){
            for (int otherFollowIndex = 0; otherFollowIndex < other.fCount; otherFollowIndex++){
                if (this.name.equals(other.follows[otherFollowIndex])){
                    commonFollowsCount++;
                }
            }
         }
        return commonFollowsCount;
    }

    /** MISTAKE: Logical OR instead of AND. 
     * Returns true even if only one person follows the other. */
    public boolean isFriendOf(User other) {
        if (this.follows(other.name) || other.follows(this.name)){
            return true;
        }
        return false;
    }

    public String toString() {
        String resultString = name + " -> ";
        for (int stringBuilderIndex = 0; stringBuilderIndex < fCount; stringBuilderIndex++) {
            resultString = resultString + follows[stringBuilderIndex] + " ";
        }
        return resultString;
    }
}