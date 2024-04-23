import org.junit.Test;

import sim.PoolSimulator;
import sim.SimplePoolSimulator;


import static org.junit.Assert.assertEquals;

/**
 * test cases class for simplePoolSimulator.
 */
public class SimplePoolSimulatorTest {

  @Test(expected = IllegalArgumentException.class)
  public void testForNegativeHeightOrWidth() {
    PoolSimulator instance1 = new SimplePoolSimulator(-10, 10, "simple");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForInvalidStringType() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "Something");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNegativeRadius() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "simple");
    instance1.start(3, 3, -1, 10, 10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testForNegativeSpeed() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "simple");
    instance1.start(3, 3, 1, -10, 10, 0);
  }

  @Test
  public void testForGetBallPositionX() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "simple");
    instance1.start(3, 3, 1, 10, 10, 0);
    assertEquals(3, instance1.getBallPositionX(), 0.01);
  }

  @Test
  public void testForGetBallPositionY() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "simple");
    instance1.start(3, 3, 1, 10, 10, 0);
    assertEquals(3, instance1.getBallPositionY(), 0.01);
  }

  @Test
  public void testForGetRadius() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "simple");
    instance1.start(3, 3, 1, 10, 10, 0);
    assertEquals(1, instance1.getBallRadius(), 0.01);
  }

  @Test
  public void testForGetWidth() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "simple");
    instance1.start(3, 3, 1, 10, 10, 0);
    assertEquals(10, instance1.getTableWidth(), 0.01);
  }

  @Test
  public void testForGetHeight() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "simple");
    instance1.start(3, 3, 1, 10, 10, 0);
    assertEquals(10, instance1.getTableHeight(), 0.01);
  }

  @Test
  public void testForSimplePhysics() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "simple");
    assertEquals("Status: Ball not set up", instance1.getStatus());
    instance1.start(3, 3, 1, 10, 4, 2);
    assertEquals("Status: Simulation started", instance1.getStatus());
    instance1.advance();
    assertEquals("Status: Ball hit right edge", instance1.getStatus());

  }

  @Test
  public void testForSimpleEnd() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "simple");
    assertEquals("Status: Ball not set up", instance1.getStatus());
    instance1.start(3, 3, 1, 10, 4, 2);
    assertEquals("Status: Simulation started", instance1.getStatus());
    instance1.advance();
    assertEquals("Status: Ball hit right edge", instance1.getStatus());
    instance1.advance();
    assertEquals("Status: Ball stationary", instance1.getStatus());

  }

  @Test
  public void testForfriction() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "friction");
    assertEquals("Status: Ball not set up", instance1.getStatus());
    instance1.start(3, 3, 1, 5, 4, 2);
    assertEquals("Status: Simulation started", instance1.getStatus());
    instance1.advance();
    assertEquals("Status: Ball hit top edge", instance1.getStatus());

  }

  @Test
  public void testForgetStatus() {
    PoolSimulator instance1 = new SimplePoolSimulator(10, 10, "simple");
    assertEquals("Status: Ball not set up", instance1.getStatus());
    instance1.start(3, 3, 1, 5, 4, 2);
    assertEquals("Status: Simulation started", instance1.getStatus());
    instance1.advance();
    assertEquals("Status: Ball is stationary", instance1.getStatus());

  }


}