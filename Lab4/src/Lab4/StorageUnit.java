package Lab4;

/**
 * @author 马洪升 20175188
 *
 * @Date 2018.12.25
 *
 * @version 1.1
 *
 * This is a parent class, I call it storage unit.
 * It has the details like the linux prints.
 */

public class StorageUnit {
    private int ownerAccess;
    private int groupAccess;
    private int publicAccess;
    private String user;
    private String groupUser;
    private int size;
    private String time;
    private String unitName;
    private Directory father = null;

    public StorageUnit(int ownerAccess, int groupAccess, int publicAccess, String user, String groupUser, int size, String time, String unitName, Directory father) {
        this.ownerAccess = ownerAccess;
        this.groupAccess = groupAccess;
        this.publicAccess = publicAccess;
        this.user = user;
        this.groupUser = groupUser;
        this.size = size;
        this.time = time;
        this.unitName = unitName;
        this.father = father;
    }

    public int getOwnerAccess() {
        return ownerAccess;
    }

    public void setOwnerAccess(int ownerAccess) {
        this.ownerAccess = ownerAccess;
    }

    public int getGroupAccess() {
        return groupAccess;
    }

    public void setGroupAccess(int groupAccess) {
        this.groupAccess = groupAccess;
    }

    public int getPublicAccess() {
        return publicAccess;
    }

    public void setPublicAccess(int publicAccess) {
        this.publicAccess = publicAccess;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroupUser() {
        return groupUser;
    }

    public void setGroupUser(String groupUser) {
        this.groupUser = groupUser;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Directory getFather() {
        return father;
    }

    public void setFather(Directory father) {
        this.father = father;
    }

    /**
     * We input the access number and then I can convert it to String.
     * @param accessNum
     * @return power
     */
    public String judgeAccess(int accessNum){
        String power = null;
        switch (accessNum){
            case 7:{
                power = "rwx";break;
            }
            case 6:{
                power = "rw-";break;
            }
            case 5:{
                power = "r-x";break;
            }
            case 4:{
                power = "r--";break;
            }
            case 3:{
                power = "-wx";break;
            }
            case 2:{
                power = "-w-";break;
            }
            case 1:{
                power = "--x";break;
            }
            case 0:{
                power = "---";break;
            }
        }
        return power;
    }
}
