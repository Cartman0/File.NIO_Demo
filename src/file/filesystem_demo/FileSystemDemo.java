/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.filesystem_demo;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * FileSystemDemo
 * @author nabana
 * https://docs.oracle.com/javase/jp/8/docs/api/java/nio/file/FileSystem.html
 * http://itpro.nikkeibp.co.jp/article/COLUMN/20110830/367909/?ST=develop&P=3
 */
public class FileSystemDemo {
    public static void main(String[] args){
        try {
            FileSystem fs_default = FileSystems.getDefault();
            // getFileStoroes
            System.out.println("getFileStores");
            fs_default.getFileStores().forEach(System.out::println);
            
            System.out.println("\ngetRootDirectories");
            fs_default.getRootDirectories().forEach(System.out::println);
            
            // 文字列として表された名前区切り文字を返す
            System.out.println("\ngetSeparator(): " + fs_default.getSeparator());
            
            /* 現在のファイルシステムのPathを取得、 
            * projectフォルダが親になり、それ以上上の階層は辿れない
            */           
            Path getpath_no_filename = fs_default.getPath("");
            Path getpath_parent = fs_default.getPath("").getParent();
            System.out.println("\ngetPath(\"\"): " + getpath_no_filename.toAbsolutePath());
            
            // filesystemが開かれているか            
            System.out.println("\nisOpen: " + fs_default.isOpen());
            
            /* newFilesystem
            もし、[memory]というProvidorがあったら、以下の様なFileSystemを書ける
            Map<String, String> env = new HashMap<>();
            env.put("capacity", "16G");
            env.put("blockSize", "4k");
            FileSystem memory_fs = FileSystems.newFileSystem(URI.create("memory:///?name=logfs"), env);
            memory_fs.getFileStores().forEach(System.out::println);
            */
            
        } catch (Exception ex) {
           ex.printStackTrace();
        }
    }
}
