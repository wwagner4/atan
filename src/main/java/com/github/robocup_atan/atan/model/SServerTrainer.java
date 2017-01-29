package com.github.robocup_atan.atan.model;

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

import com.github.robocup_atan.atan.model.enums.PlayMode;

import com.github.robocup_atan.atan.parser.CommandFilter;
import com.github.robocup_atan.atan.parser.Filter;
import com.github.robocup_atan.atan.parser.trainer.CmdParserTrainer;

import org.apache.log4j.Logger;

//~--- JDK imports ------------------------------------------------------------

import java.io.IOException;
import java.io.StringReader;

/**
 * A simple implementation of AbstractUDPClient for Trainers.
 *
 * @author Atan
 */
public class SServerTrainer extends AbstractUDPClient implements ActionsTrainer {
    private static final int           TRAINER_PORT   = 6001;
    private static Logger              log            = Logger.getLogger(SServerTrainer.class);
    private String                     initMessage    = null;
    private final CmdParserTrainer     parser         = new CmdParserTrainer(new StringReader(""));
    private final Filter               filter         = new Filter();
    private final CommandFactory       commandFactory = new CommandFactory();
    private final SServerCommandBuffer cmdBuf         = new SServerCommandBuffer();
    private ControllerTrainer          controller;
    private String                     teamName;

    /**
     * A part constructor for SServerTrainer (assumes localhost:6001)
     *
     * @param teamName The team name.
     * @param t A ControllerTrainer.
     */
    public SServerTrainer(String teamName, ControllerTrainer t) {
        this(teamName, t, TRAINER_PORT, "localhost");
    }

    /**
     * The full constructor for SServerTrainer
     *
     * @param teamName The teams name.
     * @param t A ControllerTrainer.
     * @param port The port to connect to.
     * @param hostname The host address.
     */
    public SServerTrainer(String teamName, ControllerTrainer t, int port, String hostname) {
        super(port, hostname);
        this.teamName   = teamName;
        this.controller = t;
    }

    /** {@inheritDoc} */
    @Override
    public String getInitMessage() {
        return initMessage;
    }

    /**
     * Connects to the server via AbstractUDPClient.
     */
    public void connect() {
        CommandFactory f = new CommandFactory();
        f.addTrainerInitCommand();
        initMessage = f.next();
        super.start();
        super.setName("Trainer");
    }

    /** {@inheritDoc} */
    @Override
    public void received(String msg) throws IOException {
        try {
            if (log.isDebugEnabled()) {
                log.debug("<---'" + msg + "'");
            }
            filter.run(msg, cmdBuf);
            cmdBuf.takeStep(controller, parser, this);
            while (commandFactory.hasNext()) {
                String cmd = commandFactory.next();
                if (log.isDebugEnabled()) {
                    log.debug("--->'" + cmd + "'");
                }
                send(cmd);
                pause(50);
            }
        } catch (Exception ex) {
            log.error("Error while receiving message: " + msg + " " + ex.getMessage(), ex);
        }
    }

    /** {@inheritDoc} */
    public void changePlayMode(PlayMode playMode) {
        this.commandFactory.addChangePlayModeCommand(playMode);
    }

    /** {@inheritDoc} */
    public void movePlayer(ActionsPlayer p, double x, double y) {
        this.commandFactory.addMovePlayerCommand(p, x, y);
    }

    /** {@inheritDoc} */
    public void moveBall(double x, double y) {
        this.commandFactory.addMoveBallCommand(x, y);
    }

    /** {@inheritDoc} */
    public void checkBall() {
        this.commandFactory.addCheckBallCommand();
    }

    /** {@inheritDoc} */
    public void startGame() {
        this.commandFactory.addStartCommand();
    }

    /** {@inheritDoc} */
    public void recover() {
        this.commandFactory.addRecoverCommand();
    }

    /** {@inheritDoc} */
    public void eye(boolean eyeOn) {
        this.commandFactory.addEyeCommand(eyeOn);
    }

    /** {@inheritDoc} */
    public void ear(boolean earOn) {
        this.commandFactory.addEarCommand(earOn);
    }

