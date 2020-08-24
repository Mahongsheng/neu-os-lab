package Lab4;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 马洪升 20175188
 *
 * @Date 2018.12.25
 *
 * @version 1.1
 *
 * This is a file management.
 * I define a directory: root. It will store all the files and directories.
 * It also has methods: createFile, createDirectory, addStorageUnit, removeFiles, removeDirectory,
 *  enterBackDirectory, enterNextDirectory,  getPath, chMod, showFile, showAll.
 */
public class FileSystem {
    // Define a root.
    public static Directory root = new Directory(7,7, 1,"Admin","Admin",1024,"2018-12-25","root",null);
    // Define a pointer.
    public static Directory pointer;

    public FileSystem() {
    }

    /**
     * We can create a file by this method.
     * @param name
     * @param size
     * @param time
     * @param currDirectory
     */
    public void createFile(String name, int size, String time, Directory currDirectory){
        // If the memory is enough, do it.
        if (size <= root.getSize()){
            Files file = new Files(7,6, 1,"Admin","Admin",size,time,name,currDirectory);
            // Reduce rest of the memory size.
            root.setSize(root.getSize() - size);
            // Set current directory's size.
            currDirectory.setSize(size + currDirectory.getSize());
            // Print the detail of the file.
            System.out.println(file.toString());
            // Add it to memory.
            addStorageUnit(currDirectory, file);
        }else {
            System.err.println("There isn't enough memory!");
        }
    }

    /**
     * We can create a directory by this method.
     * @param name
     * @param time
     * @param currDirectory
     */
    public void createDirectory(String name, String time, Directory currDirectory){
        // When initialize a directory, set size 0;
        Directory directory = new Directory(7,6, 1,"Admin","Admin",0,time,name,currDirectory);
        // Print the detail of the directory.
        System.out.println(directory.toString());
        // Add it to memory.
        addStorageUnit(currDirectory, directory);
    }

    /**
     * We add the file or directory into memory.
     * @param currDirectory
     * @param storageUnit
     */
    public void addStorageUnit(Directory currDirectory, StorageUnit storageUnit){
        // Judge the class.
        if (storageUnit instanceof Files){
            Files newFile = (Files)storageUnit;
            // Judge if there is a same one in memory.
            if (currDirectory.getSubFileMap().containsKey(newFile.getUnitName())){
                System.err.println("The same file has existed!");
                return;
            }
            // Store it.
            currDirectory.getSubFileMap().put(newFile.getUnitName(), newFile);
            // Set the amount.
            currDirectory.setAmountOfthis(currDirectory.getAmountOfthis() + 1);
        }else {
            Directory newDirectory = (Directory)storageUnit;
            // Judge if there is a same one in memory.
            if (currDirectory.getSubFileMap().containsKey(newDirectory.getUnitName())){
                System.err.println("The same directory has existed!");
                return;
            }
            // Store it.
            currDirectory.getSubDirectoryMap().put(newDirectory.getUnitName(), newDirectory);
            // Set the amount.
            currDirectory.setAmountOfthis(currDirectory.getAmountOfthis() + 1);
        }
    }

    /**
     * We remove a file by this method.
     * @param currDirectory
     * @param name
     */
    public void removeFiles(Directory currDirectory, String name){
        // Judge if exist.
        if (currDirectory.getSubFileMap().containsKey(name)){
            // Set the rest size of the memory.
            root.setSize(root.getSize() + currDirectory.getSubFileMap().get(name).getSize());
            // Set the amount.
            currDirectory.setAmountOfthis(currDirectory.getAmountOfthis() - 1);
            // Set the current directory size.
            currDirectory.setSize(currDirectory.getSize() - currDirectory.getSubFileMap().get(name).getSize());
            // Remove it.
            currDirectory.getSubFileMap().remove(name);
            System.out.println("Remove successfully!");
        }else {
            System.err.println("The file doesn't exist!");
        }
    }

    /**
     * We remove a directory by this method.
     * @param currDirectory
     * @param name
     */
    public void removeDirectory(Directory currDirectory, String name){
        // Judge if exist.
        if (currDirectory.getSubDirectoryMap().containsKey(name)){
            Directory directory = currDirectory.getSubDirectoryMap().get(name);
            // Judge if the directory is empty, if is, we remove it, or we deny.
            if (directory.getSubDirectoryMap().isEmpty() && directory.getSubFileMap().isEmpty()){
                currDirectory.getSubDirectoryMap().remove(name);
            }else {
                System.err.println("The directory is not empty, you can't remove it!");
            }
        }else {
            System.err.println("The directory doesn't exist!");
        }
    }

