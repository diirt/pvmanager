/**
 * Copyright (C) 2010-14 pvmanager developers. See COPYRIGHT.TXT
 * All rights reserved. Use is subject to license terms. See LICENSE.TXT
 */
package org.epics.pvmanager.sample;

import org.epics.graphene.BubbleGraph2DRendererUpdate;
import org.epics.graphene.InterpolationScheme;
import org.epics.graphene.ScatterGraph2DRendererUpdate;
import org.epics.pvmanager.graphene.ScatterGraph2DExpression;
import static org.epics.pvmanager.formula.ExpressionLanguage.*;
import org.epics.pvmanager.graphene.BubbleGraph2DExpression;
import static org.epics.pvmanager.graphene.ExpressionLanguage.*;

/**
 *
 * @author carcassi
 */
public class BubbleGraphApp extends BaseGraphApp<BubbleGraph2DRendererUpdate> {

    private String xColumn = null;
    private String yColumn = null;
    private String sizeColumn = null;
    private String colorColumn = null;
    
    public BubbleGraphApp() {
        dataFormulaField.setModel(new javax.swing.DefaultComboBoxModel<String>(
                new String[] { "=tableOf(column(\"X\", range(-10,10)), column(\"Y\", 'sim://noiseWaveform'), column(\"SIZE\", 'sim://gaussianWaveform'), column(\"COLOR\", 'sim://sineWaveform'))",
                "=tableOf(column(\"X\", range(-10,10)), column(\"Y\", 'sim://noiseWaveform'))",
                "=tableOf(column(\"X\", arrayOf(1,2,3,4,5)), column(\"Y\", arrayOf(3,1,4,2,5)), column(\"NAMES\", arrayOf(\"A\", \"A\", \"A\", \"B\", \"B\")))"}));
    }

    @Override
    protected BubbleGraph2DExpression createExpression(String dataFormula) {
        BubbleGraph2DExpression plot = bubbleGraphOf(formula(dataFormula),
                    formulaArg(xColumn),
                    formulaArg(yColumn),
                    formulaArg(sizeColumn),
                    formulaArg(colorColumn));
        return plot;
    }

    @Override
    protected void openConfigurationDialog() {
        BubbleGraphDialog dialog = new BubbleGraphDialog(new javax.swing.JFrame(), true, this);
        dialog.setTitle("Configure...");
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public String getXColumn() {
        return xColumn;
    }

    public void setXColumn(String xColumn) {
        this.xColumn = xColumn;
        reconnect();
    }

    public String getYColumn() {
        return yColumn;
    }

    public void setYColumn(String yColumn) {
        this.yColumn = yColumn;
        reconnect();
    }

    public String getSizeColumn() {
        return sizeColumn;
    }

    public void setSizeColumn(String sizeColumn) {
        this.sizeColumn = sizeColumn;
        reconnect();
    }

    public String getColorColumn() {
        return colorColumn;
    }

    public void setColorColumn(String colorColumn) {
        this.colorColumn = colorColumn;
        reconnect();
    }
    
    public static void main(String[] args) {
        main(BubbleGraphApp.class);
    }
    
}
