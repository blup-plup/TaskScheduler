package com.Observe.TaskScheduler.Common;



import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class Constants {

    public final static Set<String> TaskTypeSet = Sets.newHashSet("a","b");
    public final static String successResponse = "success";
    public final static Set<String> prioritySet = Sets.newHashSet("high","medium","low");
    public final static Map<Integer,String> statusMap = ImmutableMap.of(1,"Completed",
    2,"Failure",3,"Progress");


}