    /**
     * We back to parent directory by this method.
     * @param currDirectory
     */
    public void enterBackDirectory(Directory currDirectory){
        // Judge if it is root.
        if(currDirectory.getFather() == null) {
            System.err.println("No superior directory!");
        } else {
            // Back!
            pointer = currDirectory.getFather();
        }
    }

    /**
     * We open next directory by this method.
     * @param currDirectory
     * @param name
     */
    public void enterNextDirectory(Directory currDirectory, String name){
        // Judge if exists.
        if (currDirectory.getSubDirectoryMap().containsKey(name)){
            pointer = currDirectory.getSubDirectoryMap().get(name);
        }
        else {
            System.err.println("The directory doesn't exist! ");
        }
    }

    /**
     * We get the path by this method.
     * @param currDirectory
     */
    public void getPath(Directory currDirectory){
        String path = "";
        // Traverse.
        while (currDirectory.getFather() != null){
            path = "/" + currDirectory.getUnitName() + path;
            currDirectory = currDirectory.getFather();
        }
        path = "/" + currDirectory.getUnitName() + path;
        System.out.println(path);
    }

    /**
     * We use this method to change the access power.
     * @param currDirectory
     * @param newAccess
     * @param name
     */
    public void chMod(Directory currDirectory, String newAccess, String name){
        // Split the number.
        char[] chs = newAccess.toCharArray();
        if (currDirectory.getSubFileMap().containsKey(name)){
            // Set it.
            currDirectory.getSubFileMap().get(name).setOwnerAccess(Integer.parseInt(String.valueOf(chs[0])));
            currDirectory.getSubFileMap().get(name).setGroupAccess(Integer.parseInt(String.valueOf(chs[1])));
            currDirectory.getSubFileMap().get(name).setPublicAccess(Integer.parseInt(String.valueOf(chs[2])));
        }else if (currDirectory.getSubDirectoryMap().containsKey(name)){
            // Set it.
            currDirectory.getSubDirectoryMap().get(name).setOwnerAccess(Integer.parseInt(String.valueOf(chs[0])));
            currDirectory.getSubDirectoryMap().get(name).setGroupAccess(Integer.parseInt(String.valueOf(chs[1])));
            currDirectory.getSubDirectoryMap().get(name).setPublicAccess(Integer.parseInt(String.valueOf(chs[2])));
        }else {
            System.err.println("No such file or directory!");
        }
    }

    /**
     * We show all the files or directory name by this method.
     * @param currDirectory
     */
    public void showFile(Directory currDirectory){
        String fileName = "";
        // Iterate it.
        Iterator iteratorFile = currDirectory.getSubFileMap().entrySet().iterator();
        Iterator iteratorDir = currDirectory.getSubDirectoryMap().entrySet().iterator();
        while (iteratorFile.hasNext()){
            Map.Entry entry = (Map.Entry)iteratorFile.next();
            Files files = (Files) entry.getValue();
            fileName = fileName + files.getUnitName() + "\t";
        }
        while (iteratorDir.hasNext()){
            Map.Entry entry = (Map.Entry)iteratorDir.next();
            Directory directory = (Directory)entry.getValue();
            fileName = fileName + directory.getUnitName() + "\t"    ;
        }
        System.out.println(fileName);
    }

    /**
     * We use this method to show all the things in current directory.
     * Details!
     * @param currDirectory
     */
    public void showAll(Directory currDirectory) {
        System.out.println("***************** < " + currDirectory.getUnitName() + " > *****************");
        // Iterate it.
        Iterator iteratorFile = currDirectory.getSubFileMap().entrySet().iterator();
        Iterator iteratorDir = currDirectory.getSubDirectoryMap().entrySet().iterator();
        while (iteratorFile.hasNext()){
            Map.Entry entry = (Map.Entry)iteratorFile.next();
            Files files = (Files) entry.getValue();
            System.out.println(files.toString());
            System.out.println();
        }
        while (iteratorDir.hasNext()){
            Map.Entry entry = (Map.Entry)iteratorDir.next();
            Directory directory = (Directory)entry.getValue();
            System.out.println(directory.toString());
        }
        System.out.println("Disk Surplus Space: " + root.getSize() + "            " + "Exit the system please enter:exit");
        System.out.println();
    }
}
