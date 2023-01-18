package de.dorianignee.aoc.challenges;

import java.util.*;

public class Day7 extends Aoc {
    private List<String> lines;
    private Directory rootDirectory;
    private Directory currentDirectory;
    private Stack<Directory> subDirectories;
    private List<Directory> allDirectories;

    /**
     * Today, we have to build a file system tree and find the sum of all directories
     * that have a size less than 100 kB
     */
    @Override
    public int challenge1() {
        buildFileSystem();

        // return sum of file sizes in directories with less than 100_000 bytes total size
        return allDirectories.stream()
                             .filter(dir -> dir.getSize() <= 100_000)
                             .mapToInt(dir -> dir.getSize())
                             .sum();
    }

    /**
     * The second task is to find a directory that frees up enough space, so the update can be installed
     * The total disk size is 70_000_000 bytes and the update needs 30_000_000 bytes
     */
    @Override
    public int challenge2() {
        buildFileSystem();

        int freeSpace = 70_000_000 - rootDirectory.getSize();
        int deleteBytes = 30_000_000 - freeSpace;

        // return the smallest directory that leaves enough space for the update, when deleted
        return allDirectories.stream()
                             .mapToInt(dir -> dir.getSize())
                             .filter(size -> size >= deleteBytes)
                             .min()
                             .getAsInt();
    }

    private void buildFileSystem() {
        lines = lines().toList();
        rootDirectory = new Directory("/");
        currentDirectory = rootDirectory;
        subDirectories = new Stack<>();
        allDirectories = new LinkedList<>();

        // parse lines and build filesystem
        for (String line: lines) {
            String[] words = line.split(" ");
            switch (words[0]) {
                case "$" -> {
                    if (words[1].equals("cd")) {
                        changeDir(words[2]);
                    }
                    // "ls" can be ignored
                }
                case "dir" -> {
                    // add directory to current directory
                    Directory directory = new Directory(words[1]);
                    currentDirectory.addChild(directory); 
                    allDirectories.add(directory);
                }
                default -> currentDirectory.addChild(new File(Integer.parseInt(words[0]),words[1])); // add file to current directory
            }
        }
    }

    private void changeDir(String dirName) {
        switch (dirName) {
            case ".." -> currentDirectory = subDirectories.pop(); // go one directory up
            case "/" -> {
                // go to root directory
                currentDirectory = rootDirectory;
                subDirectories.clear();
            }
            default -> {
                // go to subdirectory with that name
                subDirectories.push(currentDirectory);
                currentDirectory = currentDirectory.changeDir(dirName);
            }
        }
    }

    static abstract class FileSystemItem {
        protected final String name;
        
        public FileSystemItem(String name) {
            this.name = name;
        }

        public abstract boolean isDirectory();

        public String getName() {
            return this.name;
        }

        public abstract int getSize();
    }

    static class File extends FileSystemItem {
        private final int size;

        public File(int size, String name){
            super(name);
            this.size = size;
        }

        @Override
        public boolean isDirectory() {
            return false;
        }

        @Override
        public int getSize() {
            return this.size;
        }
    }

    static class Directory extends FileSystemItem {
        private List<FileSystemItem> children = new LinkedList<>();

        public Directory(String name) {
            super(name);
        }        
        
        @Override
        public boolean isDirectory() {
            return true;
        }

        @Override
        public int getSize() {
            return children.stream()
                           .mapToInt(FileSystemItem::getSize)
                           .sum();
        }

        public void addChild(FileSystemItem child) {
            children.add(child);
        }

        public Directory changeDir(String dirName) {
            return (Directory) children.stream()
                                       .filter(item -> item.isDirectory() && item.getName().equals(dirName))
                                       .findAny()
                                       .get();
        }
    }
}
