package com.boomer.imperium.gui;

import com.google.common.base.Joiner;
import com.boomer.imperium.gui.util.TextUtils;
import com.boomer.imperium.model.Context;
import com.boomer.imperium.model.ContextReceiver;
import com.boomer.imperium.model.Entity;
import com.boomer.imperium.model.EntityReceiver;
import com.boomer.imperium.model.attribute.Attribute;
import com.boomer.imperium.model.attribute.AttributeType;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class ComponentEditingTab extends JPanel implements EntityReceiver,ContextReceiver,AttributeEditingPanelListener {


    private Context context;
    private Entity entity;
    private final JList<Attribute> attributeJList;
    private final DefaultListModel<Attribute> defaultListModel;
    private final Map<Attribute,AttributeEditingPanel> attributeToEditingPanelMap;
    private JPanel entityAttributesPanel;

    public ComponentEditingTab(){
        setLayout(new BorderLayout());
        this.attributeJList = new JList<>();
        this.attributeJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.attributeJList.setBorder(new LineBorder(Color.BLACK,5));
        this.defaultListModel = new DefaultListModel<>();
        this.attributeJList.setModel(defaultListModel);
        this.attributeToEditingPanelMap = new LinkedHashMap<>();
    }

    @Override
    public void receiveEntity(Entity entity) {
        if(entity == null){return;}
        this.entity = entity;
        populateUI();
    }

    @Override
    public void receiveContext(Context context) {
        if(context==null){return;}
        this.context = context;
        defaultListModel.clear();
        for(Attribute attribute: context.getCreatedAttributes().getValue()){defaultListModel.addElement(attribute);}
        populateUI();
    }

    private void populateUI(){
        removeAll();
        add(BorderLayout.NORTH,createAttributeCreationPanel());
        add(BorderLayout.WEST,createAttributeListPanel());
        if(entity != null){
            add(BorderLayout.CENTER,createEntityAttributesPanel());
        }
        revalidate();
    }

    private JPanel createAttributeCreationPanel(){
        JPanel attributeCreationPanel = new JPanel(new FlowLayout());
        JLabel createAttributeLabel = new JLabel("Create a new Attribute: ");
        final JComboBox<AttributeType> attributeTypeJComboBox = new JComboBox<AttributeType>(AttributeType.values());

        JLabel attributeNameLabel = new JLabel("Name:");
        JTextField attributeNameTextField = new JTextField();
        attributeNameTextField.setColumns(20);

        attributeCreationPanel.add(createAttributeLabel);
        attributeCreationPanel.add(attributeTypeJComboBox);
        attributeCreationPanel.add(attributeNameLabel);
        attributeCreationPanel.add(attributeNameTextField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e ->{
            if(TextUtils.isEmpty(attributeNameTextField)){
                JOptionPane.showMessageDialog(this,"Requires a non-empty attribute name!");
                return;
            }
            String name = attributeNameTextField.getText();
            AttributeType selectedType = (AttributeType)attributeTypeJComboBox.getSelectedItem();
            Attribute attribute = Attribute.createRawAttribute(name,selectedType);
            context.getCreatedAttributes().getValue().add(attribute);
            defaultListModel.addElement(attribute);
            attributeTypeJComboBox.setSelectedIndex(0);
            attributeNameTextField.setText("");
        });
        attributeCreationPanel.add(addButton);
        return attributeCreationPanel;
    }

    private JPanel createAttributeListPanel(){
        JPanel containerPanel = new JPanel(new BorderLayout());

        JScrollPane attributeListPanel = new JScrollPane();
        attributeListPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        attributeListPanel.setViewportView(attributeJList);
        containerPanel.add(BorderLayout.CENTER,attributeListPanel);

        JPanel buttonContainer = new JPanel(new BorderLayout());

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            Attribute selectedValue = attributeJList.getSelectedValue();
            if(selectedValue==null){
                return;
            }
            int option = JOptionPane
                    .showConfirmDialog(containerPanel,
                            "Are you sure you want to delete the attribute?",
                            "Warning",JOptionPane.YES_NO_OPTION);
            if(option==JOptionPane.YES_OPTION){
                List<Entity> entitiesStillUsingAttribute = checkAttributeNotUsed(selectedValue);
                if(!entitiesStillUsingAttribute.isEmpty()) {
                    JOptionPane.showMessageDialog(containerPanel,
                            "Attribute still used by the following entities:"+Joiner.on(",")
                    .join(entitiesStillUsingAttribute));
                    return;
                }
                context.getCreatedAttributes().getValue().remove(selectedValue);
                defaultListModel.removeElement(selectedValue);
            }
        });

        JButton addButton = new JButton("Add To Entity");
        addButton.addActionListener(e -> {
            Attribute selectedValue = attributeJList.getSelectedValue();
            if(selectedValue==null || entity==null){
                return;
            }
            if(checkIfEntityAlreadyHasTheAttributeWithSameName(selectedValue)){
                JOptionPane.showMessageDialog(this,"An attribute with this name already exists on the entity");
                return;
            }
            entity.getAttributes().add(selectedValue);
            attributeToEditingPanelMap.put(selectedValue,
                    new AttributeEditingPanel(context,this,selectedValue));
            entityAttributesPanel.add(attributeToEditingPanelMap.get(selectedValue));
            revalidate();
        });

        buttonContainer.add(BorderLayout.WEST,deleteButton);
        buttonContainer.add(BorderLayout.EAST,addButton);
        containerPanel.add(BorderLayout.SOUTH,buttonContainer);

        return containerPanel;
    }

    private boolean checkIfEntityAlreadyHasTheAttributeWithSameName(Attribute attribute){
        for(Attribute entityAttribute: entity.getAttributes()){
         if(attribute.getName().equals(entityAttribute.getName())){
             return true;
         }
        }
        return false;
    }

    private JScrollPane createEntityAttributesPanel(){
        JScrollPane containerScrollPane = new JScrollPane();
        containerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JPanel containerViewPanel = new JPanel();
        BoxLayout containerLayout = new BoxLayout(containerViewPanel,BoxLayout.PAGE_AXIS);
        containerViewPanel.setLayout(containerLayout);
        containerScrollPane.setViewportView(containerViewPanel);

        entityAttributesPanel = new JPanel();
        BoxLayout entityAttributesLayout = new BoxLayout(entityAttributesPanel,BoxLayout.PAGE_AXIS);
        entityAttributesPanel.setLayout(entityAttributesLayout);
        for(Attribute attribute : entity.getAttributes()){
            attributeToEditingPanelMap.put(attribute,new AttributeEditingPanel(context,this,attribute));
            entityAttributesPanel.add(attributeToEditingPanelMap.get(attribute));
        }
        containerViewPanel.add(entityAttributesPanel);
        return containerScrollPane;
    }

    private List<Entity> checkAttributeNotUsed(Attribute attribute){
        List<Entity> entitiesStillUsingTheAttribute = new ArrayList<>();
        for(Entity entity : context.getEntities().getValue()){
            for(Attribute usedAttribute: entity.getAttributes()){
                if(Attribute.compareValuedTypeWithRawType(usedAttribute,attribute)){
                    entitiesStillUsingTheAttribute.add(entity);
                }
            }
        }
        return entitiesStillUsingTheAttribute;
    }

    @Override
    public void removedAttribute(Attribute attribute) {
        AttributeEditingPanel attributeEditingPanel = attributeToEditingPanelMap.get(attribute);
        attributeToEditingPanelMap.remove(attribute);
        entity.getAttributes().remove(attribute);
        entityAttributesPanel.remove(attributeEditingPanel);
        revalidate();
    }

    public void storeEditions(){
        for(AttributeEditingPanel attributeEditingPanel : attributeToEditingPanelMap.values()){
            attributeEditingPanel.storeValues();
        }
    }
}
