package com.boomer.imperium.gui;

import com.boomer.imperium.Context;
import com.boomer.imperium.scripts.ScriptMirror;
import com.boomer.imperium.scripts.mirrors.Attribute;
import com.boomer.imperium.scripts.mirrors.AttributeType;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ItemListener;

public class ScriptAttributesPanel extends JPanel {

    private final Context context;
    private final ScriptMirror scriptMirror;

    public ScriptAttributesPanel(Context context, ScriptMirror scriptMirror){
        BoxLayout scriptAttributesPanel = new BoxLayout(this,BoxLayout.PAGE_AXIS);
        setLayout(scriptAttributesPanel);
        this.context = context;
        this.scriptMirror = scriptMirror;
        processAttributes();
    }

    private void processAttributes(){
        for(String attributeName : scriptMirror.getAttributeList().getValue().keySet()){
            Attribute attribute = scriptMirror.getAttributeList().getValue().get(attributeName);
            AttributeType attributeType = attribute.getAttributeType();
            JPanel attributePanel = new JPanel(new FlowLayout());
            attributePanel.add(new JLabel(attributeName+":"));
            if(attributeType.equals(AttributeType.STRING)){
                setStringPanel(attributePanel,attribute);
                continue;
            }
            if(attributeType.equals(AttributeType.BOOLEAN)){
                setBooleanPanel(attributePanel,attribute);
                continue;
            }
            if(attributeType.equals(AttributeType.INTEGER)
                    || attributeType.equals(AttributeType.FLOAT)
                    || attributeType.equals(AttributeType.DOUBLE)
                    || attributeType.equals(AttributeType.LONG)){
                setNumericalPanel(attributePanel,attribute);
                continue;
            }
            if(attributeType.equals(AttributeType.VECTOR2)){
                setVector2Panel(attributePanel,attribute);
                continue;
            }
            if(attributeType.equals(AttributeType.VECTOR3)){
                setVector3Panel(attributePanel,attribute);
                continue;
            }
            if(attributeType.equals(AttributeType.RECT)){
                setCirclePanel(attributePanel,attribute);
                continue;
            }
            if(attributeType.equals(AttributeType.CIRCLE)){
                setRectPanel(attributePanel,attribute);
                continue;
            }
        }
        revalidate();
    }

    private void setStringPanel(JPanel container,Attribute attribute){
        JTextField stringTextField = new JTextField();
        stringTextField.getDocument().addDocumentListener(getDocumentListener(attribute,stringTextField));
        container.add(stringTextField);
    }

    private void setBooleanPanel(JPanel container,Attribute attribute){
        JComboBox<Boolean> booleanJComboBox = new JComboBox<>(new Boolean[]{false,true});
        booleanJComboBox.addItemListener(getComboBoxListener(attribute,booleanJComboBox));
        container.add(booleanJComboBox);
    }

    private void setNumericalPanel(JPanel container,Attribute attribute){
        JSpinner numericalSpinner = new JSpinner();
        SpinnerNumberModel spinnerNumberModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        spinnerNumberModel.addChangeListener(getNumericalChangeListener(attribute,spinnerNumberModel));
        numericalSpinner.setModel(spinnerNumberModel);
        container.add(numericalSpinner);
    }

    private void setVector2Panel(JPanel container,Attribute attribute){
        JLabel xLabel = new JLabel("x:");
        JLabel yLabel = new JLabel("y:");

        JSpinner xSpinner = new JSpinner();
        SpinnerNumberModel xSpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        xSpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getVector2Value().setX(xSpinnerModel.getNumber().floatValue()));
        xSpinner.setModel(xSpinnerModel);

