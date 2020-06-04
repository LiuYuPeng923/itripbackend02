package ceshi;

import cn.itrip.auth.service.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class Ceshi {
    @Resource
    private MailService mailService;

    @Test
    public void test() {

        try {
            mailService.sendMail("15131133572@163.com", "7654321");
            System.out.println(mailService);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
