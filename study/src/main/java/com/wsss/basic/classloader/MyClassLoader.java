package com.wsss.basic.classloader;

import java.io.IOException;
import java.io.InputStream;

//该加载器可以加载与自己在同一路径下的Class文件  
public class MyClassLoader extends ClassLoader {  
    @Override  
    public Class<?> loadClass(String name) throws ClassNotFoundException{  
        try {    
            String fileName=name.substring(name.lastIndexOf(".")+1)+".class";  
            InputStream is=getClass().getResourceAsStream(fileName);    
            if(is==null){  
                //不在当前路径下的类，例如Object类（JavaBean的父类），采用委派模型加载  
                return super.loadClass(name);   
            }else{  
                //在当前路径下的类，例如JavaBean类，直接通过自己加载其Class文件  
                byte[] b=new byte[is.available()];  
                is.read(b);  
                return defineClass(name,b,0,b.length);     
            }  
  
        } catch (IOException e) {   
            throw new ClassNotFoundException();  
        }  
    }  
}  
