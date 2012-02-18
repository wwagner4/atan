package com.github.robocup_atan.atan.model;

//~--- non-JDK imports --------------------------------------------------------

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author Atan
 */
@RunWith(Suite.class)
@Suite.SuiteClasses( {
    com.github.robocup_atan.atan.model.ControllerTrainerTest.class, com.github.robocup_atan.atan.model.AbstractTeamTest.class, com.github.robocup_atan.atan.model.ControllerCoachTest.class,
    com.github.robocup_atan.atan.model.enums.EnumsSuite.class, com.github.robocup_atan.atan.model.ByteBufferTest.class, com.github.robocup_atan.atan.model.AbstractUDPClientTest.class,
    com.github.robocup_atan.atan.model.XPMImageTest.class, com.github.robocup_atan.atan.model.ActionsCoachTest.class, com.github.robocup_atan.atan.model.ActionsPlayerTest.class,
    com.github.robocup_atan.atan.model.ControllerPlayerTest.class, com.github.robocup_atan.atan.model.SServerPlayerTest.class, com.github.robocup_atan.atan.model.SServerCoachTest.class,
    com.github.robocup_atan.atan.model.CommandFactoryTest.class, com.github.robocup_atan.atan.model.ActionsTrainerTest.class, com.github.robocup_atan.atan.model.SServerTrainerTest.class
})
public class ModelSuite {

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {}

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {}

    /**
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {}

    /**
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {}
}