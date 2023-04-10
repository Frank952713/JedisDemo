import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Test {

    private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    public static void main(String[] args) {
        for (int i=0;i<3;i++){
            //记录 trace 级别的日志信息
            logger.trace("log4j日志输出：This is trace message.");
            //记录 debug 级别的日志信息
            logger.debug("log4j日志输出：This is debug message.");
            //记录 info 级别的日志信息
            logger.info("log4j日志输出：This is info message.");
            //记录 error 级别的日志信息
            logger.error("log4j日志输出：This is error message.");

        }
    }

}
