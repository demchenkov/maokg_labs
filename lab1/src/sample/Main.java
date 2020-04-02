package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.Stage;

public class Main extends Application {
    public static int i = 0;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        Polygon polygon = new Polygon();
        root.getChildren().add(polygon);
        polygon.getPoints().addAll(
            121d, 171d,
                79d, 87d,
                159d, 25d,
                279d, 77d,
                202d, 110d,
                227d, 162d
        );
        polygon.setFill(Color.rgb(0, 255, 0));

        Line line1 = new Line(polygon.getPoints().get(2), polygon.getPoints().get(3), polygon.getPoints().get(8), polygon.getPoints().get(9));
        line1.setStrokeWidth(3);
        line1.setStrokeLineCap(StrokeLineCap.ROUND);
        root.getChildren().add(line1);

        Line line2 = new Line(101, 71, 50, 28);
        line2.setStrokeWidth(5);
        line2.setStrokeLineCap(StrokeLineCap.ROUND);
        root.getChildren().add(line2);

        Line line3 = new Line(103, 128, 43, 149);
        line3.setStrokeWidth(5);
        line3.setStrokeLineCap(StrokeLineCap.ROUND);
        root.getChildren().add(line3);

        Rectangle rectangle1 = new Rectangle(132, 68, 6, 6 );
        rectangle1.setFill(Color.rgb(0, 128, 0));
        root.getChildren().add(rectangle1);

        Rectangle rectangle2 = new Rectangle(120, 113, 6, 6 );
        rectangle2.setFill(Color.rgb(0, 128, 0));
        root.getChildren().add(rectangle2);

        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                236d, 150d,
                219d, 112d,
                265d, 100d
        );
        triangle.setFill(Color.rgb(255, 255, 0));
        root.getChildren().add(triangle);

        Scene scene = new Scene(root, 300, 175);

        scene.setFill(Color.rgb(0, 128, 128));
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
