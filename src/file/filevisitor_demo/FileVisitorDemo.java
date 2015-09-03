/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.filevisitor_demo;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * FileVisitorDemo
 * @author nabana
 * http://docs.oracle.com/javase/jp/8/api/java/nio/file/FileVisitor.html
 * http://itpro.nikkeibp.co.jp/article/COLUMN/20110830/367909/?ST=develop&P=6
 */
public class FileVisitorDemo {
    public static void main(String args[]) {
        
        try {
            FileSystem fs = FileSystems.getDefault();
            Path path = fs.getPath("src");
            
            FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    // ファイルに訪問した時にコールされる
                    System.out.println("Visit File: " + file);
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // ディレクトリに入る時にコールされる
                    System.out.println("Pre Visit Dir: " + dir);
                    return FileVisitResult.CONTINUE;
                }
                
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                    // ディレクトリから出る時にコールされる
                    System.out.println("Post Visit Dir: " + dir);
                    return FileVisitResult.CONTINUE;
                }
            };
            
            Files.walkFileTree(path, visitor);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
