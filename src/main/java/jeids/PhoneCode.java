package jeids;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import redis.clients.jedis.Jedis;

import java.util.Random;

public class PhoneCode {

    private static Logger log= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    public static void main(String[] args) {

//        String code = getCode();
//        log.info("验证码：{}",code);
        //模拟验证码发送
        verifyCode("13812341234");
        //验证码校验
//        checkCode("13812341234","222");
    }

    /**
     * 验证码校验
     * @param phone
     * @param code
     */
    public static void checkCode(String phone,String code) {

        Jedis jedis = new Jedis("192.168.245.130",6379);
        jedis.auth("123");
        //验证key
        String codeKey="Verify"+phone+":code";
        String redisCode = jedis.get(codeKey);

        if (redisCode.equals(code)){
            log.info("验证码发送成功");
        }else {
            log.info("验证码发送失败");
        }
        jedis.close();
    }


    /**
     * 每个手机每天只能发送三次，发送验证码到 redis里面，设置过期时间
     * @param phone
     */
    public static void verifyCode(String phone) {

        Jedis jedis = new Jedis("192.168.245.130",6379);
        jedis.auth("123");
        //拼接
        String countKey="Verify"+phone+":count";

        String codeKey="Verify"+phone+":code";

        //每个手机每天只能发送三次
        String count=jedis.get(countKey);
        if (count==null){
            //设置发送次数是1
            jedis.setex(countKey,24*60*60,"1");
        }else if (Integer.parseInt(count)<=2){
            //发送次数+1
            jedis.incr(countKey);


        }else if (Integer.parseInt(count)>2){
            //发送超过三次，不能再发送
            log.info("今天发送次数已经超过三次了");
            jedis.close();
            return;//不再执行发送验证码的操作
        }

        //发送验证码到 redis里面
        String vcode = getCode();
        jedis.setex(codeKey,120,vcode);
    }


    /**
     * 生成6位数字验证码
     * @return
     */
    public static String getCode() {

        Random random = new Random();
        String code = "";
        for (int i=0;i<6;i++){
            int rand=random.nextInt(10);
            code += rand;
        }

        return code;
    }
}
