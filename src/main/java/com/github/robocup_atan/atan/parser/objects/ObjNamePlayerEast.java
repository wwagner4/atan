package com.github.robocup_atan.atan.parser.objects;

/*
 * #%L
 * Atan
 * %%
 * Copyright (C) 2003 - 2014 Atan
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

//~--- non-JDK imports --------------------------------------------------------

import com.github.robocup_atan.atan.model.ControllerCoach;
import com.github.robocup_atan.atan.model.ControllerPlayer;
import com.github.robocup_atan.atan.model.ControllerTrainer;

/**
 * The parser object for east players.
 *
 * @author Atan
 */
public class ObjNamePlayerEast implements ObjName {
    boolean goalie;
    int     number;

    /**
     * Constructor for east players.
     *
     * @param number The players uniform number.
     * @param goalie Is this player a goalie?
     */
    public ObjNamePlayerEast(int number, boolean goalie) {
        this.number = number;
        this.goalie = goalie;
    }

    /** {@inheritDoc} */
    public void infoSeeFromEast(ControllerPlayer c, double distance, double direction, double distChange,
                                double dirChange, double bodyFacingDirection, double headFacingDirection) {
        c.infoSeePlayerOwn(number, goalie, distance, direction, distChange, dirChange, bodyFacingDirection,
                           headFacingDirection);
    }

    /** {@inheritDoc} */
    public void infoSeeFromWest(ControllerPlayer c, double distance, double direction, double distChange,
                                double dirChange, double bodyFacingDirection, double headFacingDirection) {
        c.infoSeePlayerOther(number, goalie, distance, direction, distChange, dirChange, bodyFacingDirection,
                             headFacingDirection);
    }

    /** {@inheritDoc} */
    public void infoSeeFromEast(ControllerCoach c, double x, double y, double deltaX, double deltaY, double bodyAngle,
                                double neckAngle) {
        c.infoSeePlayerOwn(number, goalie, x, y, deltaX, deltaY, bodyAngle, neckAngle);
    }

    /** {@inheritDoc} */
    public void infoSeeFromWest(ControllerCoach c, double x, double y, double deltaX, double deltaY, double bodyAngle,
                                double neckAngle) {
        c.infoSeePlayerOther(number, goalie, x, y, deltaX, deltaY, bodyAngle, neckAngle);
    }

    /** {@inheritDoc} */
    public void infoSee(ControllerTrainer c) {}
}
