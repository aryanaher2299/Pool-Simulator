package sim;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

/**
 * class that implements PoolSimulator interface.
 */
public class SimplePoolSimulator implements PoolSimulator {

  private double x;
  private double y;

  private double radius;
  private double speed;


  private double dx;
  private double dy;

  private double vx;
  private double vy;

  private double cons = (9.81 * 0.1) / 2;
  private int width;

  private int height;
  private double xmin;

  private double ymin;

  private String type;

  private String flag;
  private double timeX;
  private double timeY;

  /**
   * Constructor.
   *
   * @param width  takes input as width.
   * @param height takes input as height.
   * @param type   takes type as string to compare simple or friction.
   */
  public SimplePoolSimulator(int width, int height, String type) {
    if (width < 0 || height < 0) {
      throw new IllegalArgumentException("width and height cannot be negative");
    } else if (!type.equals("simple") & !type.equals("friction")) {
      throw new IllegalArgumentException("type must be Simple or Friction");
    } else {
      this.width = width;
      this.height = height;
      this.x = Double.NEGATIVE_INFINITY;
      this.y = Double.NEGATIVE_INFINITY;
      this.radius = Double.NEGATIVE_INFINITY;
      this.type = type;
      this.flag = "Status: Ball not set up";
    }
  }

  /**
   * method to initalise the ball.
   *
   * @param x      takes x poisition of ball.
   * @param y      y position of the ball.
   * @param radius radius of the ball.
   * @param speed  speed of the ball.
   * @param dx     direction in x of the ball.
   * @param dy     direction in y of the ball.
   * @throws IllegalArgumentException throws exception for invalid parameters.
   */
  @Override
  public void start(double x, double y, double radius, double speed,
                    double dx, double dy) throws IllegalArgumentException {
    if (x < 0 || y < 0 || x < radius || y < radius
            || radius <= 0 || speed < 0 || (x > this.width - radius)
            || (y > this.height - radius)) {
      throw new IllegalArgumentException("x, y, speed  or radius cannot be negative");
    } else {
      this.x = x;
      this.y = y;
      this.radius = radius;
      this.speed = speed;


      if (sqrt(dx * dx + dy * dy) != 1) {
        double mag = (dx * dx) + (dy * dy);
        mag = sqrt(mag);
        this.dx = dx / mag;
        this.dy = dy / mag;
      } else {
        this.dx = dx;
        this.dy = dy;
      }

      this.vx = speed * this.dx;
      this.vy = speed * this.dy;


      this.flag = "Status: Simulation started";
    }
  }

  /**
   * advance method that calls the appropriate function.
   * for simple or friction.
   */
  @Override
  public void advance() {

    if (this.type.equals("simple")) {
      calculateSimpletime();
    } else {
      if (speed > 0) {
        calcComplexTime();
      } else {
        this.flag = "Status: Ball is stationary";
      }
    }
  }