        JSpinner ySpinner = new JSpinner();
        SpinnerNumberModel ySpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        ySpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getVector2Value().setY(ySpinnerModel.getNumber().floatValue()));
        ySpinner.setModel(ySpinnerModel);

        container.add(xLabel);
        container.add(xSpinner);
        container.add(yLabel);
        container.add(ySpinner);
    }

    private void setVector3Panel(JPanel container,Attribute attribute){
        JLabel xLabel = new JLabel("x:");
        JLabel yLabel = new JLabel("y:");
        JLabel zLabel = new JLabel("z:");

        JSpinner xSpinner = new JSpinner();
        SpinnerNumberModel xSpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        xSpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getVector3Value().setX(xSpinnerModel.getNumber().floatValue()));
        xSpinner.setModel(xSpinnerModel);

        JSpinner ySpinner = new JSpinner();
        SpinnerNumberModel ySpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        ySpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getVector3Value().setY(ySpinnerModel.getNumber().floatValue()));
        ySpinner.setModel(ySpinnerModel);

        JSpinner zSpinner = new JSpinner();
        SpinnerNumberModel zSpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        zSpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getVector3Value().setY(zSpinnerModel.getNumber().floatValue()));
        zSpinner.setModel(zSpinnerModel);

        container.add(xLabel);
        container.add(xSpinner);
        container.add(yLabel);
        container.add(ySpinner);
        container.add(zLabel);
        container.add(zSpinner);
    }

    private void setRectPanel(JPanel container,Attribute attribute){
        JLabel xLabel = new JLabel("x:");
        JLabel yLabel = new JLabel("y:");
        JLabel widthLabel = new JLabel("width:");
        JLabel heightLabel = new JLabel("height:");


        JSpinner xSpinner = new JSpinner();
        SpinnerNumberModel xSpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        xSpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getRectValue().setX(xSpinnerModel.getNumber().floatValue()));
        xSpinner.setModel(xSpinnerModel);

        JSpinner ySpinner = new JSpinner();
        SpinnerNumberModel ySpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        ySpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getRectValue().setY(ySpinnerModel.getNumber().floatValue()));
        ySpinner.setModel(ySpinnerModel);

        JSpinner widthSpinner = new JSpinner();
        SpinnerNumberModel widthSpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        widthSpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getRectValue().setWidth(widthSpinnerModel.getNumber().floatValue()));
        widthSpinner.setModel(widthSpinnerModel);

        JSpinner heightSpinner = new JSpinner();
        SpinnerNumberModel heightSpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        heightSpinner.addChangeListener(e -> attribute.getAttributeValue().getRectValue().setHeight(heightSpinnerModel.getNumber().floatValue()));
        heightSpinner.setModel(heightSpinnerModel);

        container.add(xLabel);
        container.add(xSpinner);
        container.add(yLabel);
        container.add(ySpinner);
        container.add(widthLabel);
        container.add(widthSpinner);
        container.add(heightLabel);
        container.add(heightSpinner);
    }

    private void setCirclePanel(JPanel container,Attribute attribute){
        JLabel xLabel = new JLabel("x:");
        JLabel yLabel = new JLabel("y:");
        JLabel radiusLabel = new JLabel("radius:");

        JSpinner xSpinner = new JSpinner();
        SpinnerNumberModel xSpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        xSpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getCircleValue().setX(xSpinnerModel.getNumber().floatValue()));
        xSpinner.setModel(xSpinnerModel);

        JSpinner ySpinner = new JSpinner();
        SpinnerNumberModel ySpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        ySpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getCircleValue().setY(ySpinnerModel.getNumber().floatValue()));
        ySpinner.setModel(ySpinnerModel);

        JSpinner radiusSpinner = new JSpinner();
        SpinnerNumberModel radiusSpinnerModel = new SpinnerNumberModel(0d, null, null, 0.1D);
        radiusSpinnerModel.addChangeListener(e -> attribute.getAttributeValue().getCircleValue().setRadius(radiusSpinnerModel.getNumber().floatValue()));
        radiusSpinner.setModel(radiusSpinnerModel);

        container.add(xLabel);
        container.add(xSpinner);
        container.add(yLabel);
        container.add(ySpinner);
        container.add(radiusLabel);
        container.add(radiusSpinner);
    }

    private DocumentListener getDocumentListener(Attribute attribute,JTextField textField){
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                attribute.getAttributeValue().setStringValue(textField.getText());
            }
        };
    }

    private ItemListener getComboBoxListener(Attribute attribute, JComboBox<Boolean> comboBox){
        return e -> attribute.setBooleanValue((Boolean)comboBox.getSelectedItem());
    }

    private ChangeListener getNumericalChangeListener(Attribute attribute,SpinnerNumberModel spinnerNumberModel){
        return e -> {
            if(attribute.getAttributeType().equals(AttributeType.INTEGER)){
                attribute.setIntegerValue(spinnerNumberModel.getNumber().intValue());
                return;
            }
            if(attribute.getAttributeType().equals(AttributeType.FLOAT)){
                attribute.setFloatValue(spinnerNumberModel.getNumber().floatValue());
                return;
            }
            if(attribute.getAttributeType().equals(AttributeType.DOUBLE)){
                attribute.setDoubleValue(spinnerNumberModel.getNumber().doubleValue());
                return;
            }
            if(attribute.getAttributeType().equals(AttributeType.INTEGER)){
                attribute.setLongValue(spinnerNumberModel.getNumber().longValue());
                return;
            }
        };
    }


}
