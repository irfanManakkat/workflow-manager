package com.irfan.workflowmanager.converters;

import com.irfan.workflowmanager.enums.Activity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Irfan Manakkat<irfan.manakkat@pearldatadirect.com>
 */

@Converter
public class ActivityListConverter implements AttributeConverter<List<Activity>, String> {
    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<Activity> activityList) {
        return activityList != null ? activityList.stream()
                .map(Activity::name) // Convert each Activity to its name (String)
                .collect(Collectors.joining(SPLIT_CHAR)) : "";
    }

    @Override
    public List<Activity> convertToEntityAttribute(String string) {
        return string != null && !string.isEmpty() ? Arrays.stream(string.split(SPLIT_CHAR))
                .map(Activity::valueOf) // Convert each name (String) back to Activity
                .collect(Collectors.toList()) : Collections.emptyList();
    }
}
