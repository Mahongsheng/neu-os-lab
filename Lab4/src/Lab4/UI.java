package Lab4;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

/**
 * This is a user interface. We assume that you are the owner, you have the highest access power.
 */
public class UI {
    public static void main(String[] args) {
        try{
            // Begin!
            FileSystem fileSystem = new FileSystem();
            fileSystem.pointer = fileSystem.root;
            menu(fileSystem);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void menu(FileSystem fileSystem) {
        Scanner input = new Scanner(System.in);
        String str = null;
        System.out.println("***********" + "Welcome to use the file simulation operating system" + "***********");
        System.out.println();
        fileSystem.showAll(fileSystem.root);

        System.out.println("Please enter command line（Enter help to view the command table）：");
        while ((str = input.nextLine()) != null) {
            if (str.equals("exit")) {
                System.out.println("Thank you！");
                break;
            }
            // Use this list to store the command that has been split.
            String[] strs = editStr(str);
            switch (strs[0]) {
                case "pwd":{
                    if (strs.length > 2){
                        System.out.println("Invalid command. Please check it.");
                    }else {
                        fileSystem.getPath(fileSystem.pointer);
                    }
                    break;
                }
                case "cd":{
                    if (strs.length < 2) {
                        System.out.println("Invalid command. Please check it.");
                    }
                    else {
                        fileSystem.enterNextDirectory(fileSystem.pointer, strs[1]);
                    }
                    break;
                }
                case "cd..":{
                    if (strs.length > 2){
                        System.out.println("Invalid command. Please check it.");
                    }else {
                        fileSystem.enterBackDirectory(fileSystem.pointer);
                    }
                    break;
                }
                case "ls":{
                    if (strs.length <= 1){
                        fileSystem.showFile(fileSystem.pointer);
                    }
                    else if (strs[1].equals("-l")){
                        fileSystem.showAll(fileSystem.pointer);
                    }else {
                        System.out.println("Invalid command. Please check it.");
                    }
                    break;
                }
                case "mkdir":{
                    if (strs.length < 2) {
                        System.out.println("Invalid command. Please check it.");
                    }
                    else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy h:m:s aa", Locale.ENGLISH);
                        Date time = new Date();
                        fileSystem.createDirectory(strs[1], dateFormat.format(time), fileSystem.pointer);
                    }
                    break;
                }
                case "rmdir":{
                    if (strs.length < 2) {
                        System.out.println("Invalid command. Please check it.");
                    }
                    else {
                        fileSystem.removeDirectory(fileSystem.pointer, strs[1]);
                    }
                    break;
                }

                case "touch":{
                    if (strs.length < 2) {
                        System.out.println("Invalid command. Please check it.");
                    }
                    else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d, yyyy h:m:s aa", Locale.ENGLISH);
                        Date time = new Date();
                        // We assume that the file is 256 KB.
                        fileSystem.createFile(strs[1],256, dateFormat.format(time), fileSystem.pointer);
                    }
                    break;
                }
                case "rm":{
                    if (strs.length < 2) {
                        System.out.println("Invalid command. Please check it.");
                    }
                    else {
                        fileSystem.removeFiles(fileSystem.pointer, strs[1]);
                    }
                    break;
                }
                case "chmod":{
                    if (strs.length < 3) {
                        System.out.println("Invalid command. Please check it.");
                    }
                    else {
                        fileSystem.chMod(fileSystem.pointer, strs[1], strs[2]);
                    }
                    break;
                }
                case "help": {
                    System.out.println();
                    System.out.println("The commands are as follows（Space cannot be omitted）：");
                    System.out
                            .println("pwd");
                    System.out.println("<View the current path, for instance: pwd>");
                    System.out.println();
                    System.out
                            .println("cd DirectoryName");
                    System.out.println("<Open a directory, for instance: cd myDirectory>");
                    System.out.println();
                    System.out
                            .println("cd..");
                    System.out.println("<Back to the parent directory, for instance: cd..>");
                    System.out.println();
                    System.out
                            .println("ls");
                    System.out.println("<Show all the storage unit in this directory, for instance： ls>");
                    System.out.println();
                    System.out
                            .println("ls -l");
                    System.out.println("<Show the details of all the storage units, for instance： ls -l>");
                    System.out.println();
                    System.out.println("mkdir DirectoryName");
                    System.out.println("<Create a directory, for instance： mdkir newDirectory>");
                    System.out.println();
                    System.out.println("rmdir DirectoryName");
                    System.out.println("<Remove a directory, for instance： rmdir myDirectory");
                    System.out.println();
                    System.out
                            .println("touch FileName");
                    System.out.println("<Create a file, for instance：touch newFile >");
                    System.out.println();
                    System.out
                            .println("rm FileName");
                    System.out.println("<Remove a file, for instance： rm myfile>");
                    System.out.println();
                    System.out
                            .println("chmod AccessPower FileName|DirectoryName");
                    System.out.println("<AccessPower is three numbers: 751 -->  rwxr-x--x>");
                    System.out.println("<Change the access permission of a storage unit, for instance： chmod 700 myFile>");
                    System.out.println();
                    break;
                }
                default:
                    for(String st : strs)
                        System.out.println(st);
                    System.out.println("Invalid command. Please check it.");
            }
            System.out.println("Please enter command line（Enter help to view the command table）：");
        }
    }

    /**
     * We use this method to split the command.
     * @param str
     * @return
     */
    public static String[] editStr(String str) {
        String[] cammand = str.split(" ");
        return cammand;
    }

}
