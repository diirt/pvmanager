/*
 * Copyright 2008-2010 Brookhaven National Laboratory
 * All rights reserved. Use is subject to license terms.
 */

/*
 * MockWaterfallPlot.java
 *
 * Created on Jan 10, 2011, 3:59:13 PM
 */

package org.epics.pvmanager.test;

import java.awt.Color;
import org.epics.pvmanager.CompositeDataSource;
import org.epics.pvmanager.jca.JCADataSource;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.epics.pvmanager.PVReader;
import org.epics.pvmanager.PVManager;
import org.epics.pvmanager.PVValueChangeListener;
import org.epics.pvmanager.ThreadSwitch;
import org.epics.pvmanager.data.Util;
import org.epics.pvmanager.data.VImage;
import org.epics.pvmanager.extra.ColorScheme;
import org.epics.pvmanager.extra.WaterfallPlot;
import org.epics.pvmanager.extra.WaterfallPlotParameters;
import org.epics.pvmanager.sim.SimulationDataSource;
import org.epics.pvmanager.util.TimeDuration;
import static org.epics.pvmanager.data.ExpressionLanguage.*;
import static org.epics.pvmanager.extra.ExpressionLanguage.*;
import static org.epics.pvmanager.extra.WaterfallPlotParameters.*;

/**
 *
 * @author carcassi
 */
public class MockWaterfallPlot extends javax.swing.JFrame {

    /** Creates new form MockWaterfallPlot */
    public MockWaterfallPlot() {
        PVManager.setDefaultNotificationExecutor(ThreadSwitch.onSwingEDT());
        CompositeDataSource dataSource = new CompositeDataSource();
        dataSource.putDataSource("sim", SimulationDataSource.simulatedData());
        dataSource.putDataSource("epics", new JCADataSource());
        dataSource.setDefaultDataSource("sim");
        PVManager.setDefaultDataSource(dataSource);
        initComponents();
        WaterfallPlotParameters defaults = WaterfallPlotParameters.defaults();
        adaptiveRangeField.setSelected(defaults.isAdaptiveRange());
        scrollDownField.setSelected(defaults.isScrollDown());
        heightField.setValue(defaults.getHeight());
        pixelDurationField.setValue(defaults.getPixelDuration().getNanoSec() / 1000000);
    }

    private PVReader<VImage> pv;
    private WaterfallPlot plot;

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        plotLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pvName = new javax.swing.JTextField();
        lastError = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        heightField = new javax.swing.JSpinner();
        adaptiveRangeField = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        pixelDurationField = new javax.swing.JSpinner();
        scrollDownField = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        plotLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel1.setText("PV Name:");

        pvName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pvNameActionPerformed(evt);
            }
        });

        lastError.setEditable(false);

        jLabel2.setText("Max Height:");

        heightField.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(50), Integer.valueOf(1), null, Integer.valueOf(1)));
        heightField.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                heightFieldStateChanged(evt);
            }
        });

        adaptiveRangeField.setText("Adaptive range");
        adaptiveRangeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adaptiveRangeFieldActionPerformed(evt);
            }
        });

        jLabel3.setText("ms per pixel:");

        pixelDurationField.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(100), Integer.valueOf(1), null, Integer.valueOf(1)));
        pixelDurationField.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pixelDurationFieldStateChanged(evt);
            }
        });

        scrollDownField.setSelected(true);
        scrollDownField.setText("Latest on top");
        scrollDownField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scrollDownFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(adaptiveRangeField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollDownField)
                        .addGap(239, 239, 239))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(plotLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pvName, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
                            .addComponent(lastError, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(heightField, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(pixelDurationField, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(pvName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(heightField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(pixelDurationField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adaptiveRangeField)
                    .addComponent(scrollDownField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(plotLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lastError, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pvNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pvNameActionPerformed
        if (pv != null)
            pv.close();

        plot = waterfallPlotOf(vDoubleArray(pvName.getText())).with(
                colorScheme(ColorScheme.multipleRangeGradient(Color.RED, Color.YELLOW, Color.BLACK, Color.WHITE, Color.YELLOW, Color.RED)),
                backgroundColor(getBackground().getRGB()),
                adaptiveRange(adaptiveRangeField.isSelected()),
                scrollDown(scrollDownField.isSelected()),
                height(((Number) heightField.getValue()).intValue()),
                pixelDuration(TimeDuration.ms(((Number) pixelDurationField.getValue()).intValue())));
        pv = PVManager.read(plot).andNotify(ThreadSwitch.onSwingEDT())
                .atHz(50);
        pv.addPVValueChangeListener(new PVValueChangeListener() {

            @Override
            public void pvValueChanged() {
                setLastError(pv.lastException());
                if (pv.getValue() != null) {
                    BufferedImage image = Util.toImage(pv.getValue());
                    plotLabel.setIcon(new ImageIcon(image));
                }
            }
        });
    }//GEN-LAST:event_pvNameActionPerformed

    private void heightFieldStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_heightFieldStateChanged
        if (plot != null) {
            plot.with(height(((Number) heightField.getValue()).intValue()));
        }
    }//GEN-LAST:event_heightFieldStateChanged

    private void pixelDurationFieldStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_pixelDurationFieldStateChanged
        if (plot != null) {
            plot.with(pixelDuration(TimeDuration.ms(((Number) pixelDurationField.getValue()).intValue())));
        }
    }//GEN-LAST:event_pixelDurationFieldStateChanged

    private void scrollDownFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scrollDownFieldActionPerformed
        if (plot != null) {
            plot.with(scrollDown(scrollDownField.isSelected()));
        }
    }//GEN-LAST:event_scrollDownFieldActionPerformed

    private void adaptiveRangeFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adaptiveRangeFieldActionPerformed
        if (plot != null) {
            plot.with(adaptiveRange(adaptiveRangeField.isSelected()));
        }
    }//GEN-LAST:event_adaptiveRangeFieldActionPerformed


    private void setLastError(Exception ex) {
        if (ex != null) {
            lastError.setText(ex.getMessage());
            Logger.getLogger(MockWaterfallPlot.class.getName()).log(Level.WARNING, "Error", ex);
        } else
            lastError.setText("");
    }


    final BufferedImage finalBuffer = new BufferedImage(100, 100, BufferedImage.TYPE_3BYTE_BGR);

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MockWaterfallPlot().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox adaptiveRangeField;
    private javax.swing.JSpinner heightField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField lastError;
    private javax.swing.JSpinner pixelDurationField;
    private javax.swing.JLabel plotLabel;
    private javax.swing.JTextField pvName;
    private javax.swing.JCheckBox scrollDownField;
    // End of variables declaration//GEN-END:variables

}
