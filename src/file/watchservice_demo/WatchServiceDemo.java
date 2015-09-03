/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package file.watchservice_demo;

import java.nio.file.Path;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;

import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.nio.file.WatchKey;

/**
 * WatchServiceDemo
 * @author nabana
 * ディレクトリを監視する
 *  Path dest = fs.getPath("src\\file");直下のファイルを監視する
 * https://docs.oracle.com/javase/jp/8/docs/api/java/nio/file/WatchService.html
 * http://itpro.nikkeibp.co.jp/article/COLUMN/20110830/367909/?ST=develop&P=7
 */
public class WatchServiceDemo {
    public static void main (String[] args){
        try {
            FileSystem fs = FileSystems.getDefault();
            Path dest = fs.getPath("src\\file");
 
            // WatchServiceオブジェクトの生成
            WatchService watcher = fs.newWatchService();
            
            // ディレクトリにWatchServiceオブジェクトを登録
            dest.register(watcher, 
                    StandardWatchEventKinds.ENTRY_CREATE, 
                    StandardWatchEventKinds.ENTRY_DELETE, 
                    StandardWatchEventKinds.ENTRY_MODIFY);
            
            System.out.println("Watching");
            
            while(true) {
                WatchKey key = null;
 
                // 現在のイベントを取得
                try {
                    key = watcher.take();
                } catch (InterruptedException ex) {
                    break;
                }
                 
                for (WatchEvent<?> event: key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                        // イベントがオーバフローしてしまった場合
                        continue;
                    } else {
                        // それ以外の場合、
                        // イベントの種類と対象となるパスを出力
                        System.out.println(event.kind() + " " + event.context());
                    }
                     
                    boolean valid = key.reset();
                    if (!valid) {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            System.out.println("End Watch");
        }
    }
}
