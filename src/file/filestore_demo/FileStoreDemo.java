/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.filestore_demo;

import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FileStoreDemo
 * @author nabana
 * https://docs.oracle.com/javase/jp/8/docs/api/java/nio/file/FileStore.html
 */
public class FileStoreDemo {
    public static void main(String args[]) {
        try {
            Path path = Paths.get("src\\file\\filestore_demo");
            FileStore fs = Files.getFileStore(path);
            
            // fileStore.type
            System.out.println("fileStore.type: " + fs.type());
            // fileStore.tostroing
            System.out.println("fileStore.toStoring: " + fs.toString());
                      
            // getTotalSpace: ファイル・ストアのサイズをバイトで返す
            System.out.println("getTotalSpace: " + fs.getTotalSpace());
                        
            //getUnallocatedSpace: ファイル・ストア内の未割当てのバイト数を返します。
            System.out.println("getUnallocatedSpace: " + fs.getUnallocatedSpace());
            
            // getUsableSpace(): このファイル・ストア上で、このJava仮想マシンが利用できるバイト数を返します。 
            System.out.println("getUsableSpace: " + fs.getUsableSpace());
             
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
