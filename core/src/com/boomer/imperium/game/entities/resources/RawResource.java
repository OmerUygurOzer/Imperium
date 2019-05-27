package com.boomer.imperium.game.entities.resources;

import com.boomer.imperium.game.graphics.ResourceSpriteAnimator;

import java.util.Map;
import java.util.Set;

public interface RawResource extends Resource {
    ResourceCategory getCategory();
    int getMonthlyRenewal();
    CollectabilityType getCollectabilityType();
    Set<Integer> canBeProcessedBy();
    Map<ProcessedResource,Integer> processRequirements();
    ResourceSpriteAnimator resourceSpriteAnimator();
}
