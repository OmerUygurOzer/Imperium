package com.boomer.imperium.game.map;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.LogicUtils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

final class PathFinder {

    private class Node implements Pool.Poolable {
        Node previous;
        Direction direction;
        Tile tile;
        @Override
        public void reset() {
            previous = null;
            direction = null;
            tile = null;
        }
    }

    private final Set<Tile> visited = new HashSet<Tile>(100);

    private final Pool<Node> nodePool = new Pool<Node>(100) {
        @Override
        protected Node newObject() {
            return new Node();
        }
    };

    private final QueueComparator queueComparator = new QueueComparator();
    private class QueueComparator implements Comparator<Node>{

        Tile to;

        @Override
        public int compare(Node o1, Node o2) {
            return Double.compare(LogicUtils.distance(o1.tile,to),LogicUtils.distance(o2.tile,to));
        }
    }

    private final PriorityQueue<Node> queue = new PriorityQueue<Node>(200, queueComparator);
    private final Path tempPath = new Path(200);

    PathFinder() {}

    Direction getNextMoveForTarget(Map map,Tile from,Tile to){
        tempPath.tasks.clear();
        findPath(tempPath,map,from,to);
        if(tempPath.tasks.isEmpty())
            return Direction.O;
        return tempPath.tasks.get(0);
    }

    void findPath(Path path, Map map, Tile from, Tile to) {
        path.tasks.clear();
        PriorityQueue<Node> tiles = queue;
        queueComparator.to = to;

        Node node = nodePool.obtain();
        node.tile = from;
        visited.add(from);
        tiles.offer(node);

        while (!tiles.isEmpty()) {
            Node curNode = tiles.poll();

            if (curNode.tile.equals(to)) {
                deConstructPath(path,curNode);
                break;
            }
            int x = curNode.tile.tileX;
            int y = curNode.tile.tileY;
            for (Direction direction : Direction.values()) {
                Tile neighbor = map.getTileAt(x + (int) direction.directionVector.x, y + (int) direction.directionVector.y);
                if(neighbor != null && neighbor.equals(to)){
                    tiles.offer(inject(neighbor, direction, curNode));
                    break;
                }
                if (neighbor != null && neighbor.canBeMovedTo()) {
                    if (!visited.contains(neighbor)) {
                        tiles.offer(inject(neighbor, direction, curNode));
                        visited.add(neighbor);
                    }
                }
            }
        }

        for(Node nodeTiles: tiles){
            nodePool.free(nodeTiles);
        }

        queue.clear();
        visited.clear();
    }

    private void deConstructPath(Path path,Node node){
        Node cur = node;
         while (cur.previous!=null){
            path.addTask(cur.direction);
            cur = cur.previous;
        }
        path.reverse();
    }


    private Node inject(Tile tile, Direction direction, Node previous) {
        Node node = nodePool.obtain();
        node.tile = tile;
        node.direction = direction;
        node.previous = previous;
        return node;
    }


}
