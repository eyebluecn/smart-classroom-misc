package com.smart.classroom.misc.domain.middleware.thread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;


//这个是异步处理器服务
@Slf4j
@Service
public class AsyncHandlerService {

    public final static int THREAD_MAX_NUM = 60;

    /**
     * 执行异步任务的线程池
     */
    private final ExecutorService executorService = new ThreadPoolExecutor(
            THREAD_MAX_NUM / 2,
            THREAD_MAX_NUM,
            10,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10240), // 阻塞队列的大小需要显式指定，要不然防止内存堆积导致fgc
            new BasicThreadFactory.Builder().namingPattern("AsyncHandlerService-thread-%d").daemon(true).build()
    );

    public void submit(Runnable runnable) {

        Callable<Integer> callable = () -> {
            runnable.run();
            return 1;
        };

        Future<?> future = executorService.submit(callable);
        //异常的捕获必须要一个异步线程中去进行，否则 future.get 会阻塞，失去了异步性。
        executorService.submit(() -> {
            try {
                future.get();
            } catch (Throwable throwable) {
                log.error("执行异步任务出错{}", ExceptionUtils.getRootCauseMessage(throwable), throwable);

            }
        });


    }


}
