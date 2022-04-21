package mao;

import com.rabbitmq.client.Channel;
import mao.utils.RabbitMQ;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Project name(项目名称)：rabbitMQ工作队列之消息手动应答
 * Package(包名): mao
 * Class(类名): Producer
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2022/4/21
 * Time(创建时间)： 21:40
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public class Producer
{
    private static final String QUEUE_NAME = "work";

    public static void main(String[] args) throws IOException, TimeoutException
    {
        //获得信道
        Channel channel = RabbitMQ.getChannel();

        for (int i = 0; i < 30; i++)
        {
            String message = "消息" + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("发送完成");
    }
}