  /**
   * calculates the simple time.
   */
  private void calculateSimpletime() {
    double t1 = 0;
    double t2 = 0;
    //1. dx, dy +ve so checking top and right.(t1, t2 respectively)
    //t1 checks for right edge.
    //t2 checks for the top edge.
    if (this.dx >= 0 & this.dy >= 0) {
      t1 = abs((this.width - this.x - this.radius) / (this.speed * this.dx));
      t2 = abs((this.height - this.y - this.radius) / (this.speed * this.dy));

      if (t1 <= t2) {
        this.flag = "Status: Ball hit right edge";
        System.out.println("Time: right " + t1);
        newDisplacement(t1);
        newSpeedAndVelocity();
        this.dx = -this.dx;
      } else {
        this.flag = "Status: Ball hit top edge";
        System.out.println("Time: top" + t2);
        newDisplacement(t2);

        newSpeedAndVelocity();
        this.dy = -this.dy;
      }


    }
    //2. ball hits top or left edge.
    //t1 is for left edge.
    //t2 is for top edge.
    else if (this.dx < 0 & this.dy >= 0) {
      t1 = abs((this.x - this.radius) / (this.speed * this.dx));
      t2 = abs((this.height - this.y - this.radius) / (this.speed * this.dy));
      if (t1 <= t2) {
        this.flag = "Status: Ball hit left edge";

        newDisplacement(t1);
        System.out.println("Time: left " + t1);
        newSpeedAndVelocity();
        this.dx = -this.dx;
      } else {
        this.flag = "Status: Ball hit top edge";

        newDisplacement(t2);
        System.out.println("Time: top" + t2);
        newSpeedAndVelocity();
        this.dy = -this.dy;
      }


    }
    //3.
    //t1 is for left edge
    //t2 is for bottom edge
    else if (this.dx < 0 & this.dy < 0) {
      t1 = abs((this.x - this.radius) / (this.speed * this.dx));
      t2 = abs((this.y - this.radius) / (this.speed * this.dy));
      if (t1 <= t2) {
        this.flag = "Status: Ball hit left edge";

        newDisplacement(t1);
        System.out.println("Time: left " + t1);
        newSpeedAndVelocity();
        this.dx = -this.dx;
      } else {
        this.flag = "Status: Ball hit bottom edge";

        newDisplacement(t2);
        System.out.println("Time: bottom" + t2);
        newSpeedAndVelocity();
        this.dy = -this.dy;
      }


    }
    //4.
    //t1 is for right edge
    //t2 is for bottom edge
    else if (this.dx >= 0 & this.dy < 0) {
      t1 = abs((this.width - this.x - this.radius) / (this.speed * this.dx));
      t2 = abs((this.y - this.radius) / (this.speed * this.dy));
      if (t1 <= t2) {
        this.flag = "Status: Ball hit right edge";

        newDisplacement(t1);
        System.out.println("Time: right" + t1);
        newSpeedAndVelocity();
        this.dx = -this.dx;
      } else {
        this.flag = "Status: Ball hit bottom edge";

        newDisplacement(t2);
        System.out.println("Time: bottom" + t2);
        newSpeedAndVelocity();
        this.dy = -this.dy;
      }


    }

  }

  /**
   * function that calculates new speed for simple case.
   */
  private void newSpeedAndVelocity() {
    this.speed -= 5;
    if (this.speed <= 0) {
      this.speed = 0;
      this.flag = "Status: Ball is stationary";
    }
  }


  /**
   * calculates displacement for new position of the ball.
   *
   * @param time minimum time that was selected.
   */
  private void newDisplacement(double time) {
    this.x += (this.speed * this.dx * time);
    this.y += (this.speed * this.dy * time);
  }


