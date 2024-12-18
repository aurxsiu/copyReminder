package com.aurxsiu.service;

import java.awt.*;
import java.awt.datatransfer.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CopyService {
    private Clipboard systemClipboard;
    private TrayIcon trayIcon;
    public CopyService(){
        System.out.println("start");
        if(!SystemTray.isSupported()){
            System.out.println("the toast service status: false");
            return;
        }

        /*
         * 创建tray,用于退出和通知*/
        SystemTray tray = SystemTray.getSystemTray();
        Image image = Toolkit.getDefaultToolkit().createImage(getClass().getClassLoader().getResource("testImage.png"));
        trayIcon = new TrayIcon(image,"testDemo");
        trayIcon.setImageAutoSize(true);trayIcon.setToolTip("copy behavior");
        PopupMenu popupMenu = new PopupMenu();
        MenuItem menuItem = new MenuItem();
        menuItem.setLabel("quit");
        menuItem.addActionListener((listener)->{
            System.exit(0);
        });
        popupMenu.add(menuItem);
        trayIcon.setPopupMenu(popupMenu);
        try {
            tray.add(trayIcon);
        } catch (AWTException ex) {
            System.out.println("wrong! add tray fail");
            return;
        }

        /*监测剪切板*/
        systemClipboard = Toolkit.getDefaultToolkit().getSystemClipboard();


        new Thread(new ManagerThread()).start();
    }
    private class ManagerThread implements Runnable{

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            while(true){
                System.out.println("scan");
                String str = scanner.next();
                System.out.println("\""+str+"\"");
                if(str.equals("close")){
                    System.exit(0);
                }
                if(str.equals("listener")){
                    System.out.println(Arrays.toString(systemClipboard.getFlavorListeners()));
                }
            }
        }
    }

    public void test() {
        Object recentContent = null;
        while(true){
            try{
                Thread.sleep(1000);
                List<DataFlavor> list = Arrays.asList(systemClipboard.getAvailableDataFlavors());
                Object data = null;
                if(list.contains(DataFlavor.stringFlavor)){
                    data = systemClipboard.getData(DataFlavor.stringFlavor);
                }else if(list.contains(DataFlavor.imageFlavor)){
                    data = systemClipboard.getData(DataFlavor.imageFlavor);
                }else if(list.contains(DataFlavor.javaFileListFlavor)){
                    data = systemClipboard.getData(DataFlavor.javaFileListFlavor);
                }
                if(data == null){
                    if(data != recentContent){
                        effect(data);
                    }
                }else if (!(data instanceof Image) && !data.equals(recentContent)) {
                    System.out.println(data.getClass());
                    recentContent = data;
                    effect(data);
                }
            }catch (Exception e){
                System.out.println("wrong");
            }

        }
    }
    private void effect(Object data){
        System.out.println("New clipboard text detected: " + data);
        trayIcon.displayMessage("copyService","剪切板更换", TrayIcon.MessageType.INFO);
    }
}
