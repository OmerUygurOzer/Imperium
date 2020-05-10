package com.boomer.imperium.gui;

import com.google.common.base.Splitter;
import com.boomer.imperium.model.Context;
import com.boomer.imperium.model.ContextReceiver;
import com.boomer.imperium.model.Entity;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

class EntityTreeView extends JTree implements ContextReceiver {

    private final List<Entity> entityList;
    private final EntityTreeListener entityTreeListener;
    private final DefaultMutableTreeNode root;
    private final DefaultTreeModel treeModel;
    private final Map<String,Entity> pathToEntityMap;

    EntityTreeView(EntityTreeListener entityTreeListener){
        this.entityList = new ArrayList<Entity>();
        this.entityTreeListener = entityTreeListener;
        this.root = new DefaultMutableTreeNode("Project");
        this.treeModel = new DefaultTreeModel(root);
        this.pathToEntityMap = new HashMap<String, Entity>();
        setModel(treeModel);
        addMouseListener(getMouseAdapter());
    }


    @Override
    public void receiveContext(Context context) {
        if(context == null){
            return;
        }
        root.removeAllChildren();
        pathToEntityMap.clear();
        entityList.clear();
        entityList.addAll(context.getEntities().getValue());
        Collections.sort(entityList, (o1, o2) -> String.CASE_INSENSITIVE_ORDER.compare(o1.getName(),o2.getName()));
        DefaultMutableTreeNode projectRoot = new DefaultMutableTreeNode(context.getName());
        root.add(projectRoot);
        for(Entity entity: entityList){
            DefaultMutableTreeNode entityNode = new DefaultMutableTreeNode(entity.getName());
            projectRoot.add(entityNode);
            pathToEntityMap.put(entity.getName(),entity);
        }
        treeModel.reload();
        expandPath(new TreePath(projectRoot.getPath()));
    }

    private MouseAdapter getMouseAdapter(){
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath path = getPathForLocation(e.getX(), e.getY());
                if(path == null ){
                    return;
                }
                String relativePathString = getRelativeEntityPathString(path);
                if(!pathToEntityMap.containsKey(relativePathString)){
                    return;
                }
                entityTreeListener.entitySelected(pathToEntityMap.get(relativePathString));
            }
        };
    }

    private static String getRelativeEntityPathString(TreePath treePath){
        List<String> splitStrings = Splitter.on(",")
                .splitToList(treePath.toString());
        return splitStrings.get(splitStrings.size()-1).replaceAll("]","").trim();
    }

}
