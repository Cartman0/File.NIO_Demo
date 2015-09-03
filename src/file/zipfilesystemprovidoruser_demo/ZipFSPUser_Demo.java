/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.zipfilesystemprovidoruser_demo;

import java.net.URI;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

/**
 * ZipFSPUser_Demo
 * @author nabana
 * http://docs.oracle.com/javase/jp/8/docs/technotes/guides/io/fsp/zipfilesystemprovider.html
 * Zip のFileSystemをつかうサンプル
 * Zipファイル・システム・プロバイダは、ZipまたはJARファイルを1つのファイル・システムとして扱い、そのファイルの内容を操作する機能を提供
 */
public class ZipFSPUser_Demo {
    static final String ZIP_FILE_NAME = "zipfs";
    static final Path ZIP_FILE_PATH = FileSystems.getDefault().getPath("src/file/zipfilesystemprovidoruser_demo");
    static final URI uri = URI.create("jar:" + ZIP_FILE_PATH.toAbsolutePath().toUri() + ZIP_FILE_NAME + ".zip"); 
            
    public static void main(String[] args) {
        Map<String, String> env = new HashMap<>();
        env.put("create", "true");
        
        System.out.println(uri);
        try (FileSystem zipfs = FileSystems.newFileSystem(uri, env, null)){
            
            System.out.println("getRootDirectories():");
            zipfs.getRootDirectories().forEach(System.out::println);
            
            System.out.println("\ngetSeparator(): " + zipfs.getSeparator());
            
            for(FileStore filestore : zipfs.getFileStores()){
                System.out.println("name: " + filestore.name());
                System.out.println("type: " + filestore.type());
                System.out.println("getTotalSpace(): " + filestore.getTotalSpace());
            }
            
            Path zipfs_path = zipfs.getPath(ZIP_FILE_NAME);
            System.out.println("\ngetPath(\"\").toAbsolute: " + zipfs_path.toAbsolutePath());
       
            // write
            // localのfilesystem
            FileSystem fs = FileSystems.getDefault();
            Path src = fs.getPath("src\\file\\zipfilesystemprovidoruser_demo\\ZipFSPUser_Demo.java");
            System.out.println("src: " + src.toAbsolutePath());
            
            Path dst = zipfs.getPath(ZIP_FILE_NAME + "/" + src.getFileName());
            System.out.println("dst: " + dst);
            Files.copy(src, dst, StandardCopyOption.REPLACE_EXISTING);
            
            System.out.println("\nwalk:");
            Files.walk(zipfs_path).map(p -> p.getFileName().toString()).forEach(System.out::println);
            
            zipfs.close();
           
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