  /**
   * function to calculate the friction case time.
   */
  private void calcComplexTime() {
    double bx = -(this.speed * abs(this.dx));
    double by = -(this.speed * abs(this.dy));
    double ax = this.cons * abs(this.dx);
    double ay = this.cons * abs(this.dy);
    double cx = 0;
    double cy = 0;
    double temp1 = 0;
    double temp2 = 0;

    if (this.dx > 0) {
      cx = this.width - this.x - this.radius;
    } else if (this.dx < 0) {
      cx = this.x - this.radius;
    }
    if (this.dy > 0) {
      cy = this.height - this.y - this.radius;
    } else if (this.dy < 0) {
      cy = this.y - this.radius;
    }


    double rootX = (bx * bx) - (4 * ax * cx);
    if (rootX < 0) {
      this.flag = "Status: Ball is Stationary";
    } else {
      rootX = sqrt(rootX);
      temp1 = 2 * cx / (-bx + rootX);
      temp2 = (2 * cx) / (-bx - rootX);

      if (temp1 < 0) {
        if (temp2 < 0) {
          timeX = Double.POSITIVE_INFINITY;
        } else if (temp2 > 0) {
          timeX = temp2;
        }
      }
      if (temp2 < 0 & temp1 > 0) {
        timeX = temp1;
      }

      if (temp1 < temp2) {
        this.timeX = abs(temp1);
      } else {
        this.timeX = abs(temp2);
      }

    }


    double rootY = (by * by) - (4 * ay * cy);
    if (rootY < 0) {
      this.flag = "Status: Ball is Stationary";
    } else {
      rootY = sqrt(rootY);
      temp1 = (2 * cy) / (-by + rootY);
      temp2 = (2 * cy) / (-by - rootY);

      if (temp1 < 0) {
        if (temp2 < 0) {
          timeY = Double.POSITIVE_INFINITY;
        } else if (temp2 > 0) {
          timeY = temp2;
        }
      }
      if (temp2 < 0 & temp1 > 0) {
        timeY = temp1;
      }

      if (temp1 < temp2) {
        this.timeY = abs(temp1);
      } else {
        this.timeY = abs(temp2);
      }

    }

    double stopTime = this.speed / (9.81 * 0.1);

    double time = min(timeX, timeY);
    if ((stopTime - min(time, stopTime)) < 0.001) {
      this.flag = "Status: Ball is stationary";
      this.speed = 0;
    } else if (timeX <= timeY && this.dx > 0) {
      this.flag = "Status: Ball hit right edge";

      newComplexDisplacement(timeX);
      this.dx = -this.dx;
      newComplexSpeedAndVelocity(timeX);
    }

    //ball hits the top edge since Y is smaller.
    else if (this.timeY < this.timeX & this.dy > 0) {
      this.flag = "Status: Ball hit top edge";

      newComplexDisplacement(timeY);
      this.dy = -this.dy;
      newComplexSpeedAndVelocity(timeY);
    } else if (this.timeY < this.timeX & this.dy < 0) {
      this.flag = "Status: Ball hit bottom edge";

      newComplexDisplacement(timeY);
      this.dy = -this.dy;
      newComplexSpeedAndVelocity(timeY);
    } else if (timeX < timeY && this.dx < 0) {
      this.flag = "Status: Ball hit left edge";

      newComplexDisplacement(timeX);
      this.dx = -this.dx;
      newComplexSpeedAndVelocity(timeX);
    }

  }


  /**
   * function to calculate new position of the ball.
   *
   * @param time takes minimum time as the parameter.
   */
  private void newComplexDisplacement(double time) {
    this.x += (this.speed * this.dx * time) - (this.cons * this.dx * time * time);
    this.y += (this.speed * this.dy * time) - (this.cons * this.dy * time * time);
  }

  /**
   * calculates the new speed.
   *
   * @param time uses time that was minimum.
   */
  private void newComplexSpeedAndVelocity(double time) {
    this.speed -= 9.81 * 0.1 * time;
    if (speed <= 0) {
      this.flag = "Status: Ball is stationary";
    }
  }


  /**
   * returns the width of table.
   *
   * @return the width of the table.
   */
  @Override
  public int getTableWidth() {
    return this.width;
  }

  /**
   * returns the height of table.
   *
   * @return the height of the table.
   */
  @Override
  public int getTableHeight() {
    return this.height;
  }

  /**
   * returns the x position of the ball.
   *
   * @return the x position of the ball.
   */
  @Override
  public double getBallPositionX() {
    return this.x;
  }

  /**
   * returns the y position of the ball.
   *
   * @return the y position of the ball.
   */
  @Override
  public double getBallPositionY() {
    return this.y;
  }

  /**
   * returns the radius position of the ball.
   *
   * @return the radius position of the ball.
   */
  @Override
  public double getBallRadius() {
    return this.radius;
  }

  /**
   * returns the x velocity of the ball.
   *
   * @return the x velocity of the ball.
   */
  @Override
  public double getBallVelocityX() {
    if (Double.isNaN(this.speed)) {
      this.speed = 0;
    }
    return this.speed * this.dx;
  }

  /**
   * returns the y velocity of the ball.
   *
   * @return the y velocity of the ball.
   */
  @Override
  public double getBallVelocityY() {
    return this.speed * this.dy;
  }

  /**
   * returns the string status of the ball.
   *
   * @return the string status of the ball.
   */
  @Override
  public String getStatus() {
    return this.flag;
  }

}
