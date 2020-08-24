package Lab4;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 马洪升 20175188
 *
 * @date 2018.12.25
 *
 * @version 1.1
 *
 * This is a directory class and it extends storage unit.
 * Besides the attributes in storage unit, it also has directory type("d"), file and directory amount,
 * two sub map one stores file, another stores directory.
 *
 */
public class Directory extends StorageUnit {
    private String directoryType;
    private int amountOfthis;
    private Map<String, Files> subFileMap;
    private Map<String, Directory> subDirectoryMap;

    public Directory(int ownerAccess, int groupAccess, int publicAccess, String user, String groupUser, int size, String time, String unitName, Directory father) {
        super(ownerAccess, groupAccess, publicAccess, user, groupUser, size, time, unitName, father);
        this.directoryType = "d";
        this.amountOfthis = 2;
        this.subFileMap = new HashMap<>();
        this.subDirectoryMap = new HashMap<>() ;
    }

    public Map<String, Files> getSubFileMap() {
        return subFileMap;
    }

    public void setSubFileMap(Map<String, Files> subFileMap) {
        this.subFileMap = subFileMap;
    }

    public Map<String, Directory> getSubDirectoryMap() {
        return subDirectoryMap;
    }

    public void setSubDirectoryMap(Map<String, Directory> subDirectoryMap) {
        this.subDirectoryMap = subDirectoryMap;
    }

    public int getAmountOfthis() {
        return amountOfthis;
    }

    public void setAmountOfthis(int amountOfthis) {
        this.amountOfthis = amountOfthis;
    }

    /**
     * Override a toString method. It will return the details of this directory.
     * @return
     */
    @Override
    public String toString() {
        return directoryType + judgeAccess(getOwnerAccess()) + judgeAccess(getGroupAccess()) + judgeAccess(getPublicAccess()) + "\t" +
                getAmountOfthis() + "\t" + getUser() + "\t" + getGroupUser() + "\t" + getSize() + "\t" + getTime() + "\t" + getUnitName();
    }
}
