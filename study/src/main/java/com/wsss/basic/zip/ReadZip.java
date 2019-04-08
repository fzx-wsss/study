package com.wsss.basic.zip;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ReadZip {

    public static void main(String[] args) throws Exception {
        try {  
               readZipFile("C:\\work\\Workspaces\\unpsMchtEmu1\\lib\\SADK-3.1.0.8.jar");  
           } catch (Exception e) {  
               // TODO Auto-generated catch block  
               e.printStackTrace();  
           }  
    }
    
    public static void readZipFile(String file) throws Exception {  
           ZipFile zf = new ZipFile(file);  
           InputStream in = new BufferedInputStream(new FileInputStream(file));  
           ZipInputStream zin = new ZipInputStream(in);  
           ZipEntry ze;  
           while ((ze = zin.getNextEntry()) != null) {  
               if (ze.isDirectory()) {
               } else {  
                   System.err.println("file - " + ze.getName() + " : "  
                           + ze.getSize() + " bytes");  
                   long size = ze.getSize();  
                   if (size > 0) {  
                       BufferedReader br = new BufferedReader(  
                               new InputStreamReader(zf.getInputStream(ze)));  
                       String line;  
                       while ((line = br.readLine()) != null) {  
                           System.out.println(line);  
                       }  
                       br.close();  
                   }  
                   System.out.println();  
               }  
           }  
           zin.closeEntry();  
       }  
}
