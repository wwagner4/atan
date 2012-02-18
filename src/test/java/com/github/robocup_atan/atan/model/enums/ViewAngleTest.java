package com.github.robocup_atan.atan.model.enums;

//~--- non-JDK imports --------------------------------------------------------

import com.github.robocup_atan.atan.model.enums.ViewAngle;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p>ViewAngleTest class.</p>
 *
 * @author Atan
 * @since 1.0
 */
public class ViewAngleTest {

    /**
     * <p>Constructor for ViewAngleTest.</p>
     */
    public ViewAngleTest() {}

    /**
     * <p>setUpClass.</p>
     *
     * @throws java.lang.Exception if any.
     */
    @BeforeClass
    public static void setUpClass() throws Exception {}

    /**
     * <p>tearDownClass.</p>
     *
     * @throws java.lang.Exception if any.
     */
    @AfterClass
    public static void tearDownClass() throws Exception {}

    /**
     * <p>setUp.</p>
     */
    @Before
    public void setUp() {}

    /**
     * <p>tearDown.</p>
     */
    @After
    public void tearDown() {}

    /**
     * Test of values method, of class ViewAngle.
     */
    @Test
    public void testValues() {
        System.out.println("values");
        ViewAngle[] expResult = {ViewAngle.NARROW, ViewAngle.NORMAL, ViewAngle.WIDE};
        ViewAngle[] result    = ViewAngle.values();
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of valueOf method, of class ViewAngle.
     */
    @Test
    public void testValueOf() {
        System.out.println("valueOf");
        String    name      = "NARROW";
        ViewAngle expResult = ViewAngle.NARROW;
        ViewAngle result    = ViewAngle.valueOf(name);
        assertEquals(expResult, result);
        name      = "NORMAL";
        expResult = ViewAngle.NORMAL;
        result    = ViewAngle.valueOf(name);
        assertEquals(expResult, result);
        name      = "WIDE";
        expResult = ViewAngle.WIDE;
        result    = ViewAngle.valueOf(name);
        assertEquals(expResult, result);
    }
}
