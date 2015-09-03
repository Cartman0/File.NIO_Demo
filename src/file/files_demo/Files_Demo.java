/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.files_demo;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

/**
 * Files_Demo
 * @author nabana
 * https://docs.oracle.com/javase/jp/8/docs/api/java/nio/file/Files.html
 * http://docs.oracle.com/cd/E26537_01/tutorial/essential/io/fileio.html
 */
public class Files_Demo {
    public static void main(String[] args){
        try {
            Path path = Paths.get("src\\file\\files_demo\\directory");
            System.out.println(path);
            
             // createDirectory  
            if (!Files.exists(path)){
                Files.createDirectory(path);
            }
            
            // createFile            
            if (!Files.exists(Paths.get(path.toString(), "file.txt"))){
                Files.createFile(Paths.get(path.toString(), "file.txt"));
            }
            
            // ハードリンクの作成
            if (!Files.exists(Paths.get(path.toString(), "hardlink.txt"))){
                Files.createLink(Paths.get(path.toString(), "hardlink.txt"), Paths.get(path.toString(), "file.txt")); 
            }
            
            //write 
            Files.write(Paths.get(path.toString(), "hardlink.txt"), "link file content".getBytes());
            
            /* シンボリックリンクの作成
            *  targetは絶対パスでないといけない
            * 循環参照で無限ループになる危険がある
            */
            System.out.println("\nmakeSymbolicLink");
            // directory
            Path absolute_symbolic_src_path = Paths.get("build").toAbsolutePath();
            Path absolute_symbolic_dst_path = Paths.get("symbolic_build_directory");
            if(!Files.exists(absolute_symbolic_dst_path)){
                Files.createSymbolicLink(absolute_symbolic_dst_path, absolute_symbolic_src_path);
            }
            
            // file
            Path absolute_path = path.toAbsolutePath();
            if(!Files.exists(Paths.get(absolute_path.toString(), "symbolic.txt"))){
                Files.createSymbolicLink(Paths.get(absolute_path.toString(), "symbolic.txt"), Paths.get(absolute_path.toString(), "file.txt"));
            }
            
            // symbolic linkかどうか
            if (Files.isSymbolicLink(absolute_symbolic_dst_path)){
                Path p = Files.readSymbolicLink(absolute_symbolic_dst_path);
                System.out.println("\n readSymbolicLink: " + p);
            }else{
                System.out.println("\n No Symboliclink");
            }
            
            //temporary directory, file(一時ファイル) の作成
            // 接頭辞にtemp
            System.out.println("\nmake Temporary directory, file");
            Path temp_directory = Files.createTempDirectory(path, "temp");
            
            Path p_temp_file = Files.createTempFile(temp_directory, "temp-test", null);
            // closeしたらdelete       
            try ( OutputStream os = Files.newOutputStream(p_temp_file, StandardOpenOption.DELETE_ON_CLOSE)) {
                //...
                os.write("temp".getBytes());
            }
            System.out.println("temp_directory: " + temp_directory);
            System.out.println("temp_file: " + p_temp_file);
            Files.deleteIfExists(temp_directory);
            
            /*            
            * copy
            * REPLACE_EXISTING で上書き
            */  
            System.out.println("\n copy");
            Path copy_src = Paths.get(path.toString(), "file.txt");
            Path copy_dst = Paths.get(path.toString(), "file1.txt");
            Files.copy(copy_src, copy_dst, StandardCopyOption.REPLACE_EXISTING);
            
            // move, rename
            System.out.println("\n move, rename");
            Path rename_src = copy_dst;
            Path rename_dst = Paths.get(path.toString(), "rename_file1.txt");
            // rename            
            Files.move(rename_src, rename_dst, StandardCopyOption.REPLACE_EXISTING);
            
            /* move
             pathディレクトリの一段上に移動
            */
            Path newDir = path.getParent();
            System.out.println(newDir);
            Files.move(rename_dst, newDir.resolve(rename_dst.getFileName()), StandardCopyOption.REPLACE_EXISTING);//resolveで結合
            
            // ファイル探索walk
            System.out.println("\nwalk:");
            Files.walk(Paths.get("src\\file")).map(p -> p.getFileName().toString()).forEach(System.out::println);
           
        } catch (Exception ex) {
           ex.printStackTrace();
        }
        
    }
}
