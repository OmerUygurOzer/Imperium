package com.boomer.imperium.gui;

import com.boomer.imperium.model.NewContextData;
import com.boomer.imperium.model.NewContextDataReceiver;
import com.boomer.imperium.gui.util.TextUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

final class NewProjectDialog {

    private static final int BORDER_WIDTH = 10;

    private NewProjectDialog(){}

    public static JDialog show(JFrame container,NewContextDataReceiver
            newContextDataReceiver){
        JDialog dialog =  new NewProjectDialogPane(container,newContextDataReceiver);
        dialog.pack();
        dialog.setVisible(true);
        return dialog;
    }

    private static final class NewProjectDialogPane extends JDialog{

        public NewProjectDialogPane(JFrame jFrame,NewContextDataReceiver
                newContextDataReceiver){
            super(jFrame);
            setContentPane(createContainerPanel(this,jFrame,newContextDataReceiver));
        }
    }

    private static final JPanel createContainerPanel(final NewProjectDialogPane dialogPane,final JFrame containerFrame,final NewContextDataReceiver
            newContextDataReceiver){
        JPanel result = new JPanel();
        result.setBorder( BorderFactory.createEmptyBorder( BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH, BORDER_WIDTH));
        GroupLayout layout = new GroupLayout( result );
        result.setLayout(layout);
        layout.setAutoCreateGaps(true);

        JLabel nameLabel = new JLabel( "Name:" );
        final JTextField nameTextField = new JTextField( 20 );
        JLabel javaPackageNameLabel = new JLabel( "Java Package:" );
        final JTextField javaPackageNameTextField = new JTextField( 20 );
        JLabel filePathLabel = new JLabel( "File Path:" );
        final JTextField filePathTextField = new JTextField( 60 );
        filePathTextField.setEnabled(false);
        JButton filePathButton = new JButton("Find");
        filePathButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Where should your new project be created?");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int option = chooser.showOpenDialog(containerFrame);
                if(option == JFileChooser.APPROVE_OPTION){
                    File file = chooser.getSelectedFile();
                    filePathTextField.setText(file.getAbsolutePath());
                }
            }
        });
        JButton createProjectButton = new JButton("Create Project");
        createProjectButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(TextUtils.isEmpty(nameTextField)){
                    JOptionPane.showMessageDialog(containerFrame,"Project must have a name!");
                    return;
                }
                if(TextUtils.isEmpty(javaPackageNameTextField)){
                    JOptionPane.showMessageDialog(containerFrame,"Project must have java package name!");
                    return;
                }
                if(TextUtils.isEmpty(filePathTextField)){
                    JOptionPane.showMessageDialog(containerFrame,"You need to select a folder to create your project in!");
                    return;
                }
                newContextDataReceiver.receiveNewContextData(new NewContextData(
                        filePathTextField.getText(),
                        javaPackageNameTextField.getText(),
                        nameTextField.getText()));
                dialogPane.setVisible(false);
                dialogPane.dispose();
            }
        });
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogPane.setVisible(false);
                dialogPane.dispose();
            }
        });

        layout.setHorizontalGroup( layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING )
                        .addComponent(nameLabel)
                        .addComponent(javaPackageNameLabel)
                        .addComponent(filePathLabel)
                        .addComponent(cancelButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(nameTextField)
                        .addComponent(javaPackageNameTextField)
                        .addComponent(filePathTextField)
                        .addComponent(createProjectButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(filePathButton)));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(nameLabel)
                        .addComponent(nameTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(javaPackageNameLabel)
                        .addComponent(javaPackageNameTextField))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(filePathLabel)
                        .addComponent(filePathTextField)
                        .addComponent(filePathButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(cancelButton)
                        .addComponent(createProjectButton)));
        return result;

    }


}
