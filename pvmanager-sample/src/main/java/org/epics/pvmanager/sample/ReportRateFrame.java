/**
 * Copyright (C) 2010-14 pvmanager developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.pvmanager.sample;

import java.util.List;
import org.epics.pvmanager.sim.SimulationDataSource;
import org.epics.pvmanager.PVReader;
import org.epics.pvmanager.PVManager;
import org.epics.pvmanager.PVReaderEvent;
import org.epics.pvmanager.PVReaderListener;
import org.epics.vtype.VDouble;
import static org.epics.pvmanager.vtype.ExpressionLanguage.*;
import static org.epics.pvmanager.util.Executors.*;
import org.epics.util.time.TimeDuration;
import static org.epics.util.time.TimeDuration.*;
import org.epics.util.time.Timestamp;

/**
 *
 * @author carcassi
 */
public class ReportRateFrame extends javax.swing.JFrame {

    /** Creates new form MockPVFrame */
    public ReportRateFrame() {
        PVManager.setDefaultNotificationExecutor(swingEDT());
        PVManager.setDefaultDataSource(SimulationDataSource.simulatedData());
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        valueLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        scanRateSpinner = new javax.swing.JSpinner();
        createPVButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        updateRateSpinner = new javax.swing.JSpinner();
        avgRateLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        currentRateLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("PV value:");

        valueLabel.setText("0");

        jLabel6.setText("UI scan rate (Hz):");

        scanRateSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));

        createPVButton.setText("Create ");
        createPVButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createPVButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("PV update rate (Hz):");

        updateRateSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 1000, 1));

        avgRateLabel.setText("0");

        jLabel2.setText("Avg rate (Hz):");

        jLabel4.setText("Current rate (Hz):");

        currentRateLabel.setText("0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(valueLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updateRateSpinner))
                    .addComponent(createPVButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(13, 13, 13)
                        .addComponent(currentRateLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scanRateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(avgRateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(valueLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(updateRateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(scanRateSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(avgRateLabel)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(currentRateLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(createPVButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private PVReader<VDouble> pv;
    private Timestamp overallTimestamp;
    private int overallCounter = 0;
    private int lastPeriodCounter;
    private Timestamp lastPeriodTimestamp;
    private final TimeDuration measureInterval = TimeDuration.ofSeconds(2);

    private void createPVButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createPVButtonActionPerformed
        if (pv != null) {
            pv.close();
        }
        
        overallTimestamp = Timestamp.now();
        overallCounter = 0;
        lastPeriodTimestamp = overallTimestamp;
        lastPeriodCounter = 0;
        valueLabel.setText("");
        avgRateLabel.setText("");
        currentRateLabel.setText("");
        
        double timeIntervalSec = (1.0 / (Integer) updateRateSpinner.getModel().getValue());
        String pvName = "gaussianNoise(0.0, 1.0, " + timeIntervalSec + ")";
        int scanRate = (Integer) scanRateSpinner.getModel().getValue();
        pv = PVManager.read(vDouble(pvName)).readListener(new PVReaderListener<VDouble>() {

            @Override
            public void pvChanged(PVReaderEvent<VDouble> event) {
                if (event.isValueChanged()) {
                    valueLabel.setText(Double.toString(pv.getValue().getValue()));
                    overallCounter++;
                    Timestamp now = Timestamp.now();
                    double avgRage = overallCounter / now.durationFrom(overallTimestamp).toSeconds();
                    avgRateLabel.setText("" + avgRage);
                    if (now.compareTo(lastPeriodTimestamp.plus(measureInterval)) >= 0) {
                        double currentRate = (overallCounter - lastPeriodCounter) / now.durationFrom(lastPeriodTimestamp).toSeconds();
                        currentRateLabel.setText("" + currentRate);
                        lastPeriodTimestamp = now;
                        lastPeriodCounter = overallCounter;
                    }
                }
            }
        }).maxRate(ofHertz(scanRate));
    }//GEN-LAST:event_createPVButtonActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReportRateFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel avgRateLabel;
    private javax.swing.JButton createPVButton;
    private javax.swing.JLabel currentRateLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSpinner scanRateSpinner;
    private javax.swing.JSpinner updateRateSpinner;
    private javax.swing.JLabel valueLabel;
    // End of variables declaration//GEN-END:variables

}