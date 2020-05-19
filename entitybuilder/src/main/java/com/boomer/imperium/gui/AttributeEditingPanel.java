package com.boomer.imperium.gui;

import com.boomer.imperium.Context;
import com.boomer.imperium.scripts.mirrors.*;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AttributeEditingPanel extends JPanel {

    private static String[] BOOLEAN_VALUE_LABELS = new String[]{
            "Boolean Value:"
    };

    private static String[] INTEGER_VALUE_LABELS = new String[]{
            "Integer Value:"
    };

    private static String[] FLOAT_VALUE_LABELS = new String[]{
            "Float Value:"
    };

    private static String[] DOUBLE_VALUE_LABELS = new String[]{
            "Double Value:"
    };

    private static String[] LONG_VALUE_LABELS = new String[]{
            "Long Value:"
    };

    private static String[] STRING_VALUE_LABELS = new String[]{
            "String/Text Value:"
    };

    private static String[] VECTOR2_VALUE_LABELS = new String[]{
            "X Value:",
            "Y Value:",
    };

    private static String[] VECTOR3_VALUE_LABELS = new String[]{
            "X Value:",
            "Y Value:",
            "Z Value:",
    };

    private static String[] RECT_VALUE_LABELS = new String[]{
            "X Value:",
            "Y Value:",
            "Width Value:",
            "Height Value:",
    };

    private static String[] CIRCLE_VALUE_LABELS = new String[]{
            "X Value:",
            "Y Value:",
            "Radius Value:",
    };

    private static String[] ENTITY_VALUE_LABELS = new String[]{
            "Entity:",
    };

    private static String[] ENUM_VALUE_LABELS = new String[]{
            "Enum:",
            "Value:"
    };

    private static final ImmutableMap<AttributeType,String[]> TYPE_TO_LABEL_MAP = new ImmutableMap.Builder<AttributeType,String[]>()
            .put(AttributeType.BOOLEAN,BOOLEAN_VALUE_LABELS)
            .put(AttributeType.INTEGER,INTEGER_VALUE_LABELS)
            .put(AttributeType.FLOAT,FLOAT_VALUE_LABELS)
            .put(AttributeType.DOUBLE,DOUBLE_VALUE_LABELS)
            .put(AttributeType.LONG,LONG_VALUE_LABELS)
            .put(AttributeType.STRING,STRING_VALUE_LABELS)
            .put(AttributeType.VECTOR2,VECTOR2_VALUE_LABELS)
            .put(AttributeType.VECTOR3,VECTOR3_VALUE_LABELS)
            .put(AttributeType.RECT,RECT_VALUE_LABELS)
            .put(AttributeType.CIRCLE,CIRCLE_VALUE_LABELS)
            .put(AttributeType.ENTITY,ENTITY_VALUE_LABELS)
            .put(AttributeType.ENUM,ENUM_VALUE_LABELS)
            .build();

    private static final ImmutableMap<AttributeType,Supplier<SpinnerNumberModel>> TYPE_TO_MODEL_MAP
            = new ImmutableMap.Builder<AttributeType,Supplier<SpinnerNumberModel>>()
            .put(AttributeType.INTEGER, () -> new SpinnerNumberModel(0, null, null, 1))
            .put(AttributeType.FLOAT, () -> new SpinnerNumberModel(0f, null, null, 0.1f))
            .put(AttributeType.DOUBLE, () -> new SpinnerNumberModel(0d, null, null, 0.1d))
            .put(AttributeType.LONG, () -> new SpinnerNumberModel(0L, null, null, 1L))
            .put(AttributeType.VECTOR2, () -> new SpinnerNumberModel(0f, null, null, 0.1f))
            .put(AttributeType.VECTOR3, () -> new SpinnerNumberModel(0f, null, null, 0.1f))
            .put(AttributeType.RECT, () -> new SpinnerNumberModel(0f, null, null, 0.1f))
            .put(AttributeType.CIRCLE, () -> new SpinnerNumberModel(0f, null, null, 0.1f))
            .build();


    private final Context context;
    private final Attribute attribute;
    private final List<JLabel> attributeCreationValueLabels;
    private final JTextField value1TextField;
    private final JComboBox<Boolean> booleanJComboBox;
    private final JComboBox<String> enumNameJComboBox;
    private final DefaultComboBoxModel<String> enumNameComboBoxModel;
    private final JComboBox<String> enumValueJComboBox;
    private final DefaultComboBoxModel<String> enumValueComboBoxModel;
    private final JComboBox<String> entityNameComboBox;
    private final JSpinner value1Spinner;
    private final JSpinner value2Spinner;
    private final JSpinner value3Spinner;
    private final JSpinner value4Spinner;

    AttributeEditingPanel(Context context,Attribute attribute){
        super(new FlowLayout());
        this.context = context;
        setAlignmentX(Component.LEFT_ALIGNMENT);
        this.attribute = attribute;
        setBorder(new LineBorder(Color.LIGHT_GRAY,2));
        this.attributeCreationValueLabels = new ArrayList<>();
        JLabel attributeNameLabel = new JLabel("Name:");
        JTextField attributeNameTextField = new JTextField();
        attributeNameTextField.setColumns(20);
        attributeNameTextField.setText(attribute.getName());
        attributeNameTextField.setEnabled(false);

        final JLabel value1Label = new JLabel();
        final JLabel value2Label = new JLabel();
        final JLabel value3Label = new JLabel();
        final JLabel value4Label = new JLabel();

        attributeCreationValueLabels.add(value1Label);
        attributeCreationValueLabels.add(value2Label);
        attributeCreationValueLabels.add(value3Label);
        attributeCreationValueLabels.add(value4Label);

        value1TextField = new JTextField();
        value1Spinner = new JSpinner();
        value1TextField.setColumns(10);
        booleanJComboBox = new JComboBox<Boolean>(new Boolean[]{false, true});

        enumNameComboBoxModel = new DefaultComboBoxModel<>();
        enumValueComboBoxModel = new DefaultComboBoxModel<>();
        enumNameJComboBox = new JComboBox<String>();
        enumNameJComboBox.addActionListener(e -> {
            if(enumNameJComboBox.getSelectedItem() == null){
                return;
            }
            enumValueComboBoxModel.removeAllElements();
//            for(String enumValue : context.getEnumList().getValue().get(enumNameJComboBox)){
//                enumValueComboBoxModel.addElement(enumValue);
//            }
        });
        populateEnums();

        value2Spinner = new JSpinner();
        List<String> entityNames = ImmutableList.copyOf(Iterables.transform(context.getEntities().getValue(),
                entity -> entity.getName()));
        entityNameComboBox = new JComboBox<String>(Iterables.toArray(entityNames,String.class));
        enumValueJComboBox = new JComboBox<String>();

        value3Spinner = new JSpinner();
        value4Spinner = new JSpinner();

        add(attributeNameLabel);
        add(attributeNameTextField);

        add(value1Label);
        add(value1TextField);
        add(value1Spinner);
        add(booleanJComboBox);
        add(enumNameJComboBox);
        add(entityNameComboBox);

        add(value2Label);
        add(value2Spinner);
        add(enumValueJComboBox);

        add(value3Label);
        add(value3Spinner);

        add(value4Label);
        add(value4Spinner);

        booleanJComboBox.setVisible(false);
        entityNameComboBox.setVisible(false);
        enumNameJComboBox.setVisible(false);
        enumValueJComboBox.setVisible(false);
        value1TextField.setVisible(false);
        prepareSpinner(value1Spinner);
        prepareSpinner(value2Spinner);
        prepareSpinner(value3Spinner);
        prepareSpinner(value4Spinner);

        setLabels(TYPE_TO_LABEL_MAP.get(attribute.getAttributeType()));
        if(TYPE_TO_MODEL_MAP.containsKey(attribute.getAttributeType())) {
            value1Spinner.setModel(TYPE_TO_MODEL_MAP.get(attribute.getAttributeType()).get());
            value2Spinner.setModel(TYPE_TO_MODEL_MAP.get(attribute.getAttributeType()).get());
            value3Spinner.setModel(TYPE_TO_MODEL_MAP.get(attribute.getAttributeType()).get());
            value4Spinner.setModel(TYPE_TO_MODEL_MAP.get(attribute.getAttributeType()).get());
        }

        adjustUIForType();
        extractValues();

        setMaximumSize(getPreferredSize());
    }

    private void prepareSpinner(JSpinner spinner){
        Dimension d = spinner.getPreferredSize();
        d.width = 150;
        spinner.setPreferredSize(d);
        ((JSpinner.DefaultEditor)spinner.getEditor()).getTextField().setColumns(10);
        spinner.setVisible(false);
    }

    private void setLabels(String[] labelTexts){
        for(int i = 0; i < labelTexts.length; i++){
            attributeCreationValueLabels.get(i).setVisible(true);
            attributeCreationValueLabels.get(i).setText(labelTexts[i]);
        }
        for(int i = labelTexts.length; i < attributeCreationValueLabels.size() ; i++){
            attributeCreationValueLabels.get(i).setVisible(false);
        }
    }
    private void adjustUIForType(){
        switch (attribute.getAttributeType()){
            case BOOLEAN:
                booleanJComboBox.setVisible(true);
                entityNameComboBox.setVisible(false);
                enumNameJComboBox.setVisible(false);
                enumValueJComboBox.setVisible(false);
                value1TextField.setVisible(false);
                value1Spinner.setVisible(false);
                value2Spinner.setVisible(false);
                value3Spinner.setVisible(false);
                value4Spinner.setVisible(false);
                break;
            case INTEGER:
            case FLOAT:
            case DOUBLE:
            case LONG:
                value1TextField.setVisible(false);
                value1Spinner.setVisible(true);
                value2Spinner.setVisible(false);
                value3Spinner.setVisible(false);
                value4Spinner.setVisible(false);
                entityNameComboBox.setVisible(false);
                enumNameJComboBox.setVisible(false);
                enumValueJComboBox.setVisible(false);
                booleanJComboBox.setVisible(false);
                break;
            case STRING:
                value1TextField.setVisible(true);
                value1Spinner.setVisible(false);
                value2Spinner.setVisible(false);
                value3Spinner.setVisible(false);
                value4Spinner.setVisible(false);
                entityNameComboBox.setVisible(false);
                enumNameJComboBox.setVisible(false);
                enumValueJComboBox.setVisible(false);
                booleanJComboBox.setVisible(false);
                break;
            case VECTOR2:
                value1TextField.setVisible(false);
                value1Spinner.setVisible(true);
                value2Spinner.setVisible(true);
                value3Spinner.setVisible(false);
                value4Spinner.setVisible(false);
                entityNameComboBox.setVisible(false);
                enumNameJComboBox.setVisible(false);
                enumValueJComboBox.setVisible(false);
                booleanJComboBox.setVisible(false);
                break;
            case VECTOR3:
            case CIRCLE:
                value1TextField.setVisible(false);
                value1Spinner.setVisible(true);
                value2Spinner.setVisible(true);
                value3Spinner.setVisible(true);
                value4Spinner.setVisible(false);
                entityNameComboBox.setVisible(false);
                enumNameJComboBox.setVisible(false);
                enumValueJComboBox.setVisible(false);
                booleanJComboBox.setVisible(false);
                break;
            case RECT:
                value1TextField.setVisible(false);
                value1Spinner.setVisible(true);
                value2Spinner.setVisible(true);
                value3Spinner.setVisible(true);
                value4Spinner.setVisible(true);
                entityNameComboBox.setVisible(false);
                enumNameJComboBox.setVisible(false);
                enumValueJComboBox.setVisible(false);
                booleanJComboBox.setVisible(false);
                break;
            case ENTITY:
                value1TextField.setVisible(false);
                value1Spinner.setVisible(false);
                value2Spinner.setVisible(false);
                value3Spinner.setVisible(false);
                value4Spinner.setVisible(false);
                entityNameComboBox.setVisible(true);
                enumNameJComboBox.setVisible(false);
                enumValueJComboBox.setVisible(false);
                booleanJComboBox.setVisible(false);
                break;
            case ENUM:
                value1TextField.setVisible(false);
                value1Spinner.setVisible(false);
                value2Spinner.setVisible(false);
                value3Spinner.setVisible(false);
                value4Spinner.setVisible(false);
                entityNameComboBox.setVisible(false);
                enumNameJComboBox.setVisible(true);
                enumValueJComboBox.setVisible(true);
                booleanJComboBox.setVisible(false);
                break;
        }
    }

    private void extractValues(){
        switch (attribute.getAttributeType()){
            case BOOLEAN:
                if(attribute.getAttributeValue().getBooleanValue()==null){ break; }
                booleanJComboBox.setSelectedItem(attribute.getAttributeValue().getBooleanValue());
                break;
            case INTEGER:
                if(attribute.getAttributeValue().getIntegerValue()==null){ break; }
                value1Spinner.setValue(attribute.getAttributeValue().getIntegerValue());
                break;
            case FLOAT:
                if(attribute.getAttributeValue().getFloatValue()==null){ break; }
                value1Spinner.setValue(attribute.getAttributeValue().getFloatValue());
                break;
            case DOUBLE:
                if(attribute.getAttributeValue().getDoubleValue()==null){ break; }
                value1Spinner.setValue(attribute.getAttributeValue().getDoubleValue());
                break;
            case LONG:
                if(attribute.getAttributeValue().getLongValue()==null){ break; }
                value1Spinner.setValue(attribute.getAttributeValue().getLongValue());
                break;
            case STRING:
                if(attribute.getAttributeValue().getStringValue()==null){ break; }
                value1TextField.setText(attribute.getAttributeValue().getStringValue());
                break;
            case VECTOR2:
                if(attribute.getAttributeValue().getVector2Value()==null){ break; }
                value1Spinner.setValue(attribute.getAttributeValue().getVector2Value().getX());
                value2Spinner.setValue(attribute.getAttributeValue().getVector2Value().getY());
                break;
            case VECTOR3:
                if(attribute.getAttributeValue().getVector3Value()==null){ break; }
                value1Spinner.setValue(attribute.getAttributeValue().getVector3Value().getX());
                value2Spinner.setValue(attribute.getAttributeValue().getVector3Value().getY());
                value3Spinner.setValue(attribute.getAttributeValue().getVector3Value().getZ());
                break;
            case RECT:
                if(attribute.getAttributeValue().getRectValue()==null){ break; }
                value1Spinner.setValue(attribute.getAttributeValue().getRectValue().getX());
                value2Spinner.setValue(attribute.getAttributeValue().getRectValue().getY());
                value3Spinner.setValue(attribute.getAttributeValue().getRectValue().getWidth());
                value4Spinner.setValue(attribute.getAttributeValue().getRectValue().getHeight());
                break;
            case CIRCLE:
                if(attribute.getAttributeValue().getCircleValue()==null){ break; }
                value1Spinner.setValue(attribute.getAttributeValue().getCircleValue().getX());
                value2Spinner.setValue(attribute.getAttributeValue().getCircleValue().getY());
                value3Spinner.setValue(attribute.getAttributeValue().getCircleValue().getRadius());
                break;
            case ENTITY:
                if(attribute.getAttributeValue().getEntityValue()==null){ break; }
                value1TextField.setText(attribute.getAttributeValue().getEntityValue().getEntityName());
                break;
            case ENUM:
                if(attribute.getAttributeValue().getEnumValue()==null){ break; }
                enumNameJComboBox.setSelectedItem(attribute.getAttributeValue().getEnumValue().getName());
                enumValueJComboBox.setSelectedItem(attribute.getAttributeValue().getEnumValue().getValue());
                break;
        }
    }

    void storeValues(){
        switch (attribute.getAttributeType()){
            case BOOLEAN:
                attribute.setBooleanValue((Boolean)booleanJComboBox.getSelectedItem());
                break;
            case INTEGER:
                attribute.setIntegerValue((Integer)value1Spinner.getValue());
                break;
            case FLOAT:
                attribute.setFloatValue((Float) value1Spinner.getValue());
                break;
            case DOUBLE:
                attribute.setDoubleValue((Double) value1Spinner.getValue());
                break;
            case LONG:
                attribute.setLongValue((Long) value1Spinner.getValue());
                break;
            case STRING:
                attribute.setStringValue(value1TextField.getText());
                break;
            case VECTOR2:
                attribute.setVector2Value(new Vector2Mirror((Float) value1Spinner.getValue(),
                        (Float) value2Spinner.getValue()));
                break;
            case VECTOR3:
                attribute.setVector3Value(new Vector3Mirror((Float) value1Spinner.getValue(),
                        (Float) value2Spinner.getValue(),(Float) value3Spinner.getValue()));
                break;
            case RECT:
                attribute.setRectValue(new RectMirror(
                        (Float) value1Spinner.getValue(),
                        (Float) value2Spinner.getValue(),
                        (Float) value3Spinner.getValue(),
                        (Float) value4Spinner.getValue()));
                break;
            case CIRCLE:
                attribute.setCircleValue(new CircleMirror(
                        (Float) value1Spinner.getValue(),
                        (Float) value2Spinner.getValue(),
                        (Float) value3Spinner.getValue()));
                break;
            case ENTITY:
                attribute.setEntityValue(new EntityMirror(entityNameComboBox.getName()));
                break;
            case ENUM:
                attribute.setEnumValue(new EnumMirror((String)enumNameJComboBox.getSelectedItem(),
                        (String)enumValueJComboBox.getSelectedItem()));
                break;
        }
    }


    private void populateEnums(){
//        enumNameComboBoxModel.removeAllElements();
//        for(String enumName : context.getEnumList().getValue().keySet()){
//            enumNameComboBoxModel.addElement(enumName);
//        }
    }
}
