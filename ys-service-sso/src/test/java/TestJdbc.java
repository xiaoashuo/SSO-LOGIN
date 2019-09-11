import com.lovecyy.sso.SSOApplication;
import com.lovecyy.sso.common.dto.HpUser;
import com.lovecyy.sso.common.service.HpUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ys
 * @topic
 * @date 2019/9/10 11:45
 */
@SpringBootTest(classes = SSOApplication.class)
@RunWith(SpringRunner.class)
public class TestJdbc {

    @Autowired
    HpUserService hpUserService;

    @Test
    public void test(){
        List<HpUser> hpUsers = hpUserService.selectAll();
        System.out.println(hpUsers);

    }
}
