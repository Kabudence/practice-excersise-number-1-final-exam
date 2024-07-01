package com.acme.center.platform.analytics.interfaces.rest.resources;

public record CreateCommandResource(String name,
                                   String description,
                                   Double minValue,
                                   Double maxValue,
                                   String deviceType) {
}
