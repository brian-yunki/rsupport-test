package rsupport.test.support;

import java.util.function.Function;

public class Utils {

    // Runtime Processor수에 따라 ForkJoinPool 사이즈 계산
    // pool size = (total processor size / argv (2)) + 1
    public static final Function<Integer, Integer> calculatedPoolSize = (argv) -> (Runtime.getRuntime().availableProcessors()/argv) + 1;




}
