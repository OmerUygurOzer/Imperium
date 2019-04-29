package com.boomer.imperium.game.map;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QuadNode<T extends Bound> {

    private final float minSize;
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

    public QuadNode(Rectangle area, float minSize) {
        this.area = area;
        this.minSize = minSize;
        float subWidth = area.width / 2f;
        float subHeight = area.height / 2f;
        this.topRightArea = new Rectangle(area.x + subWidth, area.y + subHeight, subWidth, subHeight);
        this.topLeftArea = new Rectangle(area.x, area.y + subHeight, subWidth, subHeight);
        this.botRightArea = new Rectangle(area.x + subWidth, area.y, subWidth, subHeight);
        this.botLeftArea = new Rectangle(area.x, area.y, subWidth, subHeight);
    }

    public void attach(T object) {
        attach(object, area.width);
    }

    public List<T> findObjectsWithinRect(Rectangle rectangle) {
        LinkedList<T> found = new LinkedList<T>();
        findObjectsWithinRect(rectangle, area.width, found);
        return found;
    }

    private void attach(T object, float curWidth) {
        float nextWidth = curWidth/2f;
        if (nextWidth < minSize) {
            objects.add(object);
            return;
        }
        Rectangle boundRect = object.getBounds();
        if (topRightArea.overlaps(boundRect)) {
            if (topRightNode == null) {
                topRightNode = new QuadNode<T>(topRightArea, minSize);
            }
            topRightNode.attach(object, nextWidth);
        }
        if (topLeftArea.overlaps(boundRect)) {
            if (topLeftNode == null) {
                topLeftNode = new QuadNode<T>(topLeftArea, minSize);
            }
            topLeftNode.attach(object, nextWidth);
        }
        if (botRightArea.overlaps(boundRect)) {
            if (botRightNode == null) {
                botRightNode = new QuadNode<T>(botRightArea, minSize);
            }
            botRightNode.attach(object, nextWidth);
        }
        if (botLeftArea.overlaps(boundRect)) {
            if (botLeftNode == null) {
                botLeftNode = new QuadNode<T>(botLeftArea, minSize);
            }
            botLeftNode.attach(object, nextWidth);
        }
    }

    private void findObjectsWithinRect(Rectangle rectangle, float curWidth, LinkedList<T> found) {
        float nextWidth = curWidth/2f;
        if (nextWidth < minSize) {
            found.addAll(objects);
            return;
        }
        if (topRightArea.overlaps(rectangle) && topRightNode != null) {
            topRightNode.findObjectsWithinRect(rectangle, nextWidth, found);
        }
        if (topLeftArea.overlaps(rectangle) && topLeftNode != null) {
            topLeftNode.findObjectsWithinRect(rectangle, nextWidth, found);
        }
        if (botRightArea.overlaps(rectangle) && botRightNode != null) {
            botRightNode.findObjectsWithinRect(rectangle, nextWidth, found);
        }
        if (botLeftArea.overlaps(rectangle) && botLeftNode != null) {
            botLeftNode.findObjectsWithinRect(rectangle, nextWidth, found);
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
