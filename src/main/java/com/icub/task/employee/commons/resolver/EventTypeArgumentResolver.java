package com.icub.task.employee.commons.resolver;

import com.icub.task.employee.commons.event.EventType;
import com.icub.task.employee.commons.exception.EmployeeNotFoundException;
import com.icub.task.employee.commons.exception.UnsupportedEventTypeException;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class EventTypeArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameter().getType()==EventType.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String eventType = nativeWebRequest.getHeader("X-EVENT-TYPE");
        try {
            if (eventType==null) eventType="";
            EventType  event = EventType.valueOf(eventType);
        }catch (IllegalArgumentException ex){
            throw new UnsupportedEventTypeException("Unsuported event type. Supported types are INSERT,DELETE,UPDATE");
        }
        return EventType.valueOf(eventType);
    }
}
