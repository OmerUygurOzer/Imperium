package com.boomer.imperium.game.map;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QuadNode<T extends Bound> {

    private final int maxDepth;
    private final Rectangle area;
    private final Rectangle topRightArea;
    private final Rectangle topLeftArea;
    private final Rectangle botRightArea;
    private final Rectangle botLeftArea;
    private final ArrayList<T> objects = new ArrayList<T>();
    private QuadNode<T> topRightNode;
    private QuadNode<T> topLeftNode;
    private QuadNode<T> botLeftNode;
    private QuadNode<T> botRightNode;

    public QuadNode(Rectangle area, int maxDepth) {
        this.area = area;
        this.maxDepth = maxDepth;
        float subWidth = area.width / 2f;
        float subHeight = area.height / 2f;
        this.topRightArea = new Rectangle(area.x + subWidth, area.y + subHeight, subWidth, subHeight);
        this.topLeftArea = new Rectangle(area.x, area.y + subHeight, subWidth, subHeight);
        this.botRightArea = new Rectangle(area.x + subWidth, area.y, subWidth, subHeight);
        this.botLeftArea = new Rectangle(area.x, area.y, subWidth, subHeight);
    }

    public void attach(T object) {
        attach(object, 0);
    }

    private List<T> findObjectsWithinRect(Rectangle rectangle) {
        LinkedList<T> found = new LinkedList<T>();
        findObjectsWithinRect(rectangle, 0, found);
        return found;
    }

    private void attach(T object, int depth) {
        if (maxDepth == depth - 1) {
            objects.add(object);
            return;
        }
        if (topRightArea.overlaps(object.getBounds().boundRectangle)) {
            if (topRightNode == null) {
                topRightNode = new QuadNode<T>(topRightArea, maxDepth);
            }
            topRightNode.attach(object, depth + 1);
        }
        if (topLeftArea.overlaps(object.getBounds().boundRectangle)) {
            if (topLeftNode == null) {
                topLeftNode = new QuadNode<T>(topLeftArea, maxDepth);
            }
            topLeftNode.attach(object, depth + 1);
        }
        if (botRightArea.overlaps(object.getBounds().boundRectangle)) {
            if (botRightNode == null) {
                botRightNode = new QuadNode<T>(botRightArea, maxDepth);
            }
            botRightNode.attach(object, depth + 1);
        }
        if (botLeftArea.overlaps(object.getBounds().boundRectangle)) {
            if (botLeftNode == null) {
                botLeftNode = new QuadNode<T>(botLeftArea, maxDepth);
            }
            botLeftNode.attach(object, depth + 1);
        }
    }

    private void findObjectsWithinRect(Rectangle rectangle, int depth, LinkedList<T> found) {
        if (maxDepth == depth - 1) {
            found.addAll(objects);
            return;
        }
        if (topRightArea.overlaps(rectangle)) {
            topRightNode.findObjectsWithinRect(rectangle, depth + 1, found);
        }
        if (topLeftArea.overlaps(rectangle)) {
            topLeftNode.findObjectsWithinRect(rectangle, depth + 1, found);
        }
        if (botRightArea.overlaps(rectangle)) {
            botRightNode.findObjectsWithinRect(rectangle, depth + 1, found);
        }
        if (botLeftArea.overlaps(rectangle)) {
            botLeftNode.findObjectsWithinRect(rectangle, depth + 1, found);
        }
    }

    public void clear() {
        objects.clear();
        if (topRightNode != null) {
            topRightNode.clear();
        }
        if (topLeftNode != null) {
            topLeftNode.clear();
        }
        if (botRightNode != null) {
            botRightNode.clear();
        }
        if (botLeftNode != null) {
            botLeftNode.clear();
        }
    }

}
