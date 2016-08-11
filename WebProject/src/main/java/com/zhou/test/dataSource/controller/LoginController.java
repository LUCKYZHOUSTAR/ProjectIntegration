/**
 * 
 */
package com.zhou.test.dataSource.controller;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhou.test.dataSource.dao.domain.Depart;
import com.zhou.test.dataSource.service.DepartMentService;

/**
 * @author LUCKY
 *
 */
@Controller
@RequestMapping(value = "/test")
public class LoginController {

    public static AtomicInteger indexAtomicInteger = new AtomicInteger(123);
    @Autowired
    private DepartMentService   departMentService;

    @RequestMapping(value = "insert")
    public void test() {
        task task = new task();
        ExecutorService threadService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10000; i++) {
            threadService.submit(task);
        }
        System.out.println("开始测试了");

    }

    class task implements Runnable {
        @Override
        public void run() {
            Depart depart = new Depart();
            depart.setDepMan("haha");
            depart.setDepName("hah");
            depart.setDepNo(indexAtomicInteger.incrementAndGet());
            departMentService.inertDepart(depart);
        }

    }

}
