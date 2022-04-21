package mao;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import mao.utils.RabbitMQ;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Project name(项目名称)：rabbitMQ工作队列之消息手动应答
 * Package(包名): mao
 * Class(类名): Consumer
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/21
 * Time(创建时间)： 21:40
 * Version(版本): 1.0
 * Description(描述)：
 * A.Channel.basicAck(用于肯定确认)
 * RabbitMQ 已知道该消息并且成功的处理消息，可以将其丢弃了
 * B.Channel.basicNack(用于否定确认)
 * C.Channel.basicReject(用于否定确认)
 * 与 Channel.basicNack 相比少一个参数
 * 不处理该消息了直接拒绝，可以将其丢弃了
 * multiple 的 true 和 false 代表不同意思
 * true 代表批量应答 channel 上未应答的消息
 */

public class Consumer
{
    private static final String QUEUE_NAME = "work";

    public static void main(String[] args) throws IOException, TimeoutException
    {
        Channel channel = RabbitMQ.getChannel();

        System.out.println("消费者1开始接收消息，处理时间较短");


        //设置为手动应答
        channel.basicConsume(QUEUE_NAME, false, new DeliverCallback()
        {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException
            {
                try
                {
                    Thread.sleep(30);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                byte[] messageBody = message.getBody();
                String message1 = new String(messageBody, StandardCharsets.UTF_8);
                System.out.println("消息：" + message1);
                //确认一条或多条收到的消息。
                // 从AMQP.Basic.GetOk或AMQP.Basic.Deliver方法提供 deliveryTag，其中包含正在确认的接收消息。
                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);

            }
        }, new CancelCallback()
        {
            @Override
            public void handle(String consumerTag) throws IOException
            {
                System.out.println("消息接收失败");
            }
        });
    }
}
