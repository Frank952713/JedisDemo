package jeids;

import redis.clients.jedis.Jedis;

public class JeisDemo1 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.245.130",6379);
        //设置密码
        jedis.auth("123");

        String ping = jedis.ping();

        System.out.println(ping);
    }
}
