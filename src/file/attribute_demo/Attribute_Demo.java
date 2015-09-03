/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.attribute_demo;

import java.nio.file.attribute.FileTime;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.UserPrincipal;

/**
 * Attribute_Demo
 * @author nabana
 * http://docs.oracle.com/javase/jp/8/docs/api/java/nio/file/attribute/package-summary.html
 * http://itpro.nikkeibp.co.jp/article/COLUMN/20110830/367909/?ST=develop&P=5
 */
public class Attribute_Demo {
    public static void main(String[] args){
        try {
            // オーナー
            Path path = Paths.get("src\\file\\attribute_demo", "Attribute_Demo.java");
            System.out.println(path);
            UserPrincipal owner = Files.getOwner(path);
            System.out.println("Owner: " + owner);
                       
            /* 最終更新日時
            * FileTime: ISO 8601で定義されている表記法でYYYY-MM-DDThh:mm:ss[.s+]Z 
            */
            FileTime lastModifiedTime = Files.getLastModifiedTime(path);
            System.out.println("\nLst Modified Time: " + lastModifiedTime);
            
            // 作成日時
            FileTime creationTime = (FileTime)Files.getAttribute(path, "creationTime");
            System.out.println("\nCreation Time: " + creationTime);
            
            
            /*
            * FilesクラスのgetAttribute/setAttributeメソッドを使用すれば、任意のアトリビュートを扱える。
            * しかし、アトリビュート名を文字列で指定しなくてはいけない点、キャストが必要である点などが、気になるところです。
            * これを解決するのがFileAttributeViewインタフェース
            *
            * Windows、Linux、Solarisのいずれでも使用できるのがBasicFileAttributeViewインタフェース
            * Windows特有のアトリビュートを扱うのがDosFileAttributeViewインタフェース、
            * LinuxなどPosixに準拠しているOSで使用できるのがPosixFileAttributeViewインタフェース
            *
            * アクセスコントロールを扱うAclFileAttributeViewインタフェースや、
            * オーナー情報を扱うFileOwnerAttributeViewインタフェース
            */
            
            System.out.println("\nFileAttributeView FileAttributes:");
            // FileAttributeViewオブジェクトを取得
            // getFileAttributeViewメソッドの第2引数で取得するFileAttributeViewインタフェースのサブインタフェースを指定
            BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);

            // アトリビュートを保持するBasicFileAttributesインタフェースの オブジェクトを取得
            BasicFileAttributes attributes = view.readAttributes();

            // 作成日時
            FileTime creTime = attributes.creationTime();
            System.out.println("Creation Time: " + creTime);

            // 最終アクセス日時
            FileTime lastAccessTime = attributes.lastAccessTime();
            System.out.println("Last Access Time: " + lastAccessTime);
            
            FileTime lastModTime = attributes.lastModifiedTime();
            System.out.println("Last Modified Time: " + lastModTime);

            // サイズ
            long size = attributes.size();
            System.out.println("Size: " + size + " byte");

            /* filekey: 指定されたファイルを一意に識別するオブジェクトを返します。ファイル・キーがない場合はnullを返し
            * Windowsでは常にnull
            */
            String filekey = (String)attributes.fileKey();
            System.out.println("fileKey: " + filekey);
            
            // isDirectory()
            System.out.println("Directory: " + attributes.isDirectory());
            
            // isOther()
            System.out.println("Other Type of File: " + attributes.isOther());
            
            // isRegularFile()            
            System.out.println("Regular File: " + attributes.isRegularFile());
            
            // isSymbolicLink()
            System.out.println("Symbolic File: " + attributes.isSymbolicLink());            
        
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
