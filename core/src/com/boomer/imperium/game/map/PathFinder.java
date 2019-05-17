package com.boomer.imperium.game.map;

import com.badlogic.gdx.utils.Pool;
import com.boomer.imperium.game.Direction;
import com.boomer.imperium.game.LogicUtils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public final class PathFinder {

    private static class Node implements Pool.Poolable {
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

    private static final Set<Tile> VISITED = new HashSet<Tile>(100);

    private static final Pool<Node> NODE_POOL = new Pool<Node>(100) {
        @Override
        protected Node newObject() {
            return new Node();
        }
    };

    private static final QueueComparator QUEUE_COMPARATOR = new QueueComparator();

    private static class QueueComparator implements Comparator<Node>{

        Tile to;

        @Override
        public int compare(Node o1, Node o2) {
            return Double.compare(LogicUtils.distance(o1.tile,to),LogicUtils.distance(o2.tile,to));
        }
    }

    private static final PriorityQueue<Node> QUEUE = new PriorityQueue<Node>(200, QUEUE_COMPARATOR);
    private static final Path TEMP_PATH = new Path(200);

    public static Direction getNextMoveForTarget(Map map,Tile from,Tile to){
        TEMP_PATH.tasks.clear();
        findPath(TEMP_PATH,map,from,to);
        if(TEMP_PATH.tasks.isEmpty())
            return Direction.O;
        return TEMP_PATH.tasks.get(0);
    }

    public static void findPath(Path path, Map map, Tile from, Tile to) {
        path.tasks.clear();
        PriorityQueue<Node> tiles = QUEUE;
        QUEUE_COMPARATOR.to = to;

        Node node = NODE_POOL.obtain();
        node.tile = from;
        VISITED.add(from);
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
                    if (!VISITED.contains(neighbor)) {
                        tiles.offer(inject(neighbor, direction, curNode));
                        VISITED.add(neighbor);
                    }
                }
            }
        }

        for(Node nodeTiles: tiles){
            NODE_POOL.free(nodeTiles);
        }

        QUEUE.clear();
        VISITED.clear();
    }

    private static void deConstructPath(Path path,Node node){
        Node cur = node;
         while (cur.previous!=null){
            path.addTask(cur.direction);
            cur = cur.previous;
        }
        path.reverse();
    }


    private static Node inject(Tile tile, Direction direction, Node previous) {
        Node node = NODE_POOL.obtain();
        node.tile = tile;
        node.direction = direction;
        node.previous = previous;
        return node;
    }

    private PathFinder() {
    }
}
