package sim;

/**
 * Interface containing the methods for class.
 */
public interface PoolSimulator {

  void start(double x, double y, double radius, double speed, double dx, double dy);

  void advance();

  int getTableWidth();

  int getTableHeight();

  double getBallPositionX();

  double getBallPositionY();

  double getBallRadius();

  double getBallVelocityX();

  double getBallVelocityY();

  String getStatus();
}
