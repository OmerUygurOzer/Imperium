package com.boomer.imperium.gui;

import com.boomer.imperium.Context;
import com.boomer.imperium.Entity;
import com.boomer.imperium.model.ContextReceiver;
import com.boomer.imperium.model.EntityReceiver;
import com.boomer.imperium.scripts.ScriptMirror;
import com.boomer.imperium.scripts.mirrors.Attribute;
import com.google.common.collect.Iterables;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

class ComponentEditingTab extends JPanel implements EntityReceiver,ContextReceiver,ListSelectionListener {


    private Context context;
    private Entity entity;
    private final JList<ScriptMirror> scriptMirrorJList;
    private final DefaultListModel<ScriptMirror> defaultListModel;
    private final Map<Attribute,AttributeEditingPanel> attributeToEditingPanelMap;
    private JPanel entityAttributesPanel;

    ComponentEditingTab(){
        setLayout(new BorderLayout());
        this.scriptMirrorJList = new JList<>();
        this.scriptMirrorJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.scriptMirrorJList.setBorder(new LineBorder(Color.BLACK,5));
        this.defaultListModel = new DefaultListModel<>();
        this.scriptMirrorJList.setModel(defaultListModel);
        this.attributeToEditingPanelMap = new LinkedHashMap<>();
    }

    @Override
    public void receiveEntity(Entity entity) {
        if(entity == null){return;}
        scriptMirrorJList.removeListSelectionListener(this);
        this.entity = entity;
        defaultListModel.clear();
        populateUI();
        scriptMirrorJList.addListSelectionListener(this);
    }

    @Override
    public void receiveContext(Context context) {
        if(context==null){return;}
        scriptMirrorJList.removeListSelectionListener(this);
        this.context = context;
        defaultListModel.clear();
        populateUI();
        scriptMirrorJList.addListSelectionListener(this);
    }

    private void populateUI(){
        removeAll();
        add(BorderLayout.NORTH, createScriptAdditionPanel());
        add(BorderLayout.WEST,createAttributeListPanel());
        if(entity != null){
            add(BorderLayout.CENTER, createEntityComponentsPanel());
        }
        revalidate();

    }

    private JPanel createScriptAdditionPanel(){
        JPanel attributeCreationPanel = new JPanel(new FlowLayout());
        JLabel addNewComponentLabel = new JLabel("Add New Component: ");
        final JComboBox<ScriptMirror> scriptMirrorJComboBox = new JComboBox<ScriptMirror>(Iterables
                .toArray(context.getScriptList().getValue().values(),ScriptMirror.class));

        JLabel attributeNameLabel = new JLabel("Name:");

        attributeCreationPanel.add(addNewComponentLabel);
        attributeCreationPanel.add(scriptMirrorJComboBox);
        attributeCreationPanel.add(attributeNameLabel);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e ->{
            ScriptMirror scriptMirror = (ScriptMirror) scriptMirrorJComboBox.getSelectedItem();
            if(entity == null){
                JOptionPane.showMessageDialog(this,"Must select an entity first!");
                return;
            }
            if(entity.getScripts().contains(scriptMirror)){
                JOptionPane.showMessageDialog(this,"Script already exist!");
                return;
            }
            entity.getScripts().add(scriptMirror);
            defaultListModel.addElement(scriptMirror);
        });
        attributeCreationPanel.add(addButton);
        return attributeCreationPanel;
    }

    private JPanel createAttributeListPanel(){
        JPanel containerPanel = new JPanel(new BorderLayout());
        if(entity!=null) {
            for (ScriptMirror scriptMirror : entity.getScripts()) {
                defaultListModel.addElement(scriptMirror);
            }
        }

        JScrollPane attributeListPanel = new JScrollPane();
        attributeListPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        attributeListPanel.setViewportView(scriptMirrorJList);
        containerPanel.add(BorderLayout.CENTER,attributeListPanel);

        JPanel buttonContainer = new JPanel(new BorderLayout());

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            ScriptMirror selectedValue = scriptMirrorJList.getSelectedValue();
            if(selectedValue==null){
                return;
            }
            int option = JOptionPane
                    .showConfirmDialog(containerPanel,
                            "Are you sure you want to delete the script from the entity?",
                            "Warning",JOptionPane.YES_NO_OPTION);
            if(option==JOptionPane.YES_OPTION){
                entity.getScripts().remove(selectedValue);
                defaultListModel.removeElement(selectedValue);
            }
        });

        buttonContainer.add(BorderLayout.WEST,deleteButton);
        containerPanel.add(BorderLayout.SOUTH,buttonContainer);

        return containerPanel;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(!e.getValueIsAdjusting()){
            populateScriptAttributes(scriptMirrorJList.getSelectedValue());
        }
    }

    private JScrollPane createEntityComponentsPanel(){
        JScrollPane containerScrollPane = new JScrollPane();
        containerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel containerViewPanel = new JPanel();
        BoxLayout containerLayout = new BoxLayout(containerViewPanel,BoxLayout.PAGE_AXIS);
        containerViewPanel.setLayout(containerLayout);
        containerScrollPane.setViewportView(containerViewPanel);

        entityAttributesPanel = new JPanel();
        BoxLayout entityAttributesLayout = new BoxLayout(entityAttributesPanel,BoxLayout.PAGE_AXIS);
        entityAttributesPanel.setLayout(entityAttributesLayout);
        containerViewPanel.add(entityAttributesPanel);
        return containerScrollPane;
    }

    public void storeEditions(){
        for(AttributeEditingPanel attributeEditingPanel : attributeToEditingPanelMap.values()){
            attributeEditingPanel.storeValues();
        }
    }

    private void populateScriptAttributes(ScriptMirror scriptMirror){
        entityAttributesPanel.removeAll();
        attributeToEditingPanelMap.clear();
        for(Attribute attribute: scriptMirror.getAttributeList().getValue().values()){
            attributeToEditingPanelMap.put(attribute,new AttributeEditingPanel(context,attribute));
            entityAttributesPanel.add(attributeToEditingPanelMap.get(attribute));
            scriptMirrorJList.setSelectedIndex(0);
        }
        revalidate();

    }
}
