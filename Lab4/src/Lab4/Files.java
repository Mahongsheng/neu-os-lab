package Lab4;

/**
 *
 * @author 马洪升 20175188
 *
 * @date 2018.12.25
 *
 * @version 1.1
 *
 * This is a file class and it extends storage unit.
 * Besides the attributes in storage unit, it also has file type("-")
 *
 */
public class Files extends StorageUnit {
    private String fileType;

    public Files(int ownerAccess, int groupAccess, int publicAccess, String user, String groupUser, int size, String time, String unitName, Directory father) {
        super(ownerAccess, groupAccess, publicAccess, user, groupUser, size, time, unitName, father);
        this.fileType = "-";
    }
    /**
     * Override a toString method. It will return the details of this file.
     * @return
     */
    @Override
    public String toString() {
        return fileType + judgeAccess(getOwnerAccess()) + judgeAccess(getGroupAccess()) + judgeAccess(getPublicAccess()) + "\t" +
                "1" + "\t" + getUser() + "\t" + getGroupUser() + "\t" + getSize() + "\t" + getTime() + "\t" + getUnitName();
    }
}