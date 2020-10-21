package com.icub.task.employee.configuration;

import com.icub.task.employee.commons.event.GeneralInformationEvent;
import com.icub.task.employee.commons.event.ProjectEvent;
import com.icub.task.employee.commons.event.QualificationEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KafkaConfiguration {

    @Value("${icub.kafka.topic.general-information}")
    private String generalInformationTopic;

    @Value("${icub.kafka.topic.qualifation}")
    private String qualificationTopic;

    @Value("${icub.kafka.topic.project}")
    private String projetTopic;

    @Bean
    public RecordMessageConverter consumerConverter() {
        StringJsonMessageConverter consumerConverter = new StringJsonMessageConverter();
        DefaultJackson2JavaTypeMapper mapper = new DefaultJackson2JavaTypeMapper();
        mapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        mapper.addTrustedPackages("com.icub.task.employee.commons.event");
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("generalInformationEvent", GeneralInformationEvent.class);
        mappings.put("qualificationEvent", QualificationEvent.class);
        mappings.put("projectEvent", ProjectEvent.class);
        mapper.setIdClassMapping(mappings);
        consumerConverter.setTypeMapper(mapper);
        return consumerConverter;
    }

    @Bean
    public NewTopic employeGeneralInformationTopic() {
        return TopicBuilder.name(generalInformationTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic employeQualificationTopic() {
        return TopicBuilder.name(qualificationTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic employerProjectTopic() {
        return TopicBuilder.name(projetTopic)
                .partitions(1)
                .replicas(1)
                .build();
    }

}
