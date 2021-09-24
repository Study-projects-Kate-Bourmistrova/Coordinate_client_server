public class Coordinate {
    int id = -1;
    int x_coordinate;
    int y_coordinate;


    public Coordinate(int x_coordinate,int y_coordinate) {
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    public int getX_coordinate() {
        return x_coordinate;
    }

    public void setX_coordinate(int x_coordinate) {
        this.x_coordinate = x_coordinate;
    }

    public int getY_coordinate() {
        return y_coordinate;
    }

    public void setY_coordinate(int y_coordinate) {
        this.y_coordinate = y_coordinate;
    }

    @Override
    public String toString(){
        return x_coordinate + " " + y_coordinate;
    }
}
