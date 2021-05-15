# activemq-integration-springboot
Publish Message in Active MQ using Springboot.

## Config Active MQ

##### application.properties

```
spring.activemq.broker-url=tcp://localhost:61616
spring.activemq.user=admin
spring.activemq.password=admin
```

### Config message convert and queue listner factory

#### com.example.pubsub.integration.ActiveMQConfig

```
package com.example.pubsub.integration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@EnableJms
@Configuration
public class ActiveMQConfig {

    public static final String ORDER_QUEUE = "order-queue";

    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

}
```
### Message class in my case

#### com.example.pubsub.integration.Order

```
package com.example.pubsub.integration;
import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Order implements Serializable {

    private String content;
    private String orderId;
    private String address;
    private Double amount;
    private Date timestamp;

    public Order() {
    }

    public Order(String content, Date timestamp) {
        this.content = content;
        this.timestamp = timestamp;
    }

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Order [content=" + content + ", orderId=" + orderId + ", address=" + address + ", amount=" + amount
				+ ", timestamp=" + timestamp + "]";
	}
}
```

In my case I want to publish xml data So I am using @XmlRootElement annotation to converting java class into XML string,
if you are using json then no need to annotated this.

please check my com.example.pubsub.service.ParserSample class for convertion utilities related to class to xml and reading xml and excl files.

### Producer

##### com.example.pubsub.integration.OrderSender

```
package com.example.pubsub.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.example.pubsub.service.ParserSample;

import static com.example.pubsub.integration.ActiveMQConfig.ORDER_QUEUE;

@Service
public class OrderSender {

    private static Logger log = LoggerFactory.getLogger(OrderSender.class);

    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Autowired
    private ParserSample parserSample;

    public void send(Order myMessage) {
        log.info("sending with convertAndSend() to queue <" + myMessage + ">");
        
        jmsTemplate.convertAndSend(ORDER_QUEUE, parserSample.jaxbObjectToXML(myMessage));
    }
}
```

I have executed my producer via rest endpoint.

### Consumer

##### com.example.pubsub.integration.OrderConsumer

```
package com.example.pubsub.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.Session;

import static com.example.pubsub.integration.ActiveMQConfig.ORDER_QUEUE;

@Component
public class OrderConsumer {

    private static Logger log = LoggerFactory.getLogger(OrderConsumer.class);

    @JmsListener(destination = ORDER_QUEUE)
    public void receiveMessage(@Payload String order,
                               @Headers MessageHeaders headers,
                               Message message, Session session) {
        log.info("received <" + order + ">");
    }

}
```

### Let's execute producer via rest endpoint

In my case , I did not set any port and contextpath for my application So my application running on default port 8080

##### Url: http://localhost:8080/index/publish

##### Method : POST , Body :

```
{
	"address" : "delhi"
}
```

# Thank You!
