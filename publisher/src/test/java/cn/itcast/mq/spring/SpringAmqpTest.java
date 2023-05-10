package cn.itcast.mq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() {
        String queueName = "simple.queue";
        String message = "hello, spring amqp!";
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Test
    public void testSendMessage2WorkQueue() throws InterruptedException {
        String queueName = "simple.queue";
        String message = "hello, message__";
        for (int i = 1; i <= 50; i++) {
            rabbitTemplate.convertAndSend(queueName, message + i);
            Thread.sleep(20);
        }
    }

    // 广播模式发送 发送给所有和交换机绑定的队列
    @Test
    public void testSendFanoutExchange() {
        // 交换机名称
        String exchangeName = "itcast.fanout";
        // 消息
        String message = "hello, every one!";
        // 发送消息 广播模式
        rabbitTemplate.convertAndSend(exchangeName, "", message);
    }

    // 路由模式发送 通过key发送给指定的队列
    @Test
    public void testSendDirectExchange() {
        // 交换机名称
        String exchangeName = "itcast.direct";
        // 消息
        String message = "hello, red";
        // 发送消息 路由模式
        rabbitTemplate.convertAndSend(exchangeName, "red", message);
    }

    // 发布订阅模式topic模式
    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "itcast.topic";
        // 消息
        String message = "今天不学习,明天变垃圾！";
        // 发送消息 路由模式
        rabbitTemplate.convertAndSend(exchangeName, "china.weather", message);
    }

    // 发送jcakson序列化之后的map数据
    @Test
    public void testSendObjectQueue(){
        Map<String, Object> msg = new HashMap<>();
        msg.put("name", "张三");
        msg.put("age", "21");
        rabbitTemplate.convertAndSend("object.queue", msg);
    }
}