    /** {@inheritDoc} */
    public void look() {
        this.commandFactory.addLookCommand();
    }

    /** {@inheritDoc} */
    public void teamNames() {
        this.commandFactory.addTeamNamesCommand();
    }

    /** {@inheritDoc} */
    public void changePlayerType(String teamName, int unum, int playerType) {
        this.commandFactory.addChangePlayerTypeCommand(teamName, unum, playerType);
    }

    /** {@inheritDoc} */
    public void say(String message) {
        this.commandFactory.addSayCommand(message);
    }

    /** {@inheritDoc} */
    public void bye() {
        this.commandFactory.addByeCommand();
    }

    /** {@inheritDoc} */
    public void handleError(String error) {
        log.error(error);
    }

    /**
     * Pause the thread.
     * @param ms How long to pause the thread for (in ms).
     */
    private synchronized void pause(int ms) {
        try {
            this.wait(ms);
        } catch (InterruptedException ex) {
            log.warn("Interrupted Exception ", ex);
        }
    }

    /**
     * A private controller-style filter
     * @author Atan
     */
    private static class SServerCommandBuffer implements CommandFilter {
        private String changePlayerTypeCommand = null;
        private String errorCommand            = null;
        private String hearCommand             = null;
        private String initCommand             = null;
        private String okCommand               = null;
        private String playerParamCommand      = null;
        private String playerTypeCommand       = null;
        private String seeCommand              = null;
        private String senseBodyCommand        = null;
        private String serverParamCommand      = null;
        private String warningCommand          = null;

        /**
         * @inheritDoc
         */
        public void seeCommand(String cmd) {
            seeCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        public void hearCommand(String cmd) {
            hearCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        public void senseBodyCommand(String cmd) {
            senseBodyCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        public void initCommand(String cmd) {
            initCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        public void errorCommand(String cmd) {
            errorCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        public void serverParamCommand(String cmd) {
            serverParamCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        public void playerParamCommand(String cmd) {
            playerParamCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        public void playerTypeCommand(String cmd) {
            playerTypeCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        public void changePlayerTypeCommand(String cmd) {
            changePlayerTypeCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        public void okCommand(String cmd) {
            okCommand = cmd;
        }

        /**
         * @inheritDoc
         */
        public void warningCommand(String cmd) {
            warningCommand = cmd;
        }

        /**
         * Decide the step to take.
         * @param controller
         * @param parser
         * @param c
         * @throws Exception
         */
        public void takeStep(ControllerTrainer controller, CmdParserTrainer parser, ActionsTrainer c) throws Exception {
            if (seeCommand != null) {
                parser.parseSeeCommand(seeCommand, controller, c);
                seeCommand = null;
            }
            if (hearCommand != null) {
                parser.parseHearCommand(hearCommand, controller, c);
                hearCommand = null;
            }
            if (senseBodyCommand != null) {
                parser.parseSenseBodyCommand(senseBodyCommand, controller, c);
                senseBodyCommand = null;
            }
            if (initCommand != null) {
                parser.parseInitCommand(initCommand, controller, c);
                initCommand = null;
            }
            if (okCommand != null) {
                parser.parseOkCommand(okCommand, controller, c);
                okCommand = null;
            }
            if (warningCommand != null) {
                parser.parseWarningCommand(warningCommand, controller, c);
                warningCommand = null;
            }
            if (serverParamCommand != null) {
                parser.parseServerParamCommand(serverParamCommand, controller, c);
                serverParamCommand = null;
            }
            if (playerParamCommand != null) {
                parser.parsePlayerParamCommand(playerParamCommand, controller, c);
                playerParamCommand = null;
            }
            if (playerTypeCommand != null) {
                parser.parsePlayerTypeCommand(playerTypeCommand, controller, c);
                playerTypeCommand = null;
            }
            if (changePlayerTypeCommand != null) {
                parser.parseChangePlayerTypeCommand(changePlayerTypeCommand, controller, c);
                changePlayerTypeCommand = null;
            }
            if (errorCommand != null) {
                parser.parseErrorCommand(errorCommand, controller, c);
                errorCommand = null;
            }
        }
    }
}
