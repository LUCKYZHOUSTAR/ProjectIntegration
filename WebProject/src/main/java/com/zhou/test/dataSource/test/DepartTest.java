///**
// * 
// */
//package com.zhou.test.dataSource.test;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.zhou.test.dataSource.dao.domain.Depart;
//import com.zhou.test.dataSource.service.DepartMentService;
//
///**
// * @author LUCKY
// *
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = "classpath:applicationContext-service.xml")
//public class DepartTest {
//
//    @Autowired
//    private DepartMentService departMentService;
//
//    @Test
//    public void insertTest() {
//        Depart depart = new Depart();
//        depart.setDepMan("22222222222");
//        depart.setDepName("departnameinfo");
//        depart.setDepNo(36);
//        System.out.println(departMentService.inertDepart(depart));
//    }
//}
