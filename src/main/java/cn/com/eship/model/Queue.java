package cn.com.eship.model;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by simon on 2016/10/30.
 */
@Component
public class Queue {
    private BlockingQueue taskQueue = new LinkedBlockingQueue();
    private BlockingQueue urlQueue = new LinkedBlockingQueue();

    public BlockingQueue getTaskQueue() {
        return taskQueue;
    }

    public void setTaskQueue(BlockingQueue taskQueue) {
        this.taskQueue = taskQueue;
    }

    public BlockingQueue getUrlQueue() {
        return urlQueue;
    }

    public void setUrlQueue(BlockingQueue urlQueue) {
        this.urlQueue = urlQueue;
    }
}
