


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.HashMap;



public class Test extends Application {


    private HashMap<KeyCode, Boolean> keys = new HashMap<KeyCode, Boolean>();
    private ArrayList<Node> platforms = new ArrayList<>();
    private ArrayList<Node> sky = new ArrayList<>();
    private int Width = 1000;
    private int Height = 1000;



    private Pane gameRoot = new Pane();
    private Node player;
    private int speed=10;


    private Node createEntity(int x, int y, int w, int h, Color color) {
        Rectangle entity = new Rectangle(w, h);
        entity.setTranslateX(x);
        entity.setTranslateY(y);
        entity.setFill(color);
        gameRoot.getChildren().add(entity);
        return entity;

    }

    private void initContent() {

        for (int i = 0; i < LevelData.Level1.length; i++) {
            for (int j = 0; j < LevelData.Level1[i].length(); j++) {
                switch (LevelData.Level1[i].charAt(j)) {
                    case '0':
                        Node platform = createEntity(j * 10, i * 10, 10, 10, Color.GREEN);
                        platforms.add(platform);
                        break;
                    case '1':
                        Node skys = createEntity(j * 10, i * 10, 10, 10, Color.WHITE);
                        sky.add(skys);
                        break;
                        default:
                            platform = createEntity(j * 10, i * 10, 10, 10, Color.GREEN);
                            platforms.add(platform);
                            break;
                }
            }
        }
        player = createEntity(1500, 100, 30, 30, Color.BLUE);
    }

    private boolean check;
    private boolean isPressed(KeyCode key) {
        return keys.getOrDefault(key, false);
    }
    private void update() {

        if (isPressed(KeyCode.W)||isPressed(KeyCode.UP)) { player.setTranslateY(player.getTranslateY() + -speed);}
        if (isPressed(KeyCode.S)||isPressed(KeyCode.DOWN)) { player.setTranslateY(player.getTranslateY() + speed); }
        if (isPressed(KeyCode.A)||isPressed(KeyCode.LEFT)) { player.setTranslateX(player.getTranslateX() + -speed);}
        if (isPressed(KeyCode.D)||isPressed(KeyCode.RIGHT)) { player.setTranslateX(player.getTranslateX() + speed); }

        for (Node wall : platforms) { if (player.getBoundsInParent().intersects(wall.getBoundsInParent())) {

            if (isPressed(KeyCode.W)||isPressed(KeyCode.UP)) { player.setTranslateY(player.getTranslateY() + speed);}
            if (isPressed(KeyCode.S)||isPressed(KeyCode.DOWN)) { player.setTranslateY(player.getTranslateY() + -speed);}
            if (isPressed(KeyCode.A)||isPressed(KeyCode.LEFT)) { player.setTranslateX(player.getTranslateX() + speed);}
            if (isPressed(KeyCode.D)||isPressed(KeyCode.RIGHT)) { player.setTranslateX(player.getTranslateX() + -speed);}

           }}

        player.translateXProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > Width / 3) { gameRoot.setLayoutX(-(offset - Width / 3)); }});

        player.translateYProperty().addListener((obs, old, newValue) -> {
            int offset = newValue.intValue();
            if (offset > Height / 3) { gameRoot.setLayoutY(-(offset - Height / 3)); }});
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        initContent();
        Scene scene = new Scene(gameRoot,Width,Height);
        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));
        primaryStage.setTitle("Text Adventure Maze");
        primaryStage.setScene(scene);
        primaryStage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    public static void Main(String[] args) {

        launch(args);
    }
}
