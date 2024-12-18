package com.aurxsiu.pool;

import com.aurxsiu.service.CopyService;

public class ServicePool {
    private static class Holder{
        private static CopyService copyService = new CopyService();
    }

    public static CopyService getCopyService(){
        return Holder.copyService;
    }
}
