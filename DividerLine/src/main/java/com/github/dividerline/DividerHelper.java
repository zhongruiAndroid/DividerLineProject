package com.github.dividerline;

public class DividerHelper {
    private static DividerHelper singleObj;
    private DividerHelper() {
    }
    public static DividerHelper get(){
        if(singleObj==null){
            synchronized (DividerHelper.class){
                if(singleObj==null){
                    singleObj=new DividerHelper();
                }
            }
        }
        return singleObj;
    }



}
