///**
// * 
// */
//package com.zhou.test.dataSource.test;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.zhou.test.dataSource.dao.domain.Depart;
//import com.zhou.test.dataSource.service.DepartMentService;
//
///**
// * @author LUCKY
// *
// */
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(locations = "classpath:applicationContext-service.xml")
//public class DepartTest {
//
//    @Autowired
//    private DepartMentService departMentService;
//    @Test
//    public void insertTest() {
//        Depart depart = new Depart();
//        depart.setDepMan("22222222222");
//        depart.setDepName("departnameinfo");
//        depart.setDepNo(34);
//        System.out.println(departMentService.inertDepart(depart));
//    }
//}
