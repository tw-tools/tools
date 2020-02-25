package org.woehlke.tools;

import javax.swing.*;
import java.awt.*;

public class ToolsApplicationFrameLayout extends BoxLayout {

    /**
     * Creates a layout manager that will lay out components along the
     * given axis.
     *
     * @param rootPane the container that needs to be laid out
     * @throws AWTError if the value of {@code axis} is invalid
     */
    public ToolsApplicationFrameLayout(JRootPane rootPane) {
        super(rootPane, Y_AXIS);
    }
}
