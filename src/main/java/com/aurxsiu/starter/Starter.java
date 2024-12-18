package com.aurxsiu.starter;

import com.aurxsiu.pool.ServicePool;
import com.aurxsiu.service.CopyService;

public class Starter {
    public static void main(String[] args) {
        CopyService copyService = ServicePool.getCopyService();

        copyService.test();

    }
}
